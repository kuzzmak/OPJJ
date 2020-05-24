package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JMenu;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Razred koji predstavlja lokalizirani izbornik.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LJMenu extends JMenu {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za izbornik
	 * @param flp referenca pružatelja prijevoda
	 */
	public LJMenu(String key, ILocalizationProvider flp) {
		
		super(flp.getString(key));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setText(flp.getString(key));
			}
		});
	}

}
