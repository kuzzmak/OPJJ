package hr.fer.zemris.java.gui.prim;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Grafički program koji prikazuje dvije liste koje su povezane
 * istim modelom i gdje se pritiskom na gumb "sljedeći" ažurira
 * vrijednost svakog pretplaćenog slušaća, u ovom slučaju
 * svako od lista.
 * 
 * @author Antonio Kuzminski
 *
 */
public class PrimDemo extends JFrame{
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 */
	public PrimDemo() {
		setSize(300, 300);
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		initGUI();
	}

	/**
	 * Funkcija za incijalizaciju grafičkog sučelja.
	 * 
	 */
	public void initGUI() {
		
		Container cp = getContentPane();
		PrimListModel model = new PrimListModel();
		
		JList<Integer> list1 = new JList<>(model);
		JList<Integer> list2 = new JList<>(model);
		
		JPanel middle = new JPanel(new GridLayout(1, 0));
		middle.add(new JScrollPane(list1));
		middle.add(new JScrollPane(list2));
		
		cp.add(middle, BorderLayout.CENTER);
		
		JButton button = new JButton("sljedeći");
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				model.next();
			}
		});
		
		cp.add(button, BorderLayout.SOUTH);
	}
	
	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args argumenti glavnog programa
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
	}
}
