import javax.swing.*;
import java.awt.event.*;
import java.awt.Color;

public class Ventana extends JFrame{
	private JLabel time, timeLabel, title, sepa, cpuCantidad, procesosTitle;
	private JButton startButton;
	private boolean start = false;
	private JSpinner cpu;
	private int cantCPU = 1;
	private int offset[] = {150,20};
	
	public Ventana() {

	setLayout(null);
	title=new JLabel("Planificador de Linux CFS");
	timeLabel=new JLabel("Time:");
	time=new JLabel("");
	cpuCantidad = new JLabel("Cantidad de CPU's: ");
	startButton = new JButton("Start");
	SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
	cpu = new JSpinner(spinnerModel);
	startButton.setActionCommand("Start");
	startButton.addActionListener(new ButtonClickListener()); 
	title.setBounds(210,20,400,40);
	timeLabel.setBounds(20,100,60,40);
	time.setBounds(80,100,60,40);
	startButton.setBounds(470,70,100,40);
	cpu.setBounds(170,70,100,40);
	drawSeparador(15,55);
	cpuCantidad.setBounds(15,80,150,10);
	add(cpuCantidad);
	add(cpu);
	add(startButton);
	add(title);
	add(time);
	add(timeLabel);
	drawSeparador(15,140);
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

   public void setProceso(Proceso p) {

   }
}