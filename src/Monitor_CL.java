/*
*	Clase ::	Monitor_CL
*	Descripcion :: Clase que define al monitor para la cola de listos del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class Monitor_CL{

	private TreeMap<Double, Procesos> cola_listos = new TreeMap<Double, Procesos>();
	private boolean vacio = true;

	synchronized Procesos get_proceso(){
    while (vacio) {
      try {
        wait();
      } catch (InterruptedException e ) {
        System.out.println("No se logro bloquear el acceso a un hilo");
      }
    }

    // extraemos el primer proceso de la cola
    Map.Entry<Double, Procesos> proceso = cola_listos.pollFirstEntry();
    
    // indicamos si quedan procesos en la cola
    if (cola_listos.firstEntry() == null) {
      vacio = true;
    }
    
    // liberamos el monitor
    notify();

		return proceso.getValue();
	}

	synchronized void add_proceso_listo( Double peso, Procesos proceso){

    System.out.println("Cola de Listos = se agrego un nuevo proceso ["+ proceso.toString() +"]");
    cola_listos.put(peso, proceso);
    vacio = false;
    notify();
	
	}
	
}
