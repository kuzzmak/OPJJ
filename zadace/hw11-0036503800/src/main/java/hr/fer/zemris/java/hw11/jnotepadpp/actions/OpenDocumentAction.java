package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;

/**
 * Akcija koja predstavlja otvaranje postojeće datoteke.
 * 
 * @author Antonio Kuzminski
 *
 */
public class OpenDocumentAction extends LocalizableAction {
	
	private static final long serialVersionUID = 1L;
	
	private IDataGetter data;
	
	/**
	 * Konstruktor.
	 * 
	 * @param key ključ prijevoda za ime akcije
	 * @param lp referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 */
	public OpenDocumentAction(String key, ILocalizationProvider flp, IDataGetter data) {
		
		super(key, flp);
		
		putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
		putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(Action.SHORT_DESCRIPTION, flp.getString("opendesc"));
		
		flp.addLocalizationListener(new ILocalizationListener() {
			
			@Override
			public void localizationChanged() {
				putValue(Action.SHORT_DESCRIPTION, flp.getString("opendesc"));
			}
		});
		
		this.data = data;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Open file");
		if (fc.showOpenDialog(data.getFrame()) != JFileChooser.APPROVE_OPTION) {
			return;
		}

		File fileName = fc.getSelectedFile();
		Path filePath = fileName.toPath();

		if (!Files.isReadable(filePath)) {
			JOptionPane.showMessageDialog(data.getFrame(), "Datoteka " + filePath + " ne postoji!", "Pogreška",
					JOptionPane.ERROR_MESSAGE);
			return;
		}

		int indexOfAlredyOpened = -1;
		int i = 0;
		Iterator<SingleDocumentModel> iter = data.getDmdm().iterator();
		while (iter.hasNext()) {

			SingleDocumentModel model = iter.next();

			if (model.getFilePath() != null) {
				if (model.getFilePath().equals(filePath)) {
					indexOfAlredyOpened = i;
				}
			}
			i++;
		}

		// izabrana datoteka koja nije otvorena u nekoj kratici
		if (indexOfAlredyOpened == -1) {

			data.getDmdm().loadDocument(filePath);

		} else {

			data.getDmdm().setSelectedIndex(indexOfAlredyOpened);
		}
	}

}
