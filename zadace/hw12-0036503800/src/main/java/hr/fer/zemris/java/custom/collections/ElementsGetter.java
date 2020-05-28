package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Sučelje koje modelira razred za dohvat elemenata liste poput iteratora.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface ElementsGetter<E> {
	
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
	 * @throws ConcurrentModificationException ako se rade modifikacije 
	 * 		   kolekcije prilikom iteriranja
	 */
	E getNextElement();
	
	/**
	 * Metoda za obradu ostatka elemenata kolekcije. Ostatka 
	 * elemenata koji do sad nisu dohvaćeni metodom {@link #getNextElement()}.
	 * 
	 * @param p referenca razreda <code>Processora</code> koji obrađje svaki 
	 *          element kolekcije
	 * @throws NullPointerException ako je predan <code>null</code> kao Processor
	 * @see Processor
	 */
	default void processRemaining(Processor<E> p) {
		
		if(p == null) throw new NullPointerException("Null nije valjan Processor.");
		
		while(hasNextElement()) {
			
			p.process(getNextElement());
		}
	}
}
