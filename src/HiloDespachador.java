/*
*	Clase ::	HiloDespachador
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/
import java.util.*;

public class HiloDespachador extends Thread {
	
  int numCpu = 0;
  MonitorCL colas[] = null;
  MonitorTime time = null;
  TreeMap<Integer, Proceso> procesos = null;

	HiloDespachador(int numCpu, TreeMap<Integer, Proceso> procesosTreemap, MonitorTime time){
		super("Despachador");

    this.colas = new MonitorCL[numCpu];
    this.numCpu = numCpu;
    this.time = time;

    //Contiene todos los procesos
    this.procesos = procesosTreemap;

    for (int i = 0; i < numCpu ; i++) {
      colas[i] = new MonitorCL(i);
      HiloCPU cpu = new HiloCPU(colas[i], time, i , 4);
    }
    // levantamos los cpus requeridos y les asignamos sus colas de procesos listos

    //-----------------------asignar procesos de la cola general a la cola de procesos de cada cpu
    
		System.out.println("Levantando Hilo Despachador: " + this);
		start(); // Arrancamos el despachador
	}

  public void run(){ 
    Pair<String,Integer> source;
    Integer arrivalTime;
    while(true){
    
      if (procesos.firstEntry() != null) {
        Proceso entrante = procesos.firstEntry().getValue();

        arrivalTime = entrante.getArrivalTime();
        if (time.getTime() >= arrivalTime) {          
          entrante = procesos.pollFirstEntry().getValue();

          source = entrante.getFirstSource();


          if (source.getL().equals("CPU")){

            System.out.println("tiempo:: "+time.getTime()+" >> Un proceso solicita CPU");
            distribuir(entrante); 
          }else{

            System.out.println("tiempo:: "+time.getTime()+" >> Un proceso solicita IO");
            entrante.setArrivalTime(arrivalTime + source.getR());
            entrante.removeFirstSource();
            arrivalTime = (entrante.getArrivalTime() * 1000);
            while(procesos.containsKey(arrivalTime))
                    arrivalTime++;
            procesos.put(arrivalTime, entrante);
          }
                  
        }
      }
    }
    
  }


  public void distribuir(Proceso proceso){

    // buscamos la cola con menos carga de procesos
    int tam = this.colas.length;
    int iter = 0;
    MonitorCL minimo = null;

    if (tam > 0) {
      
      minimo = colas[0];

      for (int i = 0; i < tam ; i++) {
        if (minimo.getCarga() > colas[i].getCarga())
          minimo = colas[i];        
      }

      minimo.addProcesoListo(0.0,proceso);
    }
  }

}