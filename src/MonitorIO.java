/*
*	Clase ::	MonitorIO
*	Descripcion :: Clase que define al monitor para la cola de IO del 
*				   simulador 
*	Estado :: Incompleto
*/

import java.util.*;

public class MonitorIO{

  	private ArrayList<Proceso> ioList = new ArrayList<Proceso>();

	public MonitorIO(){
	  
	}

	synchronized void addProcesoIO(Proceso pro){
		this.ioList.add(pro);
	}

	synchronized ArrayList<Proceso> getProcesosIO() {
		ArrayList<Proceso> ioTemp = this.ioList;
		this.ioList = new ArrayList<Proceso>();
		return ioTemp;
	}
}