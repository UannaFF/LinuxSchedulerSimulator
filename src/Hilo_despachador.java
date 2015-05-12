/*
*	Clase ::	Hilo_Despachador
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/

public class Hilo_despachador extends Thread {
	
  int num_cpu = 0;
  Monitor_CL colas[] = null;
  Procesos pendientes[] = null;

	Hilo_despachador(int num_cpu){
		super("Despachador");

    colas = new Monitor_CL[num_cpu];
    this.num_cpu = num_cpu;

     for (int i = 0; i < num_cpu ; i++) {
      Hilo_CPU cpu = new Hilo_CPU(colas[i]);
    }
    // levantamos los cpus requeridos y les asignamos sus colas de procesos listos
    
		System.out.println("Levantando Hilo Despachador: " + this);
		start(); // Arrancamos el despachador
	}

  public void run(){ 
    
   

    while(true){
      // por definir 
    }
    
  }


  public void distribuir(Procesos proceso){
    System.out.println("hola");
  }

}