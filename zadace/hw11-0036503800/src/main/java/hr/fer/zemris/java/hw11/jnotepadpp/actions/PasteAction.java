package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.Action;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Razred koji predstavlja akciju lijepljenja teksta iz međuspremnika.
 * 
 * @author Antonio Kuzminski
 *
 */
public class PasteAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;

	private IDataGetter data;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param flp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public PasteAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control V"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_V);
		putValue(Action.SHORT_DESCRIPTION, flp.getString("pastedesc"));

		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString("pastedesc"));
			}
		});
		
		this.data = data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JTextArea textArea = data.getDmdm().getCurrentDocument().getTextComponent();
		Document doc = textArea.getDocument();

		int carretPosition = textArea.getCaret().getDot();

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Object dataFromClipboard;
		try {
			dataFromClipboard = clipboard.getData(DataFlavor.stringFlavor);
			
			if (dataFromClipboard instanceof String) {

				String textFromClipboard = (String) dataFromClipboard;
				
				try {
					doc.insertString(carretPosition, textFromClipboard, null);
				} catch (BadLocationException e1) {
					e1.printStackTrace();
				}
			}
		} catch (UnsupportedFlavorException | IOException e1) {
			e1.printStackTrace();
		}
	}

}
