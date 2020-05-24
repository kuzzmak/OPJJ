package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Razred koji predstavlja akciju dohvata statističkih informacija
 * oko trenutno otvorenome dokumentu.
 * 
 * @author Antonio Kuzminski
 *
 */
public class StatisticalnfoAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private IDataGetter data;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param flp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public StatisticalnfoAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F5"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(Action.SHORT_DESCRIPTION, flp.getString("infodesc"));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString("infodesc"));
			}
		});
		
		this.data = data;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		String infoString = "Statističke informacije o trenutno otvorenom dokumentu.\n\n";

		JTextArea textArea = data.getDmdm().getCurrentDocument().getTextComponent();

		infoString += "Ukupan broj znakova: " + String.valueOf(textArea.getText().length()) + "\n";
		infoString += "Broj nepraznih znakova: "
				+ String.valueOf(textArea.getText().replaceAll("\\s+", "").length()) + "\n";
		infoString += "Broj linija: " + String.valueOf(textArea.getLineCount());

		JOptionPane.showMessageDialog(data.getFrame(), infoString, "Informacije", JOptionPane.INFORMATION_MESSAGE);
	}

}
