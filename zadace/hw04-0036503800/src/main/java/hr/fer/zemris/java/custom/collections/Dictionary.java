package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji predstavlja implementaciju rječnika u ulozi adaptera
 * oko razreda kolekcije <code>ArrayIndexedCollection</code> gdje 
 * je svaki element rječnika objekt tipa <code>Tuple<K, V></code>.
 * 
 * @author Antonio Kuzminski
 *
 * @param <K> ključ rječnika
 * @param <V> vrijednost pod ključem <code>K</code>
 */
public class Dictionary<K, V> {

	/**
	 * Interni razred rječnika koji predstavlja jedan unos tipa (K, V), gdje
	 * <code>K</code> predstavlja ključ, a <code>V</code> vrijednost.
	 * 
	 * @author Antonio Kuzminski
	 *
	 * @param <Key>   ključ unosa
	 * @param <Value> vrijednost unosa
	 */
	class Tuple<Key, Value> {

		Key key;
		Value value;

		public Tuple(Key key, Value value) {
			this.key = key;
			this.value = value;
		}

		public Key getKey() {
			return key;
		}

		public Value getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "(" + key + ", " + value + ")";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + getEnclosingInstance().hashCode();
			result = prime * result + ((key == null) ? 0 : key.hashCode());
			result = prime * result + ((value == null) ? 0 : value.hashCode());
			return result;
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Tuple other = (Tuple) obj;
			if (!getEnclosingInstance().equals(other.getEnclosingInstance()))
				return false;
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

		private Dictionary getEnclosingInstance() {
			return Dictionary.this;
		}
	}

	private ArrayIndexedCollection<Tuple<K, V>> dict;

	/**
	 * Inicijalni konstruktor.
	 * 
	 */
	public Dictionary() {
		dict = new ArrayIndexedCollection<>();
	}

	/**
	 * Metoda za stavljanje novih unosa u rječnik. Kao ključ, <code>null</code>
	 * vrijdnost nije dopuštena.
	 * 
	 * @throws NullPointerException ako je predani ključ <code>null</code>
	 *                              vrijednost
	 * @param key   ključ novog unosa
	 * @param value vrijdnost novog unosa
	 */
	public void put(K key, V value) {

		if (key == null)
			throw new NullPointerException("Null nije valjan ključ.");

		ElementsGetter<Tuple<K, V>> eg = dict.createElementsGetter();

		while (eg.hasNextElement()) {

			Tuple<K, V> t = eg.getNextElement();

			if (t.getKey().equals(key)) {
				t.value = value;
				return;
			}
		}

		dict.add(new Tuple<>(key, value));
	}

	/**
	 * Metoda za provjeru je li rječnik prazan.
	 * 
	 * @return istina ako je prazan, laž inače
	 */
	public boolean isEmpty() {
		return dict.size() == 0;
	}

	/**
	 * Metoda za dohvat veličine rješnika.
	 * 
	 * @return veličina rječnika
	 */
	public int size() {
		return dict.size();
	}

	/**
	 * Metoda za dohvat vrijednost pod ključem <code>key</code>. Ako 
	 * vrijednost pod ključem ne postoji, vraća se <code>null</code>.
	 * 
	 * @param key ključ pod kojim se pokušava dohvatiti vrijednost
	 * @return vrijednost pod kjlučem <code>key</code> ako postoji
	 */
	public V get(Object key) {

		ElementsGetter<Tuple<K, V>> eg = dict.createElementsGetter();

		while (eg.hasNextElement()) {

			Tuple<K, V> t = eg.getNextElement();

			if (t.getKey().equals(key)) {
				return t.getValue();
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return dict.toString();
	}
}
