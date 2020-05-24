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
 * Razred koji predstavlja akciju kopiranja.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CopyAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;

	private IDataGetter data;

	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param flp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public CopyAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control C"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(Action.SHORT_DESCRIPTION, flp.getString("copydesc"));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString("copydesc"));
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

			} catch (BadLocationException e1) {
				e1.printStackTrace();
			}
		}
	}

}
