/*
*	Clase ::	HiloCPU
*	Descripcion :: Clase que define el hilo de cada cpu del simulador
*	Estado :: Incompleto
*/

public class HiloCPU extends Thread {

	MonitorCL colaListos = null;
	MonitorTime time = null;

	HiloCPU( MonitorCL colaListos, MonitorTime time){
		super("CPU");
		this.colaListos = colaListos;
		this.time = time;
		System.out.println("Levantando Hilo CPU: " + this);

    start();
	}

	public void run(){
		Proceso procesoExec = null;
		while(true){
		/*	procesoExec = colaListos.getProceso();
			if (procesoExec != null) {
				try{
					sleep(10);
				} catch (InterruptedException e ) {
					System.out.println("Error al dormir el cpu");
				}
			}*/
		}
	
	}
}