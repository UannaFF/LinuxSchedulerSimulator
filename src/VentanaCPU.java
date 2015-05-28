import javax.swing.*;
import java.awt.event.*;
import java.awt.GridLayout;

public class VentanaCPU extends JFrame{
	private JLabel time, timeLabel, title;
	private JButton startButton;
	private boolean start = false;
	private JSpinner cpu;
	private int cantCPU = 1;
	private JPanel container;
	
	public VentanaCPU(int id) {
		setLayout(null);
		title=new JLabel("CPU "+id+": ");
		//title.setBounds(10,10,60,5);
		container = new JPanel();
		//container.setSize(300,900);
		container.setLayout(new GridLayout(0, 1));
		//container.pack();
		container.add(title);
		JScrollPane jsp = new JScrollPane(container);
		jsp.setSize(500,500);
		add(jsp);
	}

	public void setLog(int t, int pid, String men) {
		JLabel log = new JLabel("Tiempo "+t+":: El proceso "+pid+" "+men);
		container.add(log);
		container.revalidate();
	}

	public void close() {
		this.dispose();
	}
}