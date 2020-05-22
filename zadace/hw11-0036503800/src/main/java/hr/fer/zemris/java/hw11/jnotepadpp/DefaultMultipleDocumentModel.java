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

	/**
	 * Konstruktor.
	 * 
	 */
	public DefaultMultipleDocumentModel() {

		documents = new ArrayList<>();
		listeners = new ArrayList<>();
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

		return newDocument;
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
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {

		if (model != null) {

			Iterator<SingleDocumentModel> documentIterator = iterator();

			while (documentIterator.hasNext()) {

				SingleDocumentModel doc = documentIterator.next();
				if (doc.equals(model)) {
					documentIterator.remove();
					int i = getSelectedIndex();
					if (i != -1)
						this.removeTabAt(i);
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

		if (index < 0 || index >= size)
			return null;

		return documents.get(index);
	}

}
