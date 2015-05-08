/*
*	Clase ::	Monitor_CL
*	Descripcion :: Clase que define al monitor para la cola de listos del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class Monitor_CL{

	private TreeMap<Double, Procesos> cola_listos = new TreeMap<Double, Procesos>();
	private boolean comprometido = false;

	synchronized Procesos get_proceso(){

		return null;
	}

	synchronized void add_proceso_listo( Double peso, Procesos proceso){

		if (comprometido) {
			try {
        wait();
      } catch (InterruptedException e ) {
        System.out.println("No se logro bloquear el acceso a un hilo");
      }
    }

    cola_listos.put(peso, proceso);
    comprometido = false;
		

	}
	
}
