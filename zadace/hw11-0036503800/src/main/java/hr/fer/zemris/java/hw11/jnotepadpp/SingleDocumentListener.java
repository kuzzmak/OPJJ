package hr.fer.zemris.java.hw11.jnotepadpp;

public interface SingleDocumentListener {
	void documentModifyStatusUpdated(SingleDocumentModel model);
	void documentFilePathUpdated(SingleDocumentModel model);
}
