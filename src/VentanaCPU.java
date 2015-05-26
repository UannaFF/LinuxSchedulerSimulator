import javax.swing.*;
import java.awt.event.*;
import java.awt.FlowLayout;

public class VentanaCPU extends JFrame{
	private JLabel time, timeLabel, title;
	private JButton startButton;
	private boolean start = false;
	private JSpinner cpu;
	private int cantCPU = 1;
	private JPanel container;
	private int offset[] = {10,30};
	
	public VentanaCPU(int id) {
		setLayout(null);
		title=new JLabel("CPU "+id+": ");
		//title.setBounds(10,10,60,5);
		container = new JPanel();
		container.setSize(300,900);
		container.setLayout(new FlowLayout());
		//container.pack();
		container.add(title);
		JScrollPane jsp = new JScrollPane(container);
		jsp.setSize(300,300);
		add(jsp);
	}

	public void setLog(int t, int pid, String men) {
		JLabel log = new JLabel("Tiempo "+t+":: El proceso "+pid+" "+men);
		log.setBounds(offset[0], offset[1],250,5);
		container.add(log);
		container.revalidate();
		offset[1] = offset[1] + 10;
	}
}