/*
*	Clase ::	Procesos
*	Descripcion :: Clase que representa la estructura de datos de un 
*				   proceso
*	Estado :: Incompleto
*/
import java.util.ArrayList;


public class Procesos {
	public int pid = 0;
<<<<<<< HEAD
  public int tiempo_cpu = 0;
  public int tiempo_io = 0;

	public Procesos(int pid,int tiempo_cpu, int tiempo_io){
		this.pid = pid;
    this.tiempo_cpu = tiempo_cpu;
    this.tiempo_io = tiempo_io;
=======
	public String type = "";
	public int arrivalTime = 0;
	public int priority = 0;
	public ArrayList<Pair<String,Integer>> resources = null;

	public Procesos(int pid, String s, int prior, int arriveT, ArrayList<Pair<String,Integer>> pAr){
		this.pid = pid;
		this.type = s;
		this.arrivalTime = arriveT;
		this.priority = prior;
		this.resources = pAr;
>>>>>>> 3c8770168b2ae7c81a03534ca706f20800b0ba0e
	}

	public String toString(){
		return "( " + this.pid + ", cpu=" + this.tiempo_cpu+" , io="+ this.tiempo_io +")"; 
	}
}