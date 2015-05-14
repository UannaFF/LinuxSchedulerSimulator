/*
*	Clase ::	MonitorTime
*	Descripcion :: Clase que define al monitor el tiempo del 
*				   simulador 
*	Estado :: falta agregar el wait y el notify
*/

import java.util.*;

public class MonitorTime{

  private int time = 0; //indicador del tiempo
  private boolean ocupado = false;

  //retorna el tiempo actual del simulador
	synchronized int getTime(){    

    	return this.time;
	}

  //incrementa el tiempo
	synchronized void tic(){

    	this.time++;	
	}
	
}
