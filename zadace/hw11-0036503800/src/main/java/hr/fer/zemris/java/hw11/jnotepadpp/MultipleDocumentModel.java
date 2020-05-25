package hr.fer.zemris.java.hw11.jnotepadpp;

import java.nio.file.Path;

/**
 * Sučelje koje modelira središnji dio prozora {@code JNotepad++} koji se
 * sastoji od kratica i prostora za upis teksta.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface MultipleDocumentModel extends Iterable<SingleDocumentModel> {
	
	/**
	 * Metoda za stvaranje novog dokumenta.
	 * 
	 * @return nova instanca dokumenta
	 */
	SingleDocumentModel createNewDocument();

	/**
	 * Metoda za dohvat trenutno aktivnog dokumenta.
	 * 
	 * @return dokument trenutne kratice
	 */
	SingleDocumentModel getCurrentDocument();

	/**
	 * Metoda za učitavanje dokumenta s računala.
	 * 
	 * @param path staza dokumenta koji se učitava
	 * @return učitani dokument
	 */
	SingleDocumentModel loadDocument(Path path);

	/**
	 * Metoda za spremanje dokumenta.
	 * 
	 * @param model dokument koji se sprema
	 * @param newPath staza dokumenta koji se sprema
	 */
	void saveDocument(SingleDocumentModel model, Path newPath);

	/**
	 * Metoda za zatvaranje trenutno aktivnog dokumenta.
	 * 
	 * @param model dokument koji se zatvara
	 */
	void closeDocument(SingleDocumentModel model);

	/**
	 * Metoda za dodavanje slušaća.
	 * 
	 * @param l slušać koji se dodaje
	 */
	void addMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda za uklanjanje slušaća.
	 * 
	 * @param l slušać koji se uklanja
	 */
	void removeMultipleDocumentListener(MultipleDocumentListener l);

	/**
	 * Metoda za dohvat broja postojećih dokumenata.
	 * 
	 * @return broj dokumenata
	 */
	int getNumberOfDocuments();

	/**
	 * Metoda za dohvat određenog dokumenta prema indeksu.
	 * 
	 * @param index redni broj dokumenta
	 * @return dokument na indeksu {@code index}
	 */
	SingleDocumentModel getDocument(int index);
}
