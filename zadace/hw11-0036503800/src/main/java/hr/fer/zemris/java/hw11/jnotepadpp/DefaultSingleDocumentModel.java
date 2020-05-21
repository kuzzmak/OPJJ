package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	private JTextArea textArea;
	
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
		this.textArea = new JTextArea(text);
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
		notifyListeners();
	}

	@Override
	public boolean isModified() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setModified(boolean modified) {
		// TODO Auto-generated method stub

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
	
	public void notifyListeners() {
		for(SingleDocumentListener l: listeners) {
			l.documentFilePathUpdated(this);
		}
	}

}
