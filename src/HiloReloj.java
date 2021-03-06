/*
*	Clase ::	HiloReloj
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/

public class HiloReloj extends Thread {
	
  MonitorTime time = null;
  Integer multiplier;
  boolean running = true;
  public HiloReloj(MonitorTime time, Integer multiplier){
    super("Reloj");
    this.time = time;
    this.multiplier= multiplier;
    System.out.println("Levantado Hilo Reloj = " + this);
    //setDaemon(true);
    start();
  }

  public void terminate() {
    running = false;
  }

  public void run(){ 

    int contador = 0;
    
    while(running){ 
      try{
        this.sleep(100 * this.multiplier);
        this.time.tic();
      }catch (InterruptedException e) {
        System.out.println("Error :: actualizando el tiempo de la simulacion");
      }
      
    }    
  }
}