package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class DefaultMultipleDocumentModel extends JTabbedPane implements MultipleDocumentModel {

	private static final long serialVersionUID = 1L;
	
	List<SingleDocumentModel> documents;
	
	List<MultipleDocumentListener> listeners;
	
	public DefaultMultipleDocumentModel() {
		
		documents = new ArrayList<>();
		listeners = new ArrayList<>();
		
		SingleDocumentModel newDocument = createNewDocument();
		documents.add(newDocument);
		JScrollPane scp = new JScrollPane(newDocument.getTextComponent());
		addTab("(unnamed)", scp);
	}

	@Override
	public Iterator<SingleDocumentModel> iterator() {
		return documents.iterator();
	}

	@Override
	public SingleDocumentModel createNewDocument() {
		
		SingleDocumentModel newDocument = new DefaultSingleDocumentModel(null, "");
		return newDocument;
	}

	@Override
	public SingleDocumentModel getCurrentDocument() {
		return getDocument(getSelectedIndex());
	}

	@Override
	public SingleDocumentModel loadDocument(Path path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void saveDocument(SingleDocumentModel model, Path newPath) {
		// TODO Auto-generated method stub

	}

	@Override
	public void closeDocument(SingleDocumentModel model) {
		// TODO Auto-generated method stub

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
		
		if(index < 0 || index >= size)
			throw new IndexOutOfBoundsException("Index izvan granica.");
		
		return documents.get(index);
	}
}
