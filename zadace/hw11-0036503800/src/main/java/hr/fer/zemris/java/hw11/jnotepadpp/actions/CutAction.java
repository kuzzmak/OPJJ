package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
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
 * Razred koji modelira akciju rezanja teksta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CutAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private IDataGetter data;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param lp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public CutAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control X"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_X);
		putValue(Action.SHORT_DESCRIPTION, flp.getString(key));

		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString(key));
			}
		});
		
		this.data = data;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextArea textArea = data.getDmdm().getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();
		int len = Math.abs(textArea.getCaret().getDot() - textArea.getCaret().getMark());

		if (len > 0) {

			int offset = Math.min(textArea.getCaret().getDot(), textArea.getCaret().getMark());

			try {

				StringSelection stringSelection = new StringSelection(doc.getText(offset, len));
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(stringSelection, null);

				doc.remove(offset, len);

			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}

}
