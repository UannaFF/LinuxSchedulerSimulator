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
	public Integer actual_cpu = null;

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

	public Integer getActualCPU() {
		return this.actual_cpu;
	}

	public void setActualCPU(int cpu) {
		this.actual_cpu = cpu;
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

	public void restarFirstResource(Integer t) {
		Pair<String, Integer> res = resources.get(0);
		if(res.getL().equals("CPU")){
		    Integer a_t = res.getR();
		    if(a_t > t) {
			    System.out.println("Source antes: "+t);
			    res.setR(a_t-t);
			} else {
				System.out.println("El tamano a restar es mayor que el actual");
			}
		}
	}

	public String toString(){
		return "( " + this.pid + ", arrivo=" + this.arrivalTime+" , prioridad="+ this.priority +")"; 
	}

	public Integer getPID() {
		return this.pid;
	}
}