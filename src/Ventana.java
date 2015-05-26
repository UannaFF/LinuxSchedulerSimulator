import javax.swing.*;
import java.awt.event.*;

public class Ventana extends JFrame{
	private JLabel time, timeLabel, title;
	private JButton startButton;
	private boolean start = false;
	private JSpinner cpu;
	private int cantCPU = 1;
	
	public Ventana() {

	setLayout(null);
	title=new JLabel("Planificador de Linux CFS");
	timeLabel=new JLabel("Time:");
	time=new JLabel("");
	startButton = new JButton("Start");
	SpinnerModel spinnerModel = new SpinnerNumberModel(1, 1, 20, 1);
	cpu = new JSpinner(spinnerModel);
	startButton.setActionCommand("Start");
	startButton.addActionListener(new ButtonClickListener()); 
	title.setBounds(210,20,400,40);
	timeLabel.setBounds(20,300,60,40);
	time.setBounds(80,300,60,40);
	startButton.setBounds(470,60,100,40);
	cpu.setBounds(20,60,100,40);
	add(cpu);
	add(startButton);
	add(title);
	add(time);
	add(timeLabel);

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
}