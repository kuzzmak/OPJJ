package hr.fer.zemris.java.hw11.jnotepadpp.actions;

import javax.swing.JFrame;

import hr.fer.zemris.java.hw11.jnotepadpp.DefaultMultipleDocumentModel;

/**
 * Sučelje koje modelira objekt za dohvat frame-a JNotepad++
 * i središnjeg modela dokumenta.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface IDataGetter {
	
	/**
	 * Metoda za dohvat reference {@code JNotepad++} prozora.
	 * 
	 * @return {@code JNotepad++} prozor
	 */
	JFrame getFrame();
	
	/**
	 * Metoda za dohvat modela dokumenta.
	 * 
	 * @return model dokumenta
	 */
	DefaultMultipleDocumentModel getDmdm();

}
