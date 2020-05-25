package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.JNotepadPP;
import hr.fer.zemris.java.hw11.jnotepadpp.SingleDocumentModel;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationListener;
import hr.fer.zemris.java.hw11.jnotepadpp.local.ILocalizationProvider;
import hr.fer.zemris.java.hw11.jnotepadpp.local.LocalizableAction;
import hr.fer.zemris.java.hw11.jnotepadpp.local.components.LJFileChooser;

/**
 * Razred koji predstavlja akciju za spremanje dokumenta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class SaveDocumentAction extends LocalizableAction {

	private static final long serialVersionUID = 1L;

	private IDataGetter data;
	private ILocalizationProvider flp;
	private boolean saveas;

	/**
	 * Konstruktor.
	 * 
	 * @param key  ključ prijevoda za ime akcije
	 * @param flp   referenca pružatelja prijevoda
	 * @param data referenca za dohvat prozora glavnog programa i modela dokumenta
	 * @param saveas stvara li se akcija za save ili saveas
	 */
	public SaveDocumentAction(String key, ILocalizationProvider flp, IDataGetter data, boolean saveas) {

		super(key, flp);

		if (saveas) {
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control shift S"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
			putValue(Action.SHORT_DESCRIPTION, flp.getString("saveasdesc"));
		} else {
			putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
			putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
			putValue(Action.SHORT_DESCRIPTION, flp.getString("savedesc"));
		}

		flp.addLocalizationListener(new ILocalizationListener() {

			@Override
			public void localizationChanged() {
				if (saveas) {
					putValue(Action.SHORT_DESCRIPTION, flp.getString("saveasdesc"));
				} else {
					putValue(Action.SHORT_DESCRIPTION, flp.getString("savedesc"));
				}
			}
		});

		this.flp = flp;
		this.data = data;
		this.saveas = saveas;
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		DefaultMultipleDocumentModel dmdm = data.getDmdm();
		SingleDocumentModel document = dmdm.getCurrentDocument();

		File selectedFile = null;

		// ukoliko je tek novootvoren dokument ili se pritisne na gumb saveas
		// mora se otvoriti prozor s odabirom naziva nove datoteke
		if (document.getFilePath() == null || saveas) {

			JFileChooser jfc = new LJFileChooser("fileDialogSaveDocument", flp);
			if (jfc.showSaveDialog(data.getFrame()) != JFileChooser.APPROVE_OPTION) {
				JOptionPane.showMessageDialog(
						data.getFrame(), 
						flp.getString("optionPaneNotSavedMessage"), 
						flp.getString("optionPaneNotSavedTitle"),
						JOptionPane.WARNING_MESSAGE);
				return;
			}
			
			selectedFile = jfc.getSelectedFile();

			if (selectedFile.exists()) {

				// ako odabrano ime datoteke postoji, pita se korisnika hoće li se prepisati
				// stara datoteka
				if (JOptionPane.showConfirmDialog(
						data.getFrame(),
						flp.getString("optionPaneFileExistMessage"), 
						flp.getString("optionPaneFileExistTitle"),
						JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

				} else { // stara datoteka se ne smije prepisati
					return;
				}
			}
			
			// ukoliko je izabrano ime koje još ne postoji, sprema se
			dmdm.saveDocument(document, selectedFile.toPath());
			dmdm.setTitleAt(dmdm.getSelectedIndex(),
					dmdm.getCurrentDocument().getFilePath().getFileName().toString());
			
		} else {
			// slučaj kad je otvoren postojeći dokument i napravljena je neka
			// preinaka pa se sprema pritiskom na save
			dmdm.saveDocument(document, document.getFilePath());
		}
			
		dmdm.setIconAt(dmdm.getSelectedIndex(), JNotepadPP.unmodified);
	}

}
