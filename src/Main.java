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
	static ArrayList<Procesos> procesos = new ArrayList<Procesos>();

	/*
	*Funcion para leer del archivo xml: recibe el nombre del archivo
	* Mete en el arraylist de procesos(que es global) los procesos que se leen.
	*/
	public static void readXML(String xml) {
		int cantProcess = 0;
		Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            //Se usa el documentBuilder para convertir el archivo xml
            DocumentBuilder db = dbf.newDocumentBuilder();

            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();
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

	    		procesos.add(new Procesos(i, pType, priority, arTime, res)); //Se crea el proceso con sus datos
    			
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

		String process_file = "process_request_file.xml";

		if(args.length <= 0) {
			System.out.println("Se necesita colocar cantidad de procesadores");
			return;
		}

		Integer tiempo = new Integer(0);

		// levantamos el hilo relog que hara correr el tiempo
		Hilo_relog relog = new Hilo_relog(tiempo);

		// Leer Archivo con lista de Procesos
        readXML(process_file);

		Hilo_despachador despachador = new Hilo_despachador(Integer.parseInt(args[0]), procesos);

	}

}
