/*
*	Clase ::	Hilo_Despachador
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/
import java.util.ArrayList;

public class Hilo_despachador extends Thread {
	
  int num_cpu = 0;
  Monitor_CL colas[] = null;
  ArrayList<Proceso> procesos = null;

	Hilo_despachador(int num_cpu, ArrayList<Proceso> ps){
		super("Despachador");

    colas = new Monitor_CL[num_cpu];
    this.num_cpu = num_cpu;

    //Contiene todos los procesos
    this.procesos = ps;

    Monitor_CL colas[] = new Monitor_CL[num_cpu];

    for (int i = 0; i < num_cpu ; i++) {
      Hilo_CPU cpu = new Hilo_CPU(colas[i]);
    }
    // levantamos los cpus requeridos y les asignamos sus colas de procesos listos

    //-----------------------asignar procesos de la cola general a la cola de procesos de cada cpu
    
		System.out.println("Levantando Hilo Despachador: " + this);
		start(); // Arrancamos el despachador
	}

  public void run(){ 
    
   

    while(true){
      /* -Debe consultar frecuentemente el tiempo esperando q sea el turno del siguiente 
      proceso a ser despachado a una de las colas de los procesadores
        -Cuando sea el tiempo debe recorrer el arreglo de colas buscando cual tiene menor peso y asignarle 
        el proceso a esa cola si se trata de operaciones de CPU
        -Definir q pasara si son operaciones de entrada y salida*/

    }
    
  }


  public void distribuir(Proceso proceso){
    System.out.println("hola");
  }

}