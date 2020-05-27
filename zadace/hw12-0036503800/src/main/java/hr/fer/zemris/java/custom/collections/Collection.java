package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji ima funkciju kolekcije i bazira se na polju kao spremniku
 * podataka.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface Collection {

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
	void add(Object value);

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
	boolean remove(Object value);

	/**
	 * Metoda za vraćanje internog polja koje služi kao spremnik elemenata.
	 * 
	 * @return polje elemenata unutar kolekcije
	 */
	Object[] toArray();

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
	default void forEach(Processor processor) {

		ElementsGetter eg = this.createElementsGetter();

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
	default void addAll(Collection other) {

		other.forEach(new Processor() {
			@Override
			public void process(Object value) {
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
	ElementsGetter createElementsGetter();

	/**
	 * Metoda za dodavanje svih elemenata kolekcije <code>col</code> koji
	 * zadovoljavaju tester <code>tester</code>.
	 * 
	 * @param col    kolekcija čiji se elementi dodaju trenutnoj
	 * @param tester referenca razreda <code>Tester</code> koji definira uvjet
	 *               zadovoljivosti
	 */
	default void addAllSatisfying(Collection col, Tester tester) {

		ElementsGetter eg = col.createElementsGetter();

		while (eg.hasNextElement()) {

			Object nextElement = eg.getNextElement();

			if (tester.test(nextElement))
				add(nextElement);
		}
	}
}
