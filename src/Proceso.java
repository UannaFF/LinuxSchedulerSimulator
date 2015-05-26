/*
*	Clase ::	Proceso
*	Descripcion :: Clase que representa la estructura de datos de un 
*				   proceso
*	Estado :: Incompleto
*/
import java.util.ArrayList;
import java.lang.Math;


public class Proceso {

	public int pid = 0;
	public String type = "";
	public Integer arrivalTime = 0;
	public int priority = 0;
	public ArrayList<Pair<String,Integer>> resources = null;
	public Integer actual_cpu = null;
	public Double peso = 0.0;
	public Double vruntime = 0.0;
	public int timeSlice = 0;

	public Proceso(int pid, String s, int prior, Integer arriveT, ArrayList<Pair<String,Integer>> pAr){

		this.pid = pid;
		this.type = s;
		this.arrivalTime = arriveT;
		this.priority = prior;
		this.resources = pAr;
		this.peso = (1024/java.lang.Math.pow(1.25,prior));
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
			    res.setR(a_t-t);
			    this.arrivalTime = 0;
			    //System.out.println(getFirstSource().toString());
			} else {
				System.out.println("El tamano a restar es mayor que el actual");
			}
		}
	}

	public String toString(){
		return "( " + this.pid + ", arrivo=" + this.arrivalTime+" , prioridad="+ this.priority +", peso = "+this.peso+")"; 
	}

	public Integer getPID() {
		return this.pid;
	}

	public Double getPeso() {
		return this.peso;
	}

	public void setVruntime(double v) {
		this.vruntime = v;
	}

	public double getVruntime() {
		return this.vruntime;
	}

	public void setTimeSlice(int time) {
		this.timeSlice = time;
	}

	public int getTimeSlice() {
		return this.timeSlice;
	}
}