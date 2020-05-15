package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

public class DemoFrame1 extends JFrame{
	
	private static final long serialVersionUID = 1L;

	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		//setSize(500, 500);
		initGUI();
		pack();
	}

	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	
	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(3));
		cp.add(l("tekst 1"), new RCPosition(1, 1));
		cp.add(l("tekst 2"), new RCPosition(2, 3));
		cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
		cp.add(l("tekst kraći"), new RCPosition(4, 2));
		cp.add(l("tekst srednji"), new RCPosition(4, 5));
		cp.add(l("tekst"), new RCPosition(4, 7));
		cp.add(l("tekstom"), new RCPosition(5, 7));
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new DemoFrame1().setVisible(true));
		
//		JPanel p = new JPanel(new CalcLayout(2));
//		JLabel l1 = new JLabel(""); 
//		l1.setPreferredSize(new Dimension(108, 15));
//		JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16, 30));
//		p.add(l1, new RCPosition(1,  1));
//		p.add(l2, new RCPosition(3, 3));
//		Dimension dim = p.getPreferredSize();
//		System.out.println(dim.width + ", " + dim.height);
	}
	
}
