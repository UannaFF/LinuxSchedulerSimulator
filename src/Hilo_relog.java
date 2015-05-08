/*
*	Clase ::	Hilo_relog
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/

public class Hilo_relog extends Thread {
	
  Integer tiempo = null;

  public Hilo_relog(Integer tiempo){
    super("Relog");
    this.tiempo = tiempo;
    System.out.println("Levantado Hilo Relog = " + this);
    start();
  }

  public void run(){ 

    int contador = 0;
    
    while(true){ 
      if (contador == 100) {
        tiempo++;
        contador = 0;
      }else{
        contador++;
      }
    }
    
  }
}