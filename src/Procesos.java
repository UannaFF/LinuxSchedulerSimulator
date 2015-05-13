/*
*	Clase ::	Procesos
*	Descripcion :: Clase que representa la estructura de datos de un 
*				   proceso
*	Estado :: Incompleto
*/
import java.util.ArrayList;


public class Procesos {
	public int pid = 0;
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
	}

	public String toString(){
		return "( " + this.pid + ", arrivo=" + this.arrivalTime+" , prioridad="+ this.priority +")"; 
	}
}