/*
*	Clase ::	Hilo_Despachador
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/
import java.util.ArrayList;

public class Hilo_despachador extends Thread {
	
  Monitor_CL colas[] = null;
  ArrayList<Procesos> procesos = null;

	Hilo_despachador(int num_cpu, ArrayList<Procesos> ps){
		super("Despachador");

    //Contiene todos los procesos
    this.procesos = ps;

    Monitor_CL colas[] = new Monitor_CL[num_cpu];

    // levantamos los cpus requeridos y les asignamos sus colas de procesos listos
    for (int i = 0; i < num_cpu ; i++) {
      Hilo_CPU cpu = new Hilo_CPU(colas[i]);
    }

		System.out.println("Levantando Hilo Despachador: " + this);
		start(); // Arrancamos el despachador
	}

  public void run(){ 
    
    while(true){
   //   System.out.println("Se arranco un hilo despachador : " + this);
    }
    
  }

}