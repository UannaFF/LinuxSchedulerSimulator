/*
*	Clase ::	Proceso
*	Descripcion :: Clase que representa la estructura de datos de un 
*				   proceso
*	Estado :: Incompleto
*/
import java.util.ArrayList;


public class Proceso {

	public int pid = 0;
	public String type = "";
	public Integer arrivalTime = 0;
	public int priority = 0;
	public ArrayList<Pair<String,Integer>> resources = null;

	public Proceso(int pid, String s, int prior, Integer arriveT, ArrayList<Pair<String,Integer>> pAr){

		this.pid = pid;
		this.type = s;
		this.arrivalTime = arriveT;
		this.priority = prior;
		this.resources = pAr;
		
	}

	public Integer getArrivalTime(){
		return this.arrivalTime;
	}

	public void setArrivalTime(Integer arrivalTime){
		this.arrivalTime = arrivalTime;
	}

	public Pair<String,Integer> getFirstSource(){
		if (!resources.isEmpty()){
			return resources.get(0);
		}
		return null;
	}

	public void removeFirstSource(){
		if (!resources.isEmpty()){
			resources.remove(0);
		}
	}

	public String toString(){
		return "( " + this.pid + ", arrivo=" + this.arrivalTime+" , prioridad="+ this.priority +")"; 
	}
}