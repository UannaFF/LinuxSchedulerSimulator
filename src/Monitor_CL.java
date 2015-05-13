/*
*	Clase ::	Monitor_CL
*	Descripcion :: Clase que define al monitor para la cola de listos del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class Monitor_CL{

	private TreeMap<Double, Proceso> cola_listos = new TreeMap<Double, Proceso>();
	private boolean vacio = true;
  private int carga = 0; //para indicar la carga q tiene en ciclos de reloj esta cola

	synchronized Proceso get_proceso(){    

    while (vacio) {
      try {
        wait();
      } catch (InterruptedException e ) {
        System.out.println("No se logro bloquear el acceso a un hilo");
      }
    }

    // extraemos el primer proceso de la cola
    Map.Entry<Double, Proceso> proceso = cola_listos.pollFirstEntry();
    
    // indicamos si quedan procesos en la cola
    if (cola_listos.firstEntry() == null) {
      vacio = true;
    }

    /*se resta a la carga de la cola el tiempo de la siguiente 
    operacion del proceso tomado siempre y cuando sea CPU
    (para estar en esta cola la operacion debe ser de CPU)*/
    Pair<String,Integer> source = proceso.getValue().getFirstSource();
    if (source.getL().equals("CPU")){
      carga = carga - source.getR() ;
    }
    
    // liberamos el monitor???
    notify();

		return proceso.getValue();
	}

	synchronized void add_proceso_listo( Double peso, Proceso proceso){

    System.out.println("Cola de Listos = se agrego un nuevo proceso ["+ proceso.toString() +"]");    
    Pair<String,Integer> source = proceso.getFirstSource();

    /*se sula a la carga de la cola el tiempo de la siguiente 
    operacion del proceso tomado siempre y cuando sea CPU
    (para haber llegado a esta cola la operacion debe ser de CPU)*/
    if (source.getL().equals("CPU")){
      carga = source.getR() + carga;
    }

    cola_listos.put(peso, proceso);
    vacio = false;
    notify();
	
	}

  synchronized int getCarga(){
    return this.carga;
  }
	
}
