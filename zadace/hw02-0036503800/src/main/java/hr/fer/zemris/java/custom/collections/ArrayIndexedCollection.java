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
	
	void addAll(Collection other) {
		
	}
	
	public Object[] toArray() {
		
		return new Object[] {this.getElements()};
	}
	
	public int size() {
		return this.size;
	}
	
	public boolean isEmpty() {
		return this.size() == 0;
	}
	
	boolean contains(Object value) {
		
		for(Object o: this.elements) {
			if(o.equals(value)) return true;
		}
		
		return false;
	}
	
	boolean remove(Object value) {
		
		Object[] temp = new Object[this.elements.length];
		
		int index = 0;
		boolean removed = false;
		
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				index = i;
				removed = true;
				break;
			}else {
				temp[i] = this.elements[i];
			}
			
		}
		
		for(int i = index + 1; i < this.size; i++) {
			temp[i] = this.elements[i];
		}
		
		return removed;
	}
	
	public Object[] getElements() {
		return this.elements;
	}
	
	/**
	 * Metoda za dohvat elementa polja na indeksu <code>index</code>.
	 * 
	 * @param index indeks na kojem se pokušava dohvatiti element
	 * @throws IndexOutOfBoundsException ako je predani <code>index</code> izvan granica polja
	 * @return element polja na mjestu <code>index</code>
	 */
	public Object get(int index) {
		
		if(index > this.size - 1 || index < 0) 
			throw new IndexOutOfBoundsException("Nemoguće dohvatiti element izvan indeksa polja.");
		
		return this.elements[index];
	}
	
	/**
	 * Metoda za brisanje elemenata kolekcije. Polje unutar kolekcije ostaje jednake
	 * veličine, ali su mu svi elementi <code>null</code>.
	 * 
	 */
	void clear() {
		
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
	}
	
	
	void insert(Object value, int position) {
		
		if(value == null) 
			throw new NullPointerException("Nije moguće dodati null u kolekciju.");
		if(position < 0 || position > this.size) 
			throw new IndexOutOfBoundsException("Neispravni indeksi za umetanje elementa.");
			
		
	}
	
	/**
	 * Metoda za pronalazak indeksa predanog elementa <code>value</code>.
	 * Ukoliko predani elemnt nije član kolekcije, vraća se vrijednost -1. 
	 * 
	 * @param value element čiji se indeks pokušava naći
	 * @return indeks traženog elementa ako je unutar kolekcije, -1 inače
	 */
	int indexOf(Object value) {
		
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) return i;
		}
		
		return -1;
	}
	
}
