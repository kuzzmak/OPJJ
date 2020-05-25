package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

import javax.swing.JTextArea;

/**
 * Sučelje koje predstavlja jedan dokument otvoren u {@code JNotepad++}.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface SingleDocumentModel {

	/**
	 * Metoda za dohvat tekstualne komponente koja se koristi za pojedini dokument.
	 * 
	 * @return objekt tipa {@code JTextArea}
	 */
	JTextArea getTextComponent();

	/**
	 * Metoda za dohvat staze dokumenta.
	 * 
	 * @return staza dokumenta ako je učitan dokument s računala ili {@code null}
	 *         ako je tek otvoren dokument
	 */
	Path getFilePath();

	/**
	 * Metoda za postavljanje staze dokumenta.
	 * 
	 * @param path staza koja se postavlja dokumentu
	 */
	void setFilePath(Path path);

	/**
	 * Metoda za provjeru je li dokument na neki način modificiran.
	 * 
	 * @return istina ako je dokument modificiran, laž inače
	 */
	boolean isModified();

	/**
	 * Metoda za promjenu statusa modifikacije dokumenta.
	 * 
	 * @param modified status dokumenta koji se postavlja
	 */
	void setModified(boolean modified);

	/**
	 * Metoda za dodavanje slušaća dokumenta.
	 * 
	 * @param l slušač koji se dodaje
	 */
	void addSingleDocumentListener(SingleDocumentListener l);

	/**
	 * Metoda za uklanjanje slušaća dokumenta.
	 * 
	 * @param l slušać koji se uklanja
	 */
	void removeSingleDocumentListener(SingleDocumentListener l);
}
