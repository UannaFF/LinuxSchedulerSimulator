/*
*	Clase ::	HiloDespachador
*	Descripcion :: Clase que define el hilo despachador del simulador
*	Estado :: Incompleto
*/
import java.util.ArrayList;

public class HiloDespachador extends Thread {
	
  int numCpu = 0;
  MonitorCL colas[] = null;
  MonitorTime time = null;
  ArrayList<Proceso> procesos = null;

	HiloDespachador(int numCpu, ArrayList<Proceso> ps, MonitorTime time){
		super("Despachador");

    this.colas = new MonitorCL[numCpu];
    this.numCpu = numCpu;
    this.time = time;

    //Contiene todos los procesos
    this.procesos = ps;

    for (int i = 0; i < numCpu ; i++) {
      HiloCPU cpu = new HiloCPU(colas[i], time, i , 4);
    }
    // levantamos los cpus requeridos y les asignamos sus colas de procesos listos

    //-----------------------asignar procesos de la cola general a la cola de procesos de cada cpu
    
		System.out.println("Levantando Hilo Despachador: " + this);
		start(); // Arrancamos el despachador
	}

  public void run(){ 
    
    while(true){
      /* -Debe consultar frecuentemente el tiempo esperando q sea el turno del siguiente 
      proceso a ser despachado a una de las colas de los procesadores
        -Cuando sea el tiempo debe recorrer el arreglo de colas buscando cual tiene menor peso y asignarle 
        el proceso a esa cola si se trata de operaciones de CPU
        -Definir q pasara si son operaciones de entrada y salida*/
    
      if (!procesos.isEmpty()) {
        Proceso entrante = procesos.get(0);

        int tiempo = time.getTime();

        if (tiempo >= entrante.arrivalTime) {
          System.out.println("tiempo:: "+tiempo+" >> Un proceso solicita recursos");
          procesos.remove(0);
          distribuir(entrante);         
        }
      }
    }
    
  }


  public void distribuir(Proceso proceso){

    // buscamos la cola con menos carga de procesos
    int tam = colas.length;
    int iter = 0;
    MonitorCL minimo = null;

    if (colas.length > 0) {
      
      minimo = colas[0];
      iter++;

      while(iter < tam){
        int temp = colas[iter].getCarga();
        if (minimo.getCarga() > temp) {
          minimo = colas[iter];
        }
        iter++;
      }

      minimo.addProcesoListo(0.0,proceso);
    }
  }

}