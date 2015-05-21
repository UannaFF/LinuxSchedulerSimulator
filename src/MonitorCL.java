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
        else return 1;
    }
  });
	private boolean vacio = true;
  private int carga = 0; //para indicar la carga q tiene en ciclos de reloj esta cola
  private int id = 0;

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

      Pair<String,Integer> source = proceso.getValue().getFirstSource();
      if(source != null) {
        if (source.getL().equals("CPU")){
          carga = carga - source.getR();
        }
        System.out.println("Monitor tiempo:: "+" >> getProceso "+proceso.getValue());
    		return proceso.getValue();
      } else {
        System.out.println("Monitor tiempo:: "+" >> Proceso se quedo sin resources");
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

    //System.out.println("Cola de Listos "+this.id+"= se agrego un nuevo proceso ["+ proceso.toString() +"] - Carga actual: "+this.carga);	
	}

  synchronized void devolverProceso(Proceso proceso, Integer time_rec) {
    proceso.restarFirstResource(time_rec);
    addProcesoListo(new Pair<Integer, Double>(proceso.getPID(),0.0), proceso);
  }

  synchronized int getCarga(){
    return this.carga;
  }

  synchronized boolean isVacio() {
    return this.vacio;
  }
	
}
