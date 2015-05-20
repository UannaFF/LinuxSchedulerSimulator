import javax.swing.JLabel;
import javax.swing.JFrame;

public class Ventana extends JFrame{
	private JLabel time, timeLabel, title;
	
	public Ventana() {

	setLayout(null);
	title=new JLabel("¡Simulador de Linux CPU Scheduler!");
	timeLabel=new JLabel("Time:");
	time=new JLabel("");
	title.setBounds(100,20,400,40);
	timeLabel.setBounds(20,120,60,40);
	time.setBounds(80,120,60,40);
	add(title);
	add(time);
	add(timeLabel);

}
	public void setTime(String time){
		this.time.setText(time);
	}
}