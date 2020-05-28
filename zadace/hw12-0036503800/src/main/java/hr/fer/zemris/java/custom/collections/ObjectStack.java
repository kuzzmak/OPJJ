package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji oponaša funkciju stoga, a u pozadini kao sredstvo pohrane
 * koristi kolekciju ArrayIndexedCollection. Ima ulogu adaptora u obrascu Adapter pattern.
 * 
 * @see ArrayIndexedCollection
 * @author Antonio Kuzminski
 *
 */
public class ObjectStack<E> {

	private ArrayIndexedCollection<E> collection;

	/**
	 * Inicijalni konstruktor
	 * 
	 */
	public ObjectStack() {
		this.collection = new ArrayIndexedCollection<>();
	}
	
	/**
	 * Metoda za provjeru je li stog prazan.
	 * 
	 * @return istina ako je prazan, laž inače
	 */
	public boolean isEmpty() {
		return collection.isEmpty();
	}

	/**
	 * Metoda za dohvat veličine stoga.
	 * 
	 * @return veličina stoga
	 */
	public int size() {
		return this.collection.size();
	}

	/**
	 * Metoda za stavljanje elementa na stog. Null vrijednosti se ne dopuštaju.
	 * 
	 * @param value element koji se stavlja na stog
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>.
	 */
	public void push(E value) {

		if (value == null)
			throw new NullPointerException("Nemoguće spremiti null na stog.");

		this.collection.add(value);
	}

	/**
	 * Metoda za dohvat elementa na vrhu stoga.
	 * 
	 * @throws EmptyStackException ako je stog prazan
	 * @return element na vrhu stoga
	 */
	public E pop() {

		if (this.isEmpty())
			throw new EmptyStackException("Stog je prazan.");

		E top = this.collection.get(this.collection.size() - 1);

		this.collection.remove(top);

		return top;
	}

	/**
	 * Metoda za dohvat elementa na vrhu stoga nakon čijeg se izvođenja 
	 * taj element ne uklanja sa stoga.
	 * 
	 * @throws EmptyStackException ako je stog prazan
	 * @return element na vrhu stoga
	 */
	public E peek() {

		if (this.isEmpty())
			throw new EmptyStackException("Stog je prazan.");

		return this.collection.get(this.collection.size() - 1);
	}

	/**
	 * Metoda za brisanje stoga.
	 * 
	 */
	public void clear() {
		this.collection.clear();
	}
}
