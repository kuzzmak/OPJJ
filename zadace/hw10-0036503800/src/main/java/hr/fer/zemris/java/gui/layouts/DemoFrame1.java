package hr.fer.zemris.java.gui.layouts;

import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred za prikaz funkcionalnosti layout managera {@code CalcLayout}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class DemoFrame1 extends JFrame{
	
	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 * 
	 */
	public DemoFrame1() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
		pack();
	}

	/**
	 * Funkcija za stvaranje labele s određenim tekstom.
	 * 
	 * @param text tekst ispisan u labeli
	 * @return objekt tipa {@code JLabel}
	 */
	private JLabel l(String text) {
		JLabel l = new JLabel(text);
		l.setBackground(Color.YELLOW);
		l.setOpaque(true);
		return l;
	}
	
	/**
	 * Funkcija za inicijalizaciju grafičkog sučelja.
	 * 
	 */
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
	
	/**
	 * Metoda iz koje kreće izvođenje glavnog programa.
	 *  
	 * @param args argumenti glavnog programa
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new DemoFrame1().setVisible(true));
	}
	
}
