package hr.fer.zemris.java.gui.calc.model;

import javax.swing.JLabel;

/**
 * Razred koji ima ulogu promatrača u oblikovnom obrazcu promatrač.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ScreenListener implements CalcValueListener {

	private JLabel screen;
	
	/**
	 * Konstruktor.
	 * 
	 * @param screen ekran koji se ažurira nakon svake promjene rezultata
	 */
	public ScreenListener(JLabel screen) {
		this.screen = screen;
	}
	
	@Override
	public void valueChanged(CalcModel model) {
		screen.setText(model.toString());
	}

}
