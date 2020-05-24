package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JLabel;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Razred koji predstavlja labelu čiji je tekst lokaliziran.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LJLabel extends JLabel {

	private static final long serialVersionUID = 1L;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za tekst labele
	 * @param flp  referenca pružatelja prijevoda
	 */
	public LJLabel(String key, ILocalizationProvider flp) {
		
		super(flp.getString(key));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(flp.getString(key));
			}
		});
	}
}
