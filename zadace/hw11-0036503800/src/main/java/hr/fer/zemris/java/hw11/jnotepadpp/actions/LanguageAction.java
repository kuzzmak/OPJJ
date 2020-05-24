package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizationProvider;

/**
 * Razred koj predstavlja akciju promjene jezika.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LanguageAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private String key;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param flp referenca pružatelja prijevoda
	 */
	public LanguageAction(String key, ILocalizationProvider flp) {
		
		super(key, flp);
		this.key = key;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		LocalizationProvider.getInstance().setLanguage(key);
	}

}
