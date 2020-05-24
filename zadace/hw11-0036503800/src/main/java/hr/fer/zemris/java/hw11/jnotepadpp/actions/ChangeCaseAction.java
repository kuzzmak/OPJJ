package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Razred koji predstavlja akciju promjene velikog i malog slova.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ChangeCaseAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;
	
	private IDataGetter data;
	private String key;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param flp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public ChangeCaseAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		if(key.equals("invertcase")) {
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F6"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_I);
		}else if(key.equals("lowercase")) {
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F7"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_L);
		}else {
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("F8"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_U);
		}

		putValue(Action.SHORT_DESCRIPTION, flp.getString(key));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString(key));
			}
		});
		
		this.data = data;
		this.key = key;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextArea textArea = data.getDmdm().getCurrentDocument().getTextComponent();
		
		Document doc = textArea.getDocument();
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());
		int offset = 0;
		if (len != 0) {
			offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());
		} else {
			len = doc.getLength();
		}
		try {
			String text = doc.getText(offset, len);
			text = changeCase(text, key);
			doc.remove(offset, len);
			doc.insertString(offset, text, null);
		} catch (BadLocationException ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Metoda za promjenu velikog i malog slova odabranog teksta.
	 * 
	 * @param text tekst koji se mijenja
	 * @param caseType vrsta promjene
	 * @return promijenjen tekst
	 */
	private String changeCase(String text, String caseType) {
		
		char[] znakovi = text.toCharArray();
		
		if(caseType.equals("invertcase")) {
			
			for (int i = 0; i < znakovi.length; i++) {
				char c = znakovi[i];
				if (Character.isLowerCase(c)) {
					znakovi[i] = Character.toUpperCase(c);
				} else if (Character.isUpperCase(c)) {
					znakovi[i] = Character.toLowerCase(c);
				}
			}
			
		}else if(caseType.equals("lowercase")) {
			for (int i = 0; i < znakovi.length; i++) {
				znakovi[i] = Character.toLowerCase(znakovi[i]);
			}
			
		}else {
			for (int i = 0; i < znakovi.length; i++) {
				znakovi[i] = Character.toUpperCase(znakovi[i]);
			}
		}
		
		return new String(znakovi);
	}

}
