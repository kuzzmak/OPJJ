package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji ima funkciju kolekcije i bazira se na polju kao spremniku
 * podataka.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface Collection<E> {

	/**
	 * Metoda za za provjer je li kolekcija prazna.
	 * 
	 * @return istina ako je prazna ili laž u suprotnom
	 */
	default boolean isEmpty() {
		return this.size() == 0;
	}

	/**
	 * Metoda za dohvat veličine kolekcije.
	 * 
	 * @return broj članova koji nisu <code>null</code>
	 */
	int size();

	/**
	 * Metoda za dodavanje nove vrijednosti u postojeću kolekciju. Moguće je dodati
	 * višestruke jednake vrijednosti, ali <code>null</code> vrijednost nije
	 * dopuštena.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	void add(E value);

	/**
	 * Metoda za provjeru sadržava li kolekcija element <code>value</code>.
	 * 
	 * @param value element za koji se provjerava pripadnost
	 * @return istina ako kolekcija sadrži <code>value</code>, laž inače
	 */
	boolean contains(Object value);

	/**
	 * Metoda za uklanjanje elementa <code>value</code> iz postojeće kolekcije.
	 * 
	 * @param value element koji se uklanja
	 * @return istina ako je element uklonjen, laž inače
	 */
	boolean remove(E value);

	/**
	 * Metoda za vraćanje internog polja koje služi kao spremnik elemenata.
	 * 
	 * @return polje elemenata unutar kolekcije
	 */
	E[] toArray();

	/**
	 * Metoda koja nad svakim članom koji nije <code>null</code> izvodi nekakve
	 * radnje specificirane preko metode <code>process</code> razreda
	 * <code>Processor</code>.
	 * 
	 * @param processor instanca razreda <code>Processor</code> koja izvodi akcije
	 *                  nad svakim elementom kolekcije
	 * @throws NullPointerException ako je predani <code>processor null</code>
	 * @see Processor#process(Object value)
	 */
	default void forEach(Processor<? super E> processor) {

		ElementsGetter<E> eg = this.createElementsGetter();

		while (eg.hasNextElement()) {

			processor.process(eg.getNextElement());
		}
	}

	/**
	 * Metoda za dodavanje novih elemenata iz kolekcije <code>other</code> trenutnoj
	 * kolekciji. Elementi se dodaju preko lokalnog razreda <code>Processor</code>
	 * koji koji se brine o dodavanju pjedinog elementa.
	 * 
	 * @parathis.m other kolekcija koja se dodaje trenutnoj kolekciji
	 * @throws NullPointerException ako je predana kolekcija <code>null</code>
	 * @see Processor
	 */
	default void addAll(Collection<? extends E> other) {

		other.forEach(new Processor<E>() {
			@Override
			public void process(E value) {
				add(value);
			}
		});
	}

	/**
	 * Metoda za brisanje svih elemenata kolekcije.
	 * 
	 */
	void clear();

	/**
	 * Metoda za stvaranje "iteratora" kolekcije.
	 * 
	 */
	ElementsGetter<E> createElementsGetter();

	/**
	 * Metoda za dodavanje svih elemenata kolekcije <code>col</code> koji
	 * zadovoljavaju tester <code>tester</code>.
	 * 
	 * @param col    kolekcija čiji se elementi dodaju trenutnoj
	 * @param tester referenca razreda <code>Tester</code> koji definira uvjet
	 *               zadovoljivosti
	 */
	default void addAllSatisfying(Collection<E> col, Tester tester) {

		ElementsGetter<E> eg = col.createElementsGetter();

		while (eg.hasNextElement()) {

			E nextElement = eg.getNextElement();

			if (tester.test(nextElement))
				add(nextElement);
		}
	}
}
