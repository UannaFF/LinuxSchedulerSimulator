/*
*	Clase ::	MonitorCL
*	Descripcion :: Clase que define al monitor para la cola de listos del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class MonitorCL{

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
  private double min_vruntime = 0.0;
  private int carga = 0; //para indicar la carga q tiene en ciclos de reloj esta cola
  private int id = 0;
  private int sched_period = 20;
  private int sched_latency_ns = 20; //tiempo en el que debe pasar cada proceso al menos una vez. Periodo de planificador
  private int min_granularity = 4;
  private double totalPesos = 0.0;
  private boolean termin = false;
  public MonitorCL(int id){
    this.id = id;
  }

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
        proc.setVruntime(proceso.getKey().getR());
        Double a = (proc.getPeso()/totalPesos);
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

	synchronized void addProcesoListo( Pair<Integer,Double> peso, Proceso proceso){
  
    Pair<String,Integer> source = proceso.getFirstSource();

    if (source.getL().equals("CPU")){
      carga = source.getR() + carga;
    }
    /*Double pesoP = (peso)*1000;
    while(colaListos.containsKey(pesoP))
      pesoP++;*/
    colaListos.put(peso, proceso);
    vacio = false;
    totalPesos = getPesosCPU();
    double porc = proceso.getPeso()/totalPesos;
    System.out.println("Porcentaje de cpu que le toca al proceso entrante: "+porc);

    //Se actualiza el min_vruntime
    for(Map.Entry<Pair<Integer,Double>, Proceso> entry : colaListos.entrySet()) {
      Pair<Integer,Double> key = entry.getKey();
      min_vruntime = key.getR();
      break;
    }

    System.out.println("MONITOR:: min_vruntime -> "+min_vruntime);
    int tamArbol = colaListos.size();
    if(tamArbol < (sched_latency_ns/min_granularity))
      sched_period = sched_latency_ns;
    else
      sched_period = tamArbol * min_granularity;
    //System.out.println("Cola de Listos "+this.id+"= se agrego un nuevo proceso ["+ proceso.toString() +"] - Carga actual: "+this.carga);	
	}

  synchronized void devolverProceso(Proceso proceso, Integer time_rec, Integer tiempoCPU) {
    proceso.restarFirstResource(time_rec);
    //Se hace el balanceo, si el proceso tiene mas prioridad, vruntime sera menor y viceversa.
    double vruntime = proceso.getVruntime() + tiempoCPU * (1024/proceso.getPeso());
    System.out.println("VRUNTIME:: "+vruntime);
    addProcesoListo(new Pair<Integer, Double>(proceso.getPID(),vruntime), proceso);
    System.out.println(colaListos);
  }

  synchronized int getCarga(){
    return this.carga;
  }

  synchronized boolean isVacio() {
    return this.vacio;
  }

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
