package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji ima funkciju kolekcije i bazira se na polju kao spremniku podataka. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class Collection {
	
	/**
	 * Bazni konstruktor 
	 * 
	 */
	protected Collection() {
		
	}
	
	/**
	 * Metoda za za provjer je li kolekcija prazna.
	 * 
	 * @return istina ako je prazna ili laž u suprotnom
	 */
	boolean isEmpty() {
		return this.size() == 0;
	}
	
	/**
	 * Metoda za dohvat veličine kolekcije.
	 * 
	 * @return broj članova koji nisu <code>null</code>
	 */
	int size() {
		return 0;
	}
	
	/**
	 * Metoda za dodavanje nove vrijednosti u postojeću kolekciju.
	 * Ako je postojeća kolekcija popunjena, stvara se nova, dvostruko veća.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	void add(Object value) {
		
	}
	
	/**
	 * Metoda za provjeru sadržava li kolekcija element <code>value</code>.
	 * 
	 * @param value element za koji se provjerava pripadnost
	 * @return istina ako kolekcija sadrži <code>value</code>, laž inače
	 */
	boolean contains(Object value) {
		return false;
	}
	
	/**
	 * Metoda za uklanjanje elementa <code>value</code> iz postojeće kolekcije.
	 * 
	 * @param value element koji se uklanja
	 * @return istina ako je element uklonjen, laž inače
	 */
	boolean remove(Object value) {
		return false;
	}
	
	/**
	 * Metoda za vraćanje internog polja koje služi kao spremnik elemenata.
	 * 
	 * @return polje elemenata unutar kolekcije
	 */
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Metoda koja nad svakim članom koji nije <code>null</code> izvodi nekakve radnje
	 * specificirane preko metode <code>process</code> razreda <code>Processor</code>.
	 * 
	 * @param processor instanca razreda <code>Processor</code> koja izvodi akcije nad 
	 * 		  svakim elementom kolekcije
	 * 
	 * @see Processor#process(Object value)
	 */
	void forEach(Processor processor) {
		
	}
	
	/**
	 * Metoda za dodavanje novih elemenata iz kolekcije <code>other</code>
	 * trenutnoj kolekciji. Elementi se dodaju preko lokalnog razreda <code>Processor</code>
	 * koji koji se brine o dodavanju pjedinog elementa.
	 * 
	 * @param other kolekcija koja se dodaje trenutnoj kolekciji
	 * @see Processor
	 */
	void addAll(Collection other) {
		
	}
	
	/**
	 * Metoda za brisanje elemenata kolekcije. Polje unutar kolekcije ostaje jednake
	 * veličine, ali su mu svi elementi <code>null</code>.
	 * 
	 */
	void clear() {
		
	}
	
}
