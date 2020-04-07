package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

/**
 * Razred koji predstavlja implementaciju kolekcija pomoću polja objekata.
 * Moguće je imati višestruke iste elemente, ali nije moguće pohraniti null 
 * vrijednosti.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ArrayIndexedCollection implements Collection {
	
	private int size;
	private Object[] elements;
	
	/**
	 * Konstruktor gdje se inicijalizira interno polje na velicinu <code>capacity</code>.
	 * 
	 * @param capacity inicijalna veličina kolekcije
	 * @throws IllegalArgumentException ako je <code>capacity</code> manji od 1
	 */
	public ArrayIndexedCollection(int capacity) {
		if(capacity < 1) throw new IllegalArgumentException("Kapacitet ne može biti manji od 1!");
		this.elements = new Object[capacity];
	}
	
	/**
	 * Konstruktor bez parametara, inicijalna veličina kolekcije je 16 elemenata.
	 * 
	 */
	public ArrayIndexedCollection() {
		this(16);
	}
	
	/**
	 * Konstruktor gdje se kao inicijalni elementi kopiraju elementi predane kolekcije <code>col</cpde>.
	 * 
	 * @param col kolekcija čiji se elementi kopiraju u novonastalu kolekciju
	 * @throws NullPointerException ako je predana kolekcija <code>null</cpde>
	 */
	public ArrayIndexedCollection(Collection col) {
		
		if(col == null) throw new NullPointerException("Predana kolekcija ne može biti null.");
		
		this.elements = col.toArray();
		this.size = col.size();
	}
	
	/**
	 * Konstruktor gdje se kao kao inicijalni elementi uzimaju elementi kolekcije <code>col</cpde>, veličine
	 * <code>initialCapacity</cpde>. Ako je <code>initialCapacity</cpde> manji od broja elemenata
	 * predane kolekcije, kao veličina nove kolekcije uzima se veličina predane kolekcije <code>col</cpde>.
	 * 
	 * @param col
	 * @param initialCapacity
	 */
	public ArrayIndexedCollection(Collection col, int initialCapacity) {
		
		if(col == null) throw new NullPointerException("Predana kolekcija ne može biti null.");
		
		if(initialCapacity < col.size()) {
			this.elements = new Object[col.size()];
			this.addAll(col);
		}
		else {
			this.elements = new Object[initialCapacity];
			this.addAll(col);
		}
	}
	
	@Override
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
			this.size = 0;
			this.addAll(temp);
			this.elements[this.size] = value;
			this.size ++;
		}
	}
	
	@Override
	public Object[] toArray() {
		
		return this.elements;
	}
	
	@Override
	public int size() {
		return this.size;
	}
	
	@Override
	public boolean contains(Object value) {
		
		if(value == null) return false;
		
		for(Object o: this.elements) {
			if(o != null && o.equals(value)) return true;
		}
		
		return false;
	}
	
	@Override
	public boolean remove(Object value) {
		
		if(value == null) return false;
		
		Object[] temp = new Object[this.elements.length];
		
		// indeks vrijednosti koja se uklanja ako postoji		
		int index = -1;
		
		// kopiranje svih vrijednosti do indexa u novo polje		
		for(int i = 0; i < this.size; i++) {
			if(this.elements[i].equals(value)) {
				index = i;
				break;
			}else {
				temp[i] = this.elements[i];
			}
			
		}
		
		// postoji vrijednost koja se treba ukloniti		
		if(index != -1) {
			// slučaj ako je zadnji element traženi element, onda se ne 
			// radi ništa jer se već polje iskopiralo do toga dijela			
			if(index == this.size - 1) {
				
			}else {
				// kopiranje ostatka polja ako ga ima
				for(int i = index; i < this.size - 1; i++) {
					temp[i] = this.elements[i + 1];
				}
			}
			
			this.elements = temp;
			this.size--;
			return true;
		}
		
		return false;
	}
	
	/**
	 * Metoda za uklanjanje elementa iz kolekcije na mjestu <code>index</code>.
	 * 
	 * @param index mjesto uklanjanja elementa
	 * @throws IndexOutOfBoundsException ako je <code>index</code> van granica kolekcije
	 */
	public void remove(int index) {
		
		if(index < 0 || index > this.size - 1) 
			throw new IndexOutOfBoundsException("Nije moguće ukloniti element izvan kolekcije.");
		
		Object[] elements = new Object[this.elements.length];
		
		for(int i = 0; i < index; i++) {
			elements[i] = this.elements[i];
		}
		
		for(int i = index + 1; i < this.size; i++) {
			elements[i - 1] = this.elements[i];
		}
		
		this.size--;
		this.elements = elements;
	}
	
	@Override
	public void clear() {
		
		for(int i = 0; i < this.size; i++) {
			this.elements[i] = null;
		}
		
		this.size = 0;
	}
	
	@Override
	public void forEach(Processor processor) {
		
		for(Object o: this.elements) {
			if(o != null) {
				processor.process(o);
			}
		}
	}
	
	/**
	 * Metoda za umetanje elementa u kolekciju na poziciji <code>position</code>.
	 * 
	 * @param value element koji se umeće u kolekciju
	 * @param position pozicija na kojoj se umeće element
	 * @throws NullPointerException ako je predan element <code>null</code> vrijednost
	 * @throws IndexOutOfBoundsException ako je indeks < 0 ili > od veličine trenutne kolekcije
	 */
	void insert(Object value, int position) {
		
		if(value == null) 
			throw new NullPointerException("Nije moguće dodati null u kolekciju.");
		if(position < 0 || position > this.size) 
			throw new IndexOutOfBoundsException("Neispravan indeks za umetanje elementa.");
			
		Object[] temp;
		
		// ako je kolekcija puna, stvara se nova dvostruko veća
		if(this.size == this.elements.length) {
			temp = new Object[this.elements.length * 2];
		}else {
			temp = new Object[this.elements.length];
		}
		
		// kopiranje prvog dijela elemenata do position
		for(int i = 0; i < position; i++) {
			temp[i] = this.elements[i];
		}
		
		temp[position] = value;
		
		// ostatak elemenata nakon position ako nije udvostručeno polje
		if(temp.length == this.elements.length) {
			for(int i = position + 1; i < this.elements.length; i++) {
				temp[i] = this.elements[i - 1];
			}
		}else {
			
			for(int i = position + 1; i < this.elements.length + 1; i++) {
				temp[i] = this.elements[i - 1];
			}
		}
		
		this.size++;
		this.elements = temp;
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

	@Override
	public String toString() {
		return Arrays.toString(elements);
	}
	
}
