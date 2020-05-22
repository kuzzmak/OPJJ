package hr.fer.zemris.java.hw11.jnotepadpp;

import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;

	List<SingleDocumentModel> documents;

	List<MultipleDocumentListener> listeners;

	private ImageIcon unmodified;
	private ImageIcon modified;

	/**
	 * Konstruktor.
	 * 
	 */
	public DefaultMultipleDocumentModel() {

		documents = new ArrayList<>();
		listeners = new ArrayList<>();

		unmodified = createImageIcon("icons/unmodified.png", 20);
		modified = createImageIcon("icons/modified.png", 20);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {

		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		documents.add(newDocument);
		return newDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return getDocument(getSelectedIndex());
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {

		int indexOfAlredyOpened = -1;

		// provjera je li već otvorena datoteka koja je odabrana
		for (int i = 0; i < documents.size(); i++) {

			Path documentPath = documents.get(i).getFilePath();
			if (documentPath == null)
				continue;

			if (documentPath.equals(path)) {
				indexOfAlredyOpened = i;
			}
		}

		if (indexOfAlredyOpened == -1) {

			byte[] bytes;
			try {
				bytes = Files.readAllBytes(path);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Pogreška prilikom čitanja datoteke " + path + ".", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

			try {
				bytes = Files.readAllBytes(path);
			} catch (Exception ex) {
				JOptionPane.showMessageDialog(this, "Pogreška prilikom čitanja datoteke " + path + ".", "Pogreška",
						JOptionPane.ERROR_MESSAGE);
				return null;
			}

			String text = new String(bytes, StandardCharsets.UTF_8);
			SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, text);
			documents.add(newDocument);

			newTab(path.getFileName().toString(), null, new JScrollPane(newDocument.getTextComponent()));

			// odabir taba novo učitane datoteke
			setSelectedIndex(documents.size() - 1);
			return newDocument;

		} else {

			// odabir kartice koja je već otvorena
			setSelectedIndex(indexOfAlredyOpened);
			return getDocument(indexOfAlredyOpened);
		}
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		if (newPath == null)
			return;

		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(newPath, podatci);
		} catch (IOException e1) {
			JOptionPane.showMessageDialog(this,
					"Pogreška prilikom zapisivanja datoteke " + newPath.toFile().getAbsolutePath()
							+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		JOptionPane.showMessageDialog(this, "Datoteka je snimljena.", "Informacija", JOptionPane.INFORMATION_MESSAGE);

		model.setFilePath(newPath);
		setTitleAt(getSelectedIndex(), model.getFilePath().getFileName().toString());
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {

		if (model != null) {
			
			Iterator<SingleDocumentModel> documentIterator = iterator();
			
			while(documentIterator.hasNext()) {
				
				SingleDocumentModel doc = documentIterator.next();
				if(doc.equals(model)) {
					documentIterator.remove();
					int i = getSelectedIndex();
				    if (i != -1) this.removeTabAt(i);
				}
			}
		}
	}

	@Override
	public void addMultipleDocumentListener(MultipleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeMultipleDocumentListener(MultipleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

	@Override
	public int getNumberOfDocuments() {
		return documents.size();
	}

	@Override
	public SingleDocumentModel getDocument(int index) {

		int size = getNumberOfDocuments();
		
		if (index < 0 || index >= size) return null;
			
		return documents.get(index);
	}

	/**
	 * Funkcija za učitavanje i stvaranje objekta {@code ImageIcon} iz predane staze
	 * do željene sličice {@code path} uz željeno skaliranje {@code scalePercent}.
	 * 
	 * @param path         staza do slike koja se učitava
	 * @param scalePercent postotak na koj se skalira izvorna slika
	 * @return objekt tipa {@code ImageIcon}
	 */
	private ImageIcon createImageIcon(String path, int scalePercent) {

		try (InputStream is = this.getClass().getResourceAsStream(path)) {

			ImageIcon im = new ImageIcon(is.readAllBytes());
			return new ImageIcon(im.getImage().getScaledInstance(scalePercent, scalePercent, Image.SCALE_DEFAULT));

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Funkcija za dodavanje nove kratice u {@code JTabbedPanel}
	 * 
	 * @param name      ime na kratici
	 * @param icon      ikona prikazana na kratici
	 * @param component komponenta koja se dodaje
	 */
	public void newTab(String name, Icon icon, Component component) {

		Icon tabIcon = icon;
		String tabName = name;

		if (icon == null)
			tabIcon = unmodified;
		if (name == null)
			tabName = "(unnamed)";

		addTab(tabName, tabIcon, component);

		setSelectedIndex(getTabCount() - 1);
	}

}
