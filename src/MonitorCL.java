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

	synchronized Proceso getProceso(){    

    // extraemos el primer proceso de la cola
    Map.Entry<Double, Proceso> proceso = colaListos.pollFirstEntry();
    
    // indicamos si quedan procesos en la cola
    if (colaListos.firstEntry() == null) {
      vacio = true;
    }

    /*se resta a la carga de la cola el tiempo de la siguiente 
    operacion del proceso tomado siempre y cuando sea CPU
    (para estar en esta cola la operacion debe ser de CPU)*/
    Pair<String,Integer> source = proceso.getValue().getFirstSource();
    if (source.getL().equals("CPU")){
      carga = carga - source.getR() ;
    }
  
		return proceso.getValue();
	}

	synchronized void addProcesoListo( Double peso, Proceso proceso){

    System.out.println("Cola de Listos = se agrego un nuevo proceso ["+ proceso.toString() +"]");    
    Pair<String,Integer> source = proceso.getFirstSource();

    /*se sula a la carga de la cola el tiempo de la siguiente 
    operacion del proceso tomado siempre y cuando sea CPU
    (para haber llegado a esta cola la operacion debe ser de CPU)*/
    if (source.getL().equals("CPU")){
      carga = source.getR() + carga;
    }

    colaListos.put(peso, proceso);
    vacio = false;
	
	}

  synchronized int getCarga(){
    return this.carga;
  }
	
}
