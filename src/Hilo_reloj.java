/*
*	Clase ::	Hilo_reloj
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/

public class Hilo_reloj extends Thread {
	
  Integer tiempo = null;

  public Hilo_reloj(Integer tiempo){
    super("Reloj");
    this.tiempo = tiempo;
    System.out.println("Levantado Hilo Reloj = " + this);
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