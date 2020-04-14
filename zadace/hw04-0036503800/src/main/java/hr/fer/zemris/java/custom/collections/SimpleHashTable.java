package hr.fer.zemris.java.custom.collections;

public class SimpleHashTable<K, V> {

	private static class TableEntry<K, V>{
		
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
		public String toString() {
			return "(" + key + ", " + value + ")";
		}
	}
	
	private TableEntry<K, V>[] entries;
	private int size = 0;
	
	/**
	 * Inicijalni konstruktor koji postavlja veličinu tablice na 16 slotova.
	 * 
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable() {
		
		entries = (TableEntry<K, V>[]) new TableEntry[2];
	}
	
	/**
	 * Konstruktor koji stvara tablicu velčine <code>capacity</code> ako je 
	 * <code>capacity</code> potencija broja 2, a ako nije onda najbliža potencija.
	 * 
	 * @param capacity veličina tablice koja se želi stvoriti
	 */
	@SuppressWarnings("unchecked")
	public SimpleHashTable(int capacity) {
		
		if(capacity < 1)
			throw new IllegalArgumentException("Nije moguće stvoriti tablicu dimenzija: " + capacity);
		
		int power = (int) Math.ceil(Math.log(capacity) / Math.log(2));
		
		entries = (TableEntry<K, V>[]) new Object[(int) Math.pow(2, power)];
	}
	
	/**
	 * Metoda za stavljanje kombinacije (K, V) u tablicu. Nije dozvoljeno
	 * koristiti <code>null</code> kao vrijednost ključa. Ako ključ već postoji
	 * novim umetanjem se vrijednost <code>V</code> pod tim ključem ažurira
	 * na novu vrijednost.
	 * 
	 * @throws NullPointerException ako je kao ključ predana <code>null</code>
	 *                              vrijednost
	 * @param key ključ pod kojim se ubacuje nova vrijednost
	 * @param value vrijednost koja se ubacuje
	 */
	public void put(K key, V value) {
		
		if(key == null)
			throw new NullPointerException("Ključ ne može biti null.");
		
		int slot = Math.abs(key.hashCode()) % entries.length;;
		
		if(entries[slot] != null) {
			
			TableEntry<K, V> entry = entries[slot];
			
			while(entry != null) {
				
				if(entry.getKey().equals(key)) {
					entry.setValue(value);
					return;
				}
				
				// zadnji element nekog slota
				if(entry.next == null) {
					entry.next = new TableEntry<>(key, value);
					size++;
					return;
				}
				entry = entry.next;
			}
		}else {
			entries[slot] = new TableEntry<>(key, value);
			size++;
		}
	}
	
	public V get(Object key) {
		return null;
	}
	
	public int size() {
		return 0;
	}
	
	public boolean containsKey(Object key) {
		return false;
	}
	
	public boolean containsValue(Object value) {
		return false;
	}
	
	public void remove(Object key) {
		
	}
	
	public boolean isEmpty() {
		return false;
	}

	@Override
	public String toString() {
		return "SimpleHashTable []";
	}
}
