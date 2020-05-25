package hr.fer.zemris.java.hw11.jnotepadpp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

/**
 * Razred koji predstavlja središnji dio prozora {@code JNotepadPP}.
 * Sastoji se od kratica koje predstavljaju pojedini otvoreni dokument.
 * 
 * @author Antonio Kuzminski
 *
 */
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
		
		listeners.forEach(l -> l.documentAdded(newDocument));
		
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
			return null;
		}
		
		String text = new String(bytes, StandardCharsets.UTF_8);
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(path, text);
		documents.add(newDocument);
			
		listeners.forEach(l -> l.documentAdded(newDocument));
		
		return newDocument;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {

		byte[] podatci = model.getTextComponent().getText().getBytes(StandardCharsets.UTF_8);

		try {
			Files.write(newPath, podatci);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this,
					"Pogreška prilikom zapisivanja datoteke " + newPath.toFile().getAbsolutePath()
							+ ".\nPažnja: nije jasno u kojem je stanju datoteka na disku!",
					"Pogreška", JOptionPane.ERROR_MESSAGE);
			return;
		}

		model.setFilePath(newPath);
		model.setModified(false);
	}

	@Override
	public void closeDocument(SingleDocumentModel model) {

		if (model != null) {

			Iterator<SingleDocumentModel> documentIterator = iterator();

			while (documentIterator.hasNext()) {

				SingleDocumentModel doc = documentIterator.next();
				if (doc.equals(model)) {
					
					documentIterator.remove();
					
					listeners.forEach(l -> l.documentRemoved(doc));
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
