package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.AbstractAction;
import javax.swing.Action;

/**
 * Razred koji ima mogućnost lokalizacije imena akcije.
 * 
 * @author Antonio Kuzminski
 *
 */
public abstract class LocalizableAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prema kojem se traži lokalizirani naziv
	 * @param lp pružatelj lokaliziranog naziva
	 */
	public LocalizableAction(String key, ILocalizationProvider lp) {
		
		putValue(Action.NAME, lp.getString(key));
		
		lp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.NAME, lp.getString(key));
			}
		});
	}

}
