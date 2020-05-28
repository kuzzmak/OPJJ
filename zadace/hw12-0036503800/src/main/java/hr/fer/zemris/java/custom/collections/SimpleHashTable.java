package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
/**
 * Jednostavna implementacija mape gdje se elementi smještaju
 * u tablicu raspršenog adresiranja. 
 * 
 * @author Antonio Kuzminski
 *
 * @param <K> tip ključa
 * @param <V> tip vrijednosti
 */
public class SimpleHashTable<K, V> implements Iterable<SimpleHashTable.TableEntry<K, V>> {

	/**
	 * Interni razred koji ima ulogu jednog uređenog para {@code (K, V)} gdje
	 * {@code K} ima ulogu ključa, a {@code V} vrijednosti pod tim ključem.
	 * 
	 * @author Antonio Kuzminski
	 *
	 * @param <K> ključ
	 * @param <V> vrijednost
	 */
	static class TableEntry<K, V> {

		private K key;
		private V value;
		private TableEntry<K, V> next;

		public TableEntry(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public K getKey() {
			return key;
		}

		public V getValue() {
			return value;
		}

		public void setValue(V value) {
			this.value = value;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			TableEntry other = (TableEntry) obj;
			if (key == null) {
				if (other.key != null)
					return false;
			} else if (!key.equals(other.key))
				return false;
			if (value == null) {
				if (other.value != null)
					return false;
			} else if (!value.equals(other.value))
				return false;
			return true;
		}

		@Override
		public String toString() {
			return key + "=" + value;
		}
	}

	private TableEntry<K, V>[] entries;
	private int size = 0;
	private static final double threshold = 0.75;
	private static double modificationCount = 0;

	/**
	 * Inicijalni konstruktor koji postavlja veličinu tablice na 16 slotova.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable() {

		entries = (TableEntry<K, V>[]) new TableEntry[16];
	}

	/**
	 * Konstruktor koji stvara tablicu velčine <code>capacity</code> ako je
	 * <code>capacity</code> potencija broja 2, a ako nije onda najbliža potencija.
	 * 
	 * @param capacity veličina tablice koja se želi stvoriti
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int capacity) {

		if (capacity < 1)
			throw new IllegalArgumentException("Nije moguće stvoriti tablicu dimenzija: " + capacity);

		int power = (int) Math.ceil(Math.log(capacity) / Math.log(2));

		entries = (TableEntry<K, V>[]) new Object[(int) Math.pow(2, power)];
	}

	/**
	 * Metoda za stavljanje kombinacije (K, V) u tablicu. Nije dozvoljeno koristiti
	 * <code>null</code> kao vrijednost ključa. Ako ključ već postoji novim
	 * umetanjem se vrijednost <code>V</code> pod tim ključem ažurira na novu
	 * vrijednost.
	 * 
	 * @throws NullPointerException ako je kao ključ predana <code>null</code>
	 *                              vrijednost
	 * @param key   ključ pod kojim se ubacuje nova vrijednost
	 * @param value vrijednost koja se ubacuje
	 */
	public void put(K key, V value) {

		if (key == null)
			throw new NullPointerException("Ključ ne može biti null.");

		if (size >= threshold * entries.length) {

			resize();
		}

		int slot = getSlot(key);

		if (entries[slot] != null) {

			TableEntry<K, V> entry = entries[slot];

			while (entry != null) {

				if (entry.getKey().equals(key)) {
					entry.setValue(value);
					return;
				}

				// zadnji element nekog slota
				if (entry.next == null) {
					entry.next = new TableEntry<>(key, value);
					modificationCount++;
					size++;
					return;
				}
				entry = entry.next;
			}
		} else {
			entries[slot] = new TableEntry<>(key, value);
			modificationCount++;
			size++;
		}
	}

