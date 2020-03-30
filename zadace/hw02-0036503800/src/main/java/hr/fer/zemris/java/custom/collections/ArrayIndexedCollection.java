package hr.fer.zemris.java.custom.collections;

/**
 * Razred koji predstavlja implementaciju kolekcija pomoću polja objekata.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ArrayIndexedCollection extends Collection {
	
	private int size;
	private Object[] elements;
	
	public ArrayIndexedCollection(int capacity) {
		if(capacity < 1) throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
		elements = new Object[capacity];
	}
	
	public ArrayIndexedCollection() {
		this(16);
	}
	
	public ArrayIndexedCollection(Collection col) {
		
		if(col == null) throw new NullPointerException("Predana kolekcija ne može biti null.");
		
		this.elements = col.toArray();
		this.size = col.size();
	}
	
	public ArrayIndexedCollection(Collection col, int initialCapacity) {
		
		if(initialCapacity < col.size()) {
//			this(col);
		}
		else {
			this.elements = new Object[initialCapacity];
			this.addAll(col);
			this.size = col.size();
		}
		// napraviti da se dodaju elementi
	}
	
	/**
	 * Metoda za dodavanje nove vrijednosti u postojeću kolekciju.
	 * Ako je postojeća kolekcija popunjena, stvara se nova, dvostruko veća.
	 * 
	 * @param value vrijednost koja se dodaje u kolekciju
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	public void add(Object value) {
		
		if(value == null) throw new NullPointerException("Nije moguće dodati null u kolekciju.");
		
		try {
			this.elements[this.size] = value;
			this.size ++;
		}catch(IndexOutOfBoundsException e){
			// kopija trenutne kolekcije			
			ArrayIndexedCollection temp = new ArrayIndexedCollection(this);
			// stvaranje nove i kopiranje
			this.elements = new Object[this.size * 2];
			this.addAll(temp);
			this.elements[this.size] = value;
			this.size ++;
		}
	}
	
	public Object[] toArray() {
		
		return new Object[] {this.getElements()};
	}
	
	public int size() {
		return this.size;
	}
	
	public Object[] getElements() {
		return this.elements;
	}
	
}
