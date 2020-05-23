package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Razred koji predstavlja akciju za stvaranje novog dokumenta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class NewDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private IDataGetter data;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param lp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public NewDocumentAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift N"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(Action.SHORT_DESCRIPTION, flp.getString("newdesc"));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString("newdesc"));
			}
		});
		
		this.data = data;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		data.getDmdm().createNewDocument();
	}

}
