/*
*	Clase ::	MonitorCL
*	Descripcion :: Clase que define al monitor para la cola de listos del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class MonitorCL{
  //Arbol rojo-negro de procesos, balanceado por el vruntime.
	private TreeMap<Pair<Integer,Double>, Proceso> colaListos = new TreeMap<Pair<Integer,Double>,Proceso>(new Comparator<Pair<Integer,Double>>(){
    @Override
    public int compare(Pair<Integer,Double> a, Pair<Integer,Double> b) {
        if(a.getR() == b.getR() && a.getL() == b.getL()) return 0;
        if(a.getR() < b.getR()) return -1;
        else if(a.getR() == b.getR()) return 1;
        else return 1;
    }
  });
	private boolean vacio = true;
  private double min_vruntime = 0.0; //El valor mínimo de vruntime, del proceso mas izquierdo en el arbol.
  private int carga = 0; //para indicar la carga q tiene en ciclos de reloj esta cola
  private int id = 0;
  private int sched_period = 20; //tiempo ajustable en el que pasa cada proceso al menos una vez
  private int sched_latency_ns = 20; //tiempo en el que debe pasar cada proceso al menos una vez.
  private int min_granularity = 4; //cantidad mínima de ciclos de cpu que puede tener un proceso.
  private double totalPesos = 0.0;
  private boolean termin = false;
  private Ventana ventana;
  public MonitorCL(int id, Ventana v){
    this.id = id;
    this.ventana = v;
  }

  synchronized void statProceso(Proceso p, int t) {
    this.ventana.setStatProceso(p,t);
  }

  //Funcion que devuelve el proceso con el vRuntime mas bajo
	synchronized Proceso getProceso(){    

    // extraemos el primer proceso de la cola
    if(colaListos.firstEntry() != null) {
      Map.Entry<Pair<Integer,Double>, Proceso> proceso = colaListos.pollFirstEntry();
    
      // indicamos si quedan procesos en la cola
      if (colaListos.firstEntry() == null) {
        vacio = true;
      }
      Proceso proc = proceso.getValue();
      Pair<String,Integer> source = proc.getFirstSource();
      if(source != null) {
        if (source.getL().equals("CPU")){
          carga = carga - source.getR();
        }
        //Se le asigna el vRuntime para que lo conserve, si vuelve al arbol.
        proc.setVruntime(proceso.getKey().getR());
        Double a = (proc.getPeso()/totalPesos);
        //Se le asigna la cantidad de ciclos de CPU que le corresponde
        proc.setTimeSlice(sched_period*a.intValue());
    		return proc;
      } else {
        return null;
      }
    } else {
      //System.out.println("Monitor tiempo:: "+" >> colaListos vacia");
      return null;
    }
	}

  //Procesimiento que añade un proceso listo al arbol
	synchronized void addProcesoListo( Pair<Integer,Double> vRun, Proceso proceso, int tiempo){
  
    Pair<String,Integer> source = proceso.getFirstSource();

    //Si esta solicitando un recurso de CPU, se actualiza la carga
    if (source.getL().equals("CPU")){
      carga = source.getR() + carga;
    }

    // ESTADISTICA indica al proceso que empezo un periodo de espera
    proceso.inicioEspera(tiempo);

    colaListos.put(vRun, proceso);
    vacio = false;

    //Se revisa el porcentaje de cpu que le corresponde al proceso.
    totalPesos = getPesosCPU();
    double porc = proceso.getPeso()/totalPesos;
    System.out.println("Porcentaje de cpu que le toca al proceso entrante: "+porc);

    //Se actualiza el min_vruntime
    for(Map.Entry<Pair<Integer,Double>, Proceso> entry : colaListos.entrySet()) {
      Pair<Integer,Double> key = entry.getKey();
      min_vruntime = key.getR();
      break;
    }

    //Se actualiza el periodo de cpu en el que deben pasar todos los procesos en el arbol
    int tamArbol = colaListos.size();
    if(tamArbol < (sched_latency_ns/min_granularity))
      sched_period = sched_latency_ns;
    else
      sched_period = tamArbol * min_granularity;
    //System.out.println("Cola de Listos "+this.id+"= se agrego un nuevo proceso ["+ proceso.toString() +"] - Carga actual: "+this.carga);	
	}

  //Procedimiento encargado de devolver un proceso que estaba consumiendo CPU al arbol de espera
  synchronized void devolverProceso(Proceso proceso, Integer time_rec, Integer tiempoCPU, int tiempo) {
    proceso.restarFirstResource(time_rec); //Se le resta el tiempo que ya consumio de CPU
    //Se hace el balanceo, si el proceso tiene mas prioridad, vruntime sera menor y viceversa.
    double vruntime = proceso.getVruntime() + tiempoCPU * (1024/proceso.getPeso());
    addProcesoListo(new Pair<Integer, Double>(proceso.getPID(),vruntime), proceso, tiempo);
  }

  synchronized int getCarga(){
    return this.carga;
  }

  synchronized boolean isVacio() {
    return this.vacio;
  }

  //Devuelve la sumatoria de los pesos de todos los procesos del arbol
  double getPesosCPU() {
    double pesoTotal = 0;
    for(Map.Entry<Pair<Integer,Double>, Proceso> entry : colaListos.entrySet()) {
      Pair<Integer,Double> key = entry.getKey();
      Proceso value = entry.getValue();
      pesoTotal = pesoTotal + value.getPeso();
    }
    System.out.println("Peso total: " + " => " + pesoTotal);
    return pesoTotal;
  }

  synchronized double getMinVruntime() {
    return min_vruntime;
  }
}
