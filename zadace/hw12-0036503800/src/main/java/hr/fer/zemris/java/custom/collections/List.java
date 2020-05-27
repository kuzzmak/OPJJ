package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje koje modelita objekt liste.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface List extends Collection {
	
	/**
	 * Metoda za dohvat elementa polja na indeksu <code>index</code>.
	 * 
	 * @param index indeks na kojem se pokušava dohvatiti element
	 * @throws IndexOutOfBoundsException ako je predani <code>index</code> izvan
	 *                                   granica polja
	 * @return element kolekcije na mjestu <code>index</code>
	 */
	Object get(int index);
	
	/**
	 * Metoda za umetanje elementa u kolekciju na poziciji <code>position</code>.
	 * 
	 * @param value    element koji se umeće u kolekciju
	 * @param position pozicija na kojoj se umeće element
	 * @throws NullPointerException      ako je predan element <code>null</code>
	 *                                   vrijednost
	 * @throws IndexOutOfBoundsException ako je indeks < 0 ili > od veličine
	 *                                   trenutne kolekcije
	 */
	void insert(Object value, int position);
	
	/**
	 * Metoda za pronalazak indeksa predanog elementa <code>value</code> unutar kolekcije. 
	 * Ukoliko predani element nije član kolekcije, vraća se vrijednost -1.
	 * 
	 * @param value element čiji se indeks pokušava naći
	 * @return indeks traženog elementa ako je unutar kolekcije, -1 inače
	 */
	int indexOf(Object value);
	
	/**
	 * Metoda za uklanjanje elementa iz kolekcije na mjestu <code>index</code>.
	 * 
	 * @param index mjesto uklanjanja elementa
	 * @throws IndexOutOfBoundsException ako je <code>index</code> van granica
	 *                                   kolekcije
	 */
	void remove(int index);
}
