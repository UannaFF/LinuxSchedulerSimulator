import java.util.*;


public class Main {
	// hilo principal de ejecucion
	
	public static void main(String arg[]){

	// ################# PREBAS PARA EL ARBOL ROJO NEGRO DE LA LIBRERIA JAVA.UTIL ##################
		// el arbol almacenara nuestra cola de listos, se debe monitorear
		TreeMap<Double,Procesos> colaListos = new  TreeMap<Double,Procesos>();

		colaListos.put(2.0,new Procesos(2));
		colaListos.put(3.0,new Procesos(3));
		colaListos.put(4.0,new Procesos(4));
		colaListos.put(10.0,new Procesos(1));

		// con esta funcion sacamos el primer proceso de nuestra lista
		Map.Entry primero = colaListos.pollFirstEntry ();
		System.out.println("este es el primero = " + primero.getValue().toString() ); 

		colaListos.put(0.0,new Procesos(10));
		primero = colaListos.pollFirstEntry();
		System.out.println("este es el primero luego de agregar algo = " + primero.getValue().toString() );
		// FUNCIONA A LA PREFECCION 
	// ################################ FIN DE LAS PRUEBAS ###########################################

	// ################# Hilo Despachador ######################################

		// tiempo de relog

		Integer tiempo = new Integer(0);

		// levantamos el hilo relog que hara correr el tiempo
		Hilo_relog relog = new Hilo_relog(tiempo);

		// Leer Archivo con lista de Procesos

		// Creacion de los procesos

		// (iterar) Paso de Procesos al Hilo despachador segun su tiempo de entrada
		Hilo_despachador despachador = new Hilo_despachador(1);

	// #################  Fin Hilo Despachador ################################

	}

}
