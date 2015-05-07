/*
*	Clase ::	Procesos
*	Descripcion :: Clase que representa la estructura de datos de un 
*				   proceso
*	Estado :: Incompleto
*/


public class Procesos {
	public int pid = 0;

	public Procesos(int pid){
		this.pid = pid;
	}

	public String toString(){
		return "( " + this.pid + " )"; 
	}
}