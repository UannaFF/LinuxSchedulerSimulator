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

	HiloCPU( MonitorCL colaListos, MonitorTime time, int id, int cuantum){
		super("CPU");
		this.colaListos = colaListos;
		this.time = time;
		this.id = id;
		this.cuantum = cuantum;

		System.out.println("Levantando Hilo CPU: " + this + " id :: " + this.id);

    start();
	}

	public void run(){
		Proceso procesoExec = null;
		int auxiliar = 0;
		int cpuTime;

		while(true){
			cpuTime = time.getTime();
			
			//System.out.println("CPU ["+ this.id +"] time :: " + cpuTime);	
			while (cpuTime == time.getTime());
		}
	
	}
}