package hr.fer.zemris.java.custom.collections;

import java.util.NoSuchElementException;

/**
 * Sučelje koje modelira razred za dohvat elemenata liste poput iteratora.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface ElementsGetter {
	
	/**
	 * Metoda za provjeru ima li kolekcija još elemenata koji nis dohvaćeni.
	 * 
	 * @return istina ako ima, laž inače
	 */
	boolean hasNextElement();
	
	/**
	 * Metoda za dohvat sljedećeg elementa kolekcije.
	 * 
	 * @return sljedeći eleement kolekcije ako postoji
	 * @throws NoSuchElementException ako nema sljedećeg objekta
	 */
	Object getNextElement();
}
