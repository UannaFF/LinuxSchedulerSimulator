/*
*	Clase ::	HiloCPU
*	Descripcion :: Clase que define el hilo de cada cpu del simulador
*	Estado :: Incompleto
*/

public class HiloCPU extends Thread {

	int id = 0;
	int cuantum = 4;
	MonitorCL colaListos = null;
	MonitorTime time = null;
	MonitorIO colaIO = null;
	int min_granularity = 4;
	int period = 20;

	HiloCPU( MonitorCL colaListos, MonitorTime time, int id, int cuantum, MonitorIO monIO){
		super("CPU");
		this.colaListos = colaListos;
		this.time = time;
		this.id = id;
		this.cuantum = cuantum;
		this.colaIO = monIO;
		//this.colaIO = colaIO;
		System.out.println("Levantando Hilo CPU: " + this + " id :: " + this.id);

    start();
	}

	public void run(){
		Proceso procesoExec = null;
		int auxiliar = 0;
		int cpuTime,firstTime,timeA;
		int llegadaUntouched;
		boolean termino = true;

		while(true){
			cpuTime = time.getTime();
			llegadaUntouched = cpuTime;
			termino = true;

			//Toma el proceso y consume su tiempo
			Proceso proceso = colaListos.getProceso();
			if(proceso != null) {
				firstTime = cpuTime+min_granularity;
				cpuTime = cpuTime + proceso.getFirstSource().getR();
				timeA = time.getTime();
				System.out.println("CPU::Entro a CPU con proceso "+proceso+",llegada: "+llegadaUntouched+", firstTime: "+firstTime+", cpuTime: "+cpuTime);
				while (cpuTime > timeA){
					//System.out.println("CPU::Tiempo de sacada : "+firstTime);
					//System.out.println("CPU::Tiempo de iteracion : "+timeA);
					if(firstTime <= timeA && cpuTime >= firstTime) {
						//Le resta el tiempo al resource
						System.out.println("CPU::se saca al proceso "+proceso+"en tiempo "+timeA+", que entro en tiempo "+llegadaUntouched);
						colaListos.devolverProceso(proceso, min_granularity);
						termino = false;
						break;
					}
					timeA = time.getTime();
				}
				//Si se termino todo el tiempo de CPU, se manda a la cola de IO
				if(termino) {
					proceso.removeFirstSource();
					Pair<String, Integer> source = proceso.getFirstSource();
					if(source != null) {
						proceso.setArrivalTime(time.getTime()+source.getR());
						colaIO.addProcesoIO(proceso);
						System.out.println("CPU::Pasa a la cola de IO");
					} else {
						System.out.println("CPU::Proceso "+proceso+" termino, no tiene mas recursos");
					}
				}
			}
		}
	
	}
}