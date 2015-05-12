/*
*	Clase ::	Procesos
*	Descripcion :: Clase que representa la estructura de datos de un 
*				   proceso
*	Estado :: Incompleto
*/


public class Procesos {
	public int pid = 0;
  public int tiempo_cpu = 0;
  public int tiempo_io = 0;

	public Procesos(int pid,int tiempo_cpu, int tiempo_io){
		this.pid = pid;
    this.tiempo_cpu = tiempo_cpu;
    this.tiempo_io = tiempo_io;
	}

	public String toString(){
		return "( " + this.pid + ", cpu=" + this.tiempo_cpu+" , io="+ this.tiempo_io +")"; 
	}
}