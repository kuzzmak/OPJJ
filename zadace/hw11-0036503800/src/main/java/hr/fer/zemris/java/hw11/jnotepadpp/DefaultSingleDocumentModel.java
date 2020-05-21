package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

public class DefaultSingleDocumentModel implements SingleDocumentModel {
	
	private Path filePath;
	private JTextArea textArea;

	public DefaultSingleDocumentModel(Path filePath, String text) {
		
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
	public void addSingleDocumentListener(SingleDocumentListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public void removeSingleDocumentListener(SingleDocumentListener listener) {
		// TODO Auto-generated method stub

	}

}