	/**
	 * Metoda za dohvat vrijednosti pod ključem <code>key</code> ako taj ključ
	 * postoji.
	 * 
	 * @param key ključ čija se vrjiednost traži
	 * @return vrijednost pod traženim ključem ili <code>null</code> ako ključ ne
	 *         postoji ili je predani ključ <code>null</code>
	 */
	public V get(Object key) {

		if (key == null)
			return null;

		int slot = getSlot(key);

		TableEntry<K, V> entry = entries[slot];

		while (entry != null) {

			if (entry.getKey().equals(key))
				return entry.getValue();
			else
				entry = entry.next;
		}

		return null;
	}

	/**
	 * Metoda za dohvat veličine tablice.
	 * 
	 * @return broj pohranjenih zapisa
	 */
	public int size() {
		return size;
	}

	/**
	 * Metoda za provjeru sadrži li tablica određen ključ.
	 * 
	 * @param key ključ za koji se provjerava je li sadržan u tablici
	 * @return istina ako ključ <code>key</code> postoji, laž inače
	 */
	public boolean containsKey(Object key) {

		if (key == null)
			return false;

		int slot = getSlot(key);

		TableEntry<K, V> entry = entries[slot];

		while (entry != null) {
			if (entry.getKey().equals(key))
				return true;
			else
				entry = entry.next;
		}

		return false;
	}

	/**
	 * Metoda za provjeru sadrži li tablica vrijednost {@code value}.
	 * 
	 * @param value vrijednost za koju se provjerava je li u tablici
	 * @return istina ako je {@code value} sadržana u tablici, laž inače
	 */
	public boolean containsValue(Object value) {

		for (int i = 0; i < entries.length; i++) {

			if (entries[i] == null)
				continue;
			else {

				TableEntry<K, V> entry = entries[i];

				while (entry != null) {

					if (value != null) {
						if (entry.getValue().equals(value))
							return true;
					} else {
						if (entry.getValue() == null)
							return true;
					}

					entry = entry.next;
				}
			}
		}
		return false;
	}

	/**
	 * Metoda za uklanjanje uređenog para {@code (K, V)} iz tablice pod ključem
	 * {@code K} ako on postoji.
	 * 
	 * @param key ključ čiji se par uklanja iz tablice
	 * @throws NullPointerException ako je predani ključ {@code key null}
	 */
	public void remove(Object key) {

		if (key == null)
			throw new NullPointerException("Nije moguće izbrisati null jer ne postoji.");

		int slot = getSlot(key);

		TableEntry<K, V> entry = entries[slot];

		// ako je prazan slot
		if (entry == null)
			return;

		// ako je samo jedan entry u slotu
		if (entry.next == null) {
			entries[slot] = null;
			modificationCount++;
			size--;
			return;
		}
		
		// prvi element u slotu
		if(entry.getKey().equals(key)) {
			entries[slot] = entry.next;
			entry = null;
			modificationCount++;
			size--;
			return;
		}

		TableEntry<K, V> previousEntry = entry;

		while (entry != null) {

			if (entry.getKey().equals(key)) {

				previousEntry.next = entry.next;
				entry = null;
				modificationCount++;
				size--;
				return;
			}

			previousEntry = entry;
			entry = entry.next;
		}
	}

	/**
	 * Metoda za provjeru je li tablica prazna.
	 * 
	 * @return istina ako je tablica prazna, laž inače
	 */
	public boolean isEmpty() {
		return size == 0;
	}

	/**
	 * Metoda za povećanje veličine tablice ako je zauzetost prešla
	 * {@code threshold * trenutni limit}.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void resize() {

		int newSize = 2 * entries.length;

		TableEntry<K, V>[] oldEntries = entries;

		entries = (TableEntry<K, V>[]) new TableEntry[newSize];
		size = 0;

		for (int i = 0; i < oldEntries.length; i++) {

			if (oldEntries[i] != null) {

				TableEntry<K, V> entry = oldEntries[i];

				while (entry != null) {

					put(entry.getKey(), entry.getValue());
					entry = entry.next;
				}
			}
		}
	}

	/**
	 * Funkcija za izračun mjesta u tablici u koje se novi par {@code (K, V)} mora
	 * ubaciti.
	 * 
	 * @param key ključ za koji se mjesto izračunava
	 * @return redni broj mjesta
	 */
	public int getSlot(Object key) {
		return Math.abs(key.hashCode()) % entries.length;
	}

