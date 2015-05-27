import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;

public class Ventana extends JFrame{
	private JLabel time, timeLabel, title, sepa, cpuCantidad, procesosTitle,tiempoMultiplicador, fileLabel, tiempoOcio, tiempoEjecucion, tiempoEspera;
	private JButton startButton;
	private boolean start = false;
	private JSpinner cpu;
   private JSpinner multiplierSp;
   private JTextField fileInput = new JTextField(70);
	private int cantCPU = 1;
   private int multiplier = 1;
   private String file = "";
	private int offset[] = {15,185};
	
	public Ventana() {

	setLayout(null);

	title=new JLabel("Planificador de Linux CFS");
   fileLabel = new JLabel("Archivo XML de procesos: ");
	timeLabel=new JLabel("Time:");
	time=new JLabel("");   
   tiempoOcio=new JLabel(""); 
   tiempoEjecucion=new JLabel(""); 
   tiempoEspera=new JLabel(""); 
	cpuCantidad = new JLabel("Cantidad de CPU's: ");
   tiempoMultiplicador = new JLabel("Relentizacion: ");
	startButton = new JButton("Start");

	SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 16, 1);
	cpu = new JSpinner(spinnerModel);
   SpinnerModel spinnerMultiplier = new SpinnerNumberModel(1, 1, 100, 1);
   multiplierSp = new JSpinner(spinnerMultiplier);
	startButton.setActionCommand("Start");
	startButton.addActionListener(new ButtonClickListener()); 

	title.setBounds(210,0,400,20);
   fileLabel.setBounds(15,20,200,40);
   tiempoOcio.setBounds(15,200,600,30);
   tiempoEjecucion.setBounds(15,230,600,30);
   tiempoEspera.setBounds(15,260,600,30);
   fileInput.setBounds(200,20,250,30);
	timeLabel.setBounds(20,100,60,40);
	time.setBounds(80,100,60,40);
	startButton.setBounds(470,70,100,40);
	cpu.setBounds(170,70,50,30);
	drawSeparador(15,55);
	cpuCantidad.setBounds(15,80,150,10);
   tiempoMultiplicador.setBounds(230,80,150,10);
   multiplierSp.setBounds(345,70,50,30);
   fileInput.setText("process_request_file.xml");


	add(cpuCantidad);
	add(cpu);
	add(startButton);
	add(title);
	add(time);
	add(timeLabel);
   add(tiempoMultiplicador);
   add(multiplierSp);
   add(fileLabel);
   add(fileInput);
   add(tiempoOcio);
   add(tiempoEjecucion);
   add(tiempoEspera);

	drawSeparador(15,140);
	JLabel proc = new JLabel("Estadísticas Proceso");
	proc.setBounds(15,150,200,20);
	add(proc);
	drawSeparador(15,175);
}
	public void setTime(String time){
		this.time.setText(time);
	}

	 private class ButtonClickListener implements ActionListener{
      public void actionPerformed(ActionEvent e) {
         String command = e.getActionCommand();  
         if( command.equals( "Start" ))  {
            startButton.setText("Stop");
            start = true;
            cantCPU = (Integer)cpu.getValue();
            multiplier = (Integer)multiplierSp.getValue();
            file = fileInput.getText();
            startButton.setActionCommand("Stop");
         } else if( command.equals( "Stop" )) {
         	startButton.setText("Start");
            start = false;
            startButton.setActionCommand("Start");
         }
      }		

   }

   public boolean getStart() {
      	return this.start;
   }

   public int getCantCPU() {
   		return this.cantCPU;
   }

   public String getFile() {
         return this.file;
   }

   public int getMultiplier() {
         return this.multiplier;
   }

   public void changeTitle() {
   		title.setText("CHANGED");
   }

   public void drawSeparador(int x, int y) {
   		JLabel sepaA = new JLabel("");
   		sepaA.setOpaque(true);
		sepaA.setBackground(Color.BLACK);
   		sepaA.setBounds(x,y,550,5);
   		add(sepaA);
   }

   public void setStatProceso(Proceso p, int t) {
   		System.out.println("Entro a setStATPROCESO");
   		JLabel pid = new JLabel("Proceso " + p.getPID());
   		JLabel tSalida = new JLabel("               Tiempo salida :: " + t);
   		JLabel tEspera = new JLabel("               Tiempo de espera :: " + p.getTiempoEspera());
   		pid.setBounds(offset[0],offset[1], 500,10);
   		tSalida.setBounds(offset[0],offset[1]+10, 500,10);
   		tEspera.setBounds(15,200, 500,10);
   		add(pid);
   		add(tSalida);
   		add(tEspera);
   }

   public void setStads(Estadisticas estadisticas){
   		this.tiempoEspera.setText("Tiempo espera promedio:"+Double.toString(estadisticas.getEspera()));
         this.tiempoOcio.setText("Tiempo de ocio promedio:"+Double.toString(estadisticas.getOcio()));
         this.tiempoEjecucion.setText("Tiempo de ejecucion promedio:"+Double.toString(estadisticas.getEjecucion()));
   }
}