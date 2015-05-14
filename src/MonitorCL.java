/*
*	Clase ::	MonitorCL
*	Descripcion :: Clase que define al monitor para la cola de listos del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class MonitorCL{

	private TreeMap<Double, Proceso> colaListos = new TreeMap<Double, Proceso>();
	private boolean vacio = true;
  private int carga = 0; //para indicar la carga q tiene en ciclos de reloj esta cola
  private int id = 0;

  public MonitorCL(int id){
    this.id = id;
  }

	synchronized Proceso getProceso(){    

    // extraemos el primer proceso de la cola
    Map.Entry<Double, Proceso> proceso = colaListos.pollFirstEntry();
    
    // indicamos si quedan procesos en la cola
    if (colaListos.firstEntry() == null) {
      vacio = true;
    }

    Pair<String,Integer> source = proceso.getValue().getFirstSource();
    if (source.getL().equals("CPU")){
      carga = carga - source.getR() ;
    }
  
		return proceso.getValue();
	}

	synchronized void addProcesoListo( Double peso, Proceso proceso){
  
    Pair<String,Integer> source = proceso.getFirstSource();

    if (source.getL().equals("CPU")){
      carga = source.getR() + carga;
    }

    colaListos.put(peso, proceso);
    vacio = false;

    System.out.println("Cola de Listos "+this.id+"= se agrego un nuevo proceso ["+ proceso.toString() +"] - Carga actual: "+this.carga);	
	}

  synchronized int getCarga(){
    return this.carga;
  }
	
}
