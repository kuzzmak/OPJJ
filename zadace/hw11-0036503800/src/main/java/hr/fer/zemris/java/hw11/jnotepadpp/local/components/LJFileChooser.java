package hr.fer.zemris.java.hw11.jnotepadpp.local.components;

import javax.swing.JFileChooser;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;

/**
 * Lokalizirani prozor za odabir datoteke.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LJFileChooser extends JFileChooser {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda
	 * @param flp pružatelj prijevoda
	 */
	public LJFileChooser(String key, ILocalizationProvider flp) {
		
		setDialogTitle(flp.getString(key));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				setDialogTitle(flp.getString(key));
			}
		});
	}

}
