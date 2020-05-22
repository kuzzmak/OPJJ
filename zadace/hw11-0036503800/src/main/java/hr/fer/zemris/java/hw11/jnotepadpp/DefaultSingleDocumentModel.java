package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	private JTextArea textArea;
	private boolean modified = false;
	
	private List<SingleDocumentListener> listeners;

	/**
	 * Konstruktor.
	 * 
	 * @param filePath staza do datoteke koja je učitana
	 * @param text sadržaj datoteke
	 */
	public DefaultSingleDocumentModel(Path filePath, String text) {
		
		listeners = new ArrayList<>();
		setFilePath(filePath);
		
		textArea = new JTextArea(text);
		textArea.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setModified(true);
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setModified(true);
			}
		});
	}
	
	@Override
	public JTextArea getTextComponent() {
		return textArea;
	}

	@Override
	public Path getFilePath() {
		return filePath;
	}

	@Override
	public void setFilePath(Path path) {
		filePath = path;
		listeners.forEach(l -> l.documentFilePathUpdated(this));
	}

	@Override
	public boolean isModified() {
		return modified;
	}

	@Override
	public void setModified(boolean modified) {
		this.modified = modified;
		listeners.forEach(l -> l.documentModifyStatusUpdated(DefaultSingleDocumentModel.this));
	}

	@Override
	public void addSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
}
