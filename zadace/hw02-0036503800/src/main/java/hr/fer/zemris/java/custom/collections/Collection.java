package hr.fer.zemris.java.custom.collections;

public class Collection {
	
	protected Collection() {
		
	}
	
	boolean isEmpty() {
		return this.size() == 0;
	}
	
	int size() {
		return 0;
	}
	
	void add(Object value) {
		
	}
	
	boolean contains(Object value) {
		return false;
	}
	
	boolean remove(Object value) {
		return false;
	}
	
	Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	
	void forEach(Processor processor) {
		
	}
	
	void addAll(Collection other) {
		
	}
	
	void clear() {
		
	}
	
}