	/**
	 * Metoda čija je zadaća izbrisati sve uređene parove tablice. Ne mijenja
	 * kapacitet tablice.
	 * 
	 */
	public void clear() {

		for (int i = 0; i < entries.length; i++) {
			entries[i] = null;
			modificationCount++;
		}
		size = 0;
	}

	@Override
	public String toString() {

		if (isEmpty())
			return "[]";
		else {

			StringBuilder sb = new StringBuilder();
			sb.append("[");

			for (int i = 0; i < entries.length; i++) {

				if (entries[i] != null) {

					TableEntry<K, V> entry = entries[i];

					while (entry != null) {

						sb.append(entry).append(", ");
						entry = entry.next;
					}
				}
			}

			sb.replace(sb.length() - 2, sb.length(), "");
			sb.append("]");
			return sb.toString();
		}
	}

	/**
	 * Interni razred tablice koji omogućava iteraciju kroz elemente tablice.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private class IteratorImpl implements Iterator<SimpleHashTable.TableEntry<K, V>> {

		// trenutni redak tablice
		private int currentIndex = 0;
		private TableEntry<K, V> currentEntry = null;
		private double iterModCount;
		
		/**
		 * Inicijalni konstruktor iteratora.
		 * 
		 * @param modificationCount broj modifikacija tablice do stvaranja iteratora
		 */
		public IteratorImpl(double modificationCount) {
			iterModCount = modificationCount;
		}

		@Override
		public boolean hasNext() {
			
			if(iterModCount != modificationCount) 
				throw new ConcurrentModificationException("Nije moguće modificirati kolekciju izvana.");

			int oldIndex = currentIndex;

			for (int i = oldIndex; i < entries.length; i++) {

				TableEntry<K, V> entry = entries[i];

				if (entry == null) {
					oldIndex++;
					continue;
				}

				if (currentEntry == null) {
					return true;
				}

				if (oldIndex != currentIndex) {
					return true;
				}

				while (entry != null) {

					if (currentEntry.equals(entry)) { // element prije sljedećeg

						if (entry.next != null)
							return true;
					}
					entry = entry.next;
				}
				oldIndex++;
			}
			return false;
		}

		@Override
		public TableEntry<K, V> next() {

			if (!hasNext())
				throw new NoSuchElementException("Nema više elemenata.");

			//zadnji indeks tablice na kojoj je bio element
			int oldIndex = currentIndex;

			for (int i = currentIndex; i < entries.length; i++) {

				TableEntry<K, V> entry = entries[i];

				// preskaću se svi parazni redovi
				if (entry == null)
					continue;

				// prvi element
				if (currentEntry == null) {

					currentEntry = entry;
					currentIndex = i;
					
					return entry;
				}

				// razlika nastaje kada se prelazi u novi redak, a
				// ako se prešlo u novi redak onda postoji i neki uređeni par
				// jer bi se inače redak preskočio
				if (oldIndex != currentIndex) {
					currentIndex = i;
					return entry;
				}

				while (entry != null) {

					if (currentEntry.equals(entry)) { // element prije sljedećeg

						if (entry.next != null) {
							
							currentEntry = entry.next;
							currentIndex = i;
							
							return currentEntry;
						}
					}
					entry = entry.next;
				}
				currentIndex++; // prelazak u novi redak ako par nije iza nekog drugog para
			}
			return null;
		}
		
		@Override
		public void remove() {
			
			if(iterModCount != modificationCount) 
				throw new ConcurrentModificationException("Nije moguće modificirati kolekciju izvana.");
			
			if(currentEntry != null) {
				
				SimpleHashTable.this.remove(currentEntry.getKey());
				currentEntry = null;
				iterModCount++;
				
			}else
				throw new IllegalStateException("Nije moguće više puta pozvati metodu nad istim "
						+ "elementom ili izbrisati element bez njegovog dohvata.");
		}
	}

	@Override
	public Iterator<TableEntry<K, V>> iterator() {
		return new IteratorImpl(modificationCount);
	}
}
