import java.util.*;


public class Main {
	// hilo principal de ejecucion
	
	public static void main(String arg[]){

	// ################# Hilo principal ######################################

		// tiempo de relog

		Integer tiempo = new Integer(0);

		// levantamos el hilo relog que hara correr el tiempo
		Hilo_relog relog = new Hilo_relog(tiempo);

		// Leer Archivo con lista de Procesos

		// Creacion de los procesos

		// (iterar) Paso de Procesos al Hilo despachador segun su tiempo de entrada
		Hilo_despachador despachador = new Hilo_despachador(1);

		Procesos proceso = new Procesos(1,10,2);
		despachador.distribuir(proceso);

	// #################  Fin Hilo Principal ################################

	}

}
