package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Razred koji predstavlja akciju zatvaranja kartice prozora.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CloseTabAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private IDataGetter data;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param lp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public CloseTabAction(String key, ILocalizationProvider flp, IDataGetter data) {
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control W"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_W);
	    putValue(Action.SHORT_DESCRIPTION, flp.getString("closedesc"));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString("closedesc"));
			}
		});
		
		this.data = data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		data.getDmdm().closeDocument(data.getDmdm().getCurrentDocument());
	}

}
