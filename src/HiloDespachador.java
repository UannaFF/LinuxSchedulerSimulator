/*
*	Clase ::	HiloDespachador
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/
import java.util.*;

public class HiloDespachador extends Thread {
	
  int numCpu = 0;
  Double numProc = 0.0;
  Double tiempoEspera= 0.0;
  MonitorCL colas[] = null;
  MonitorTime time = null;
  MonitorIO colaIO = null;
  boolean running = true;
  TreeMap<Integer, Proceso> procesos = null;
  HiloCPU cp[] = null;
  Ventana vent;
  String estads = "";

	HiloDespachador(int numCpu, TreeMap<Integer, Proceso> procesosTreemap, MonitorTime time, MonitorIO colaIO, Ventana ventana){
		super("Despachador");

    this.colas = new MonitorCL[numCpu];
    this.cp = new HiloCPU[numCpu];
    this.numCpu = numCpu;
    this.time = time;
    this.colaIO = colaIO;
    //Contiene todos los procesos
    this.procesos = procesosTreemap;
    for (int i = 0; i < numCpu ; i++) {
      colas[i] = new MonitorCL(i,ventana);
      HiloCPU cpu = new HiloCPU(colas[i], time, i , 4, colaIO, ventana);
      this.cp[i] = cpu;
    }
    this.vent = ventana;
    // levantamos los cpus requeridos y les asignamos sus colas de procesos listos

    //-----------------------asignar procesos de la cola general a la cola de procesos de cada cpu
    
		System.out.println("Levantando Hilo Despachador: " + this);
		start(); // Arrancamos el despachador
	}

  public Estadisticas terminate() {
    running = false;
    Double ocioPromedio=0.0;
    Estadisticas estadisticas = new Estadisticas();
    //construir un objeto estadisticas con estadisticas de tiempo de ocio, tiempo de ejecucion y tiempo de espera (promedios)
    for(int i=0; i < numCpu; i++) {
      ocioPromedio += cp[i].terminate();
    }
    ocioPromedio = ocioPromedio / numCpu;
    estadisticas.setOcio(ocioPromedio);
    estadisticas.setEjecucion(time.getTime()-ocioPromedio);
    estadisticas.setEspera(tiempoEspera/numProc);
    System.out.println("ocioPromedio::"+ocioPromedio);
    System.out.println("ejecucionPromedio::"+(time.getTime()-ocioPromedio));
    System.out.println("esperaPromedio::"+tiempoEspera/numProc);
    return estadisticas;
  }

  public void run(){ 
    Pair<String,Integer> source;
    Integer arrivalTime;
    while(running){
      //Toma los elementos que estan en IO
      ArrayList<Proceso> procIO = colaIO.getProcesosIO();
      //System.out.println("Llegaron "+procIO.size()+" procesos para IO.");
      for(int i=0; i< procIO.size(); i++) {
        Integer arrivalTimeIO = (procIO.get(i).getArrivalTime())*1000;
        while(procesos.containsKey(arrivalTimeIO))
          arrivalTimeIO++;
        Proceso p = procIO.get(i);
        p.removeFirstSource();
        procesos.put(arrivalTimeIO, p);
        //System.out.println("Proceso "+procIO.get(i).toString()+" llego con tiempo de IO.");
      }
      //
      if (procesos.firstEntry() != null) {
        Proceso entrante = procesos.firstEntry().getValue();

        arrivalTime = entrante.getArrivalTime();
        if (time.getTime() >= arrivalTime) {          
          entrante = procesos.pollFirstEntry().getValue();

          source = entrante.getFirstSource();

          if(source != null) {
            if (source.getL().equals("CPU")){
              //System.out.println("tiempo:: "+time.getTime()+" >> Un proceso solicita CPU");
              distribuir(entrante); 
            }else{
              //System.out.println("tiempo:: "+time.getTime()+" >> Un proceso solicita IO");
              entrante.setArrivalTime(arrivalTime + source.getR());
              entrante.removeFirstSource();
              arrivalTime = (entrante.getArrivalTime() * 1000);
              while(procesos.containsKey(arrivalTime))
                      arrivalTime++;
              procesos.put(arrivalTime, entrante);
            }
          } else {
            tiempoEspera = tiempoEspera+entrante.getTiempoEspera();
            numProc++;
              //colas[0].statProceso(entrante, time.getTime());
          }                  
        }
      }
    }
    
  }

  //Funcion que decido a que CPU se le va a asignar el proceso.
  public void distribuir(Proceso proceso){
    //System.out.println("Entro con proceso -> "+proceso);
    // buscamos la cola con menos carga de procesos
    int tam = this.colas.length;
    int iter = 0;
    MonitorCL minimo = null;
    Integer minimo_pos = 0;
    //Si tiene cpu actual null es su primera vez
    if(proceso.getActualCPU() == null) {
      if (tam > 0) {
        
        minimo = colas[0];

        for (int i = 0; i < tam ; i++) {
          if (minimo.getCarga() > colas[i].getCarga())
            minimo = colas[i];
            minimo_pos = i;  
        }
        proceso.setActualCPU(minimo_pos);
        //Se crea la clase de comparacion para el treemap

        Pair<Integer,Double> p = new Pair<Integer,Double>(proceso.getPID(),minimo.getMinVruntime());
        minimo.addProcesoListo(p,proceso, time.getTime());

      }
    } else {
      boolean other = false;
      //Se le pasa el proceso al CPU del que viene
      MonitorCL procesador_asigna = colas[proceso.getActualCPU()];
      double vruntime = proceso.getVruntime();
      //Si hay otro procesador que este vacio y el asignado esta trabajando, se pasa al ocioso
      if(!procesador_asigna.isVacio()){
        for(int i=0; i<tam; i++) {
          if(colas[i].isVacio()) {
            procesador_asigna = colas[i];
            proceso.setActualCPU(i);
            vruntime = procesador_asigna.getMinVruntime();
            break;
          }
        }
      }
      procesador_asigna.addProcesoListo(new Pair<Integer,Double>(proceso.getPID(),vruntime),proceso, time.getTime());
    }
  }

}