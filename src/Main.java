import java.util.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;
import java.io.*;


public class Main {
	// hilo principal de ejecucion
	static ArrayList<Proceso> procesos = new ArrayList<Proceso>();
    static TreeMap<Integer, Proceso> procesosTreemap = new TreeMap<Integer, Proceso>();

	/*
	*Funcion para leer del archivo xml: recibe el nombre del archivo
	* Mete en el arraylist de procesos(que es global) los procesos que se leen.
	*/
	public static void readXML(String xml) {
		int cantProcess = 0;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        try {
            //Se usa el documentBuilder para convertir el archivo xml
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document dom = db.parse(xml);
            Element doc = dom.getDocumentElement();
            Proceso proceso;

            NodeList nl;

            //Toma todos los nodos que tienen el nombre de Process.
    		nl = doc.getElementsByTagName("Process");
    		cantProcess = nl.getLength();

    		//Empieza a leer la informacion detallada de cada proceso
    		for(int i = 0; i<cantProcess; i++){
    			//Convierte el nodo en elemento para poder tomar la informacion de sus hijos
    			Element eElement = (Element) nl.item(i);

    			NodeList pNodes = eElement.getElementsByTagName("ProcessType");
    			String pType = pNodes.item(0).getFirstChild().getNodeValue();

    			pNodes = eElement.getElementsByTagName("Priority");
    			int priority = Integer.parseInt(pNodes.item(0).getFirstChild().getNodeValue());

    			pNodes = eElement.getElementsByTagName("ArrivalTime");
    			int arTime = Integer.parseInt(pNodes.item(0).getFirstChild().getNodeValue());

    			pNodes = eElement.getElementsByTagName("Requirements");
    			eElement = (Element) pNodes.item(0);

    			pNodes = eElement.getElementsByTagName("Resource");
    			int cantRes = pNodes.getLength();

    			//Se crea un arraylist de los requerimientos del cpu, cada requerimiento es un par que
    			//tiene tipo y cantidad de ciclos de cpu.
    			ArrayList<Pair<String,Integer>> res = new ArrayList<Pair<String,Integer>>();
    			for(int j = 0; j<cantRes; j++) {
	    			eElement = (Element) pNodes.item(j);

	    			NodeList rNodes = eElement.getElementsByTagName("ResourceType");
	    			String valType = rNodes.item(0).getFirstChild().getNodeValue();

	    			rNodes = eElement.getElementsByTagName("UseTime");
	    			Integer value = new Integer(rNodes.item(0).getFirstChild().getNodeValue());

	    			res.add(new Pair<String,Integer>(valType,value));
	    		}

                //Se crea el proceso con sus datos y ordenan por tiempo de llegada (en un Treemap)
                proceso= new Proceso(i, pType, priority, arTime, res);

                Integer arrivalTime = (new Integer(arTime))*1000;
                while(procesosTreemap.containsKey(arrivalTime))
                    arrivalTime++;
                procesosTreemap.put(arrivalTime, proceso);

                //System.out.println("Se agrega elemento al treemap: "+proceso.toString()); 	    		 
    			
    		}

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
	}
	
	public static void main(String args[]){

		String processFile;
        Integer multiplier;
        Ventana ventana = new Ventana();
        Integer mainTime;
        Proceso proceso;
        HiloDespachador despachador = null;       

        //se despliega la ventana del simulador
        ventana.setResizable(true);

        ventana.setSize ( 600, 600 );
        ventana.setLocationRelativeTo ( null );
        ventana.setVisible ( true );

        //Ciclo principal del programa
        while(true) {
            //Esperando a que se comience, pulse el boton
            boolean start = ventana.getStart();
            boolean first = true;
            multiplier = 1;
            while(!start){
                if(first) {
                    System.out.println("Esperando start");
                    first = false;
                }
                start = ventana.getStart();
            }

            MonitorTime time = new MonitorTime();
            MonitorIO io = new MonitorIO();
            int cpus = ventana.getCantCPU();
            multiplier = ventana.getMultiplier();
            processFile = ventana.getFile();
            System.out.println("archivo:"+processFile);
            // levantamos el hilo relog que hara correr el tiempo
            HiloReloj reloj = new HiloReloj(time, multiplier);
            // Leer Archivo con lista de Procesos
            readXML(processFile);
            despachador = new HiloDespachador(cpus, procesosTreemap, time, io, ventana);
            //Ciclo de ejecucion de la simulacion.
            while(start){
                mainTime = time.getTime();

                ventana.setTime(Integer.toString(mainTime));
                while (mainTime == time.getTime());
                start = ventana.getStart();
            }
            //Si se para la simulacion se destruyen los hilos y se muestran las estadisticas
            reloj.terminate();
            Estadisticas estads = despachador.terminate();
            ventana.setStads(estads);
        }

		    
        
	}

}
