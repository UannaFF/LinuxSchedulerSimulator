/*
*	Clase ::	HiloCPU
*	Descripcion :: Clase que define el hilo de cada cpu del simulador
*	Estado :: Incompleto
*/
import javax.swing.*;
import java.awt.event.*;

public class HiloCPU extends Thread {

	int id = 0;
	int cuantum = 4;
	MonitorCL colaListos = null;
	MonitorTime time = null;
	MonitorIO colaIO = null;
	int period = 20;
	boolean running = true;
	int tiempoOcioso = 0;
	VentanaCPU ventanaCPU;
	Ventana vent;
	String estads = "";
	int ultSalida = 0;

	// ESTADISTICA tiempo de ocio del cpu
	int pivoteOcio = 0;
	int tiempoOcio = 0;

	HiloCPU( MonitorCL colaListos, MonitorTime time, int id, int cuantum, MonitorIO monIO, Ventana v){

		super("CPU");
		this.colaListos = colaListos;
		this.time = time;
		this.id = id;
		this.cuantum = cuantum;
		this.colaIO = monIO;
		this.ventanaCPU = new VentanaCPU(id);
		this.vent = v;

		//this.colaIO = colaIO;
		System.out.println("Levantando Hilo CPU: " + this + " id :: " + this.id);
		//Creando ventana
		ventanaCPU.setBounds(700,0,500,500); // (posx,posy,width,height)
        //ventanaCPU.setVisible(true);
        ventanaCPU.setResizable(true);
		ventanaCPU.setVisible ( true );
		//setDaemon(true);

    start();
	}

	public int terminate() {
		running = false;
		//String tiempoO = "\n Estadistica CPU "+this.id+":: Tiempo ocioso -> " + tiempoOcio + "\n";
		//System.out.println(tiempoO);
		System.out.println("Tiempo de salida del ultimo proceso: "+ultSalida);
		return tiempoOcio;
	}

	public void run(){
		Proceso procesoExec = null;
		int auxiliar = 0;
		int cpuTime,firstTime,timeA;
		int llegadaUntouched;
		boolean termino = true;
		int lastTime = time.getTime();

		while(running){
			cpuTime = time.getTime();
			llegadaUntouched = cpuTime;
			termino = true;
			//Toma el proceso y consume su tiempo
			Proceso proceso = colaListos.getProceso();

			if(proceso != null) {
				// ESTADISTICA indica el cierre de un periodo de espera
				proceso.finEspera(time.getTime());

				firstTime = cpuTime+proceso.getTimeSlice();				

				cpuTime = cpuTime + proceso.getFirstSource().getR();
				timeA = time.getTime();
				ventanaCPU.setLog(timeA,proceso.getPID(),"ha llegado.");
				//System.out.println("CPU::LLEGOOOOEntro a CPU con proceso "+proceso+",llegada: "+llegadaUntouched+", firstTime: "+firstTime+", cpuTime: "+cpuTime);
				while (cpuTime > timeA){
					//System.out.println("CPU::Tiempo de iteracion : "+timeA);
					if(firstTime <= timeA && cpuTime >= firstTime) {
						//Le resta el tiempo al resource
						System.out.println("slice:"+proceso.getTimeSlice());
						System.out.println("CPU::se saca al proceso "+proceso+"en tiempo "+timeA+", que entro en tiempo "+llegadaUntouched);

						colaListos.devolverProceso(proceso, proceso.getTimeSlice(),timeA-llegadaUntouched, time.getTime());

						termino = false;
						break;
					}
					timeA = time.getTime();
				}
				//Si se termino todo el tiempo de CPU, se manda a la cola de IO
				if(termino) {
					ventanaCPU.setLog(timeA,proceso.getPID(),"termino su tiempo de CPU.");
					proceso.removeFirstSource();
					Pair<String, Integer> source = proceso.getFirstSource();
					if (source == null){
						proceso.setArrivalTime(time.getTime());
					}else{
						proceso.setArrivalTime(time.getTime()+source.getR());
					}
					
					proceso.setVruntime(proceso.getVruntime() + (timeA-llegadaUntouched) * (1024/proceso.getPeso()));
					colaIO.addProcesoIO(proceso);
					ventanaCPU.setLog(timeA,proceso.getPID(),"pasa a esperar IO.");
					System.out.println("CPU::Pasa a la cola de IO");

					

					/*
					if(source != null) {
						proceso.setArrivalTime(time.getTime()+source.getR());
						proceso.setVruntime(proceso.getVruntime() + (timeA-llegadaUntouched) * (1024/proceso.getPeso()));
						colaIO.addProcesoIO(proceso);
						ventanaCPU.setLog(timeA,proceso.getPID(),"pasa a esperar IO.");
						System.out.println("CPU::Pasa a la cola de IO");
					} else {
						//System.out.println("\n CPU::Proceso "+proceso+" termino, no solicita mas recursos");
						ventanaCPU.setLog(timeA,proceso.getPID(),"ha terminado de ejecutarse.");
						String estadisticas = "\n Estadistica :: Proceso " + proceso.getPID() + "\n";
						estadisticas += "               Tiempo salida :: " + time.getTime()+ "\n";
						estadisticas += "               Tiempo de espera :: " + proceso.getTiempoEspera() + "\n";
						System.out.println(estadisticas);
						estads += estadisticas;
					}
					*/
				} else {
					ventanaCPU.setLog(timeA,proceso.getPID(),"se ha sacado para darle paso a otro.");
					pivoteOcio = cpuTime;
				}
				ultSalida = timeA;
			} else {
				if (pivoteOcio != cpuTime) {
					tiempoOcio += cpuTime-pivoteOcio;
					pivoteOcio = cpuTime ;
				}
			}
		}
	
	}
}