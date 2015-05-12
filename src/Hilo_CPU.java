/*
*	Clase ::	Hilo_CPU
*	Descripcion :: Clase que define el hilo de cada cpu del simulador
*	Estado :: Incompleto
*/

public class Hilo_CPU extends Thread {

	Monitor_CL colaListos = null;

	Hilo_CPU( Monitor_CL colaListos){
		super("CPU");
		this.colaListos = colaListos;
		System.out.println("Levantando Hilo CPU: " + this);

    start();
	}

	public void run(){
		Procesos proceso_exec = null;
		while(true){
		/*	proceso_exec = colaListos.get_proceso();
			if (proceso_exec != null) {
				try{
					sleep(10);
				} catch (InterruptedException e ) {
					System.out.println("Error al dormi el cpu");
				}
			}*/
		}
	
	}
}