package hr.fer.zemris.java.custom.collections;

public class test {
	
	public static void main(String[] args) {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		
		for(int i = 0; i < 4; i++) {
			col.add(i);
		}
		
		Object[] elem = col.toArray();
		
		for(int i = 0; i < elem.length; i++) {
			System.out.println(elem[i]);
		}
		System.out.println();
		
		Collection c = new ArrayIndexedCollection();
		for(int i = 0; i < 15; i++) {
			c.add(i);
		}
			
		col.addAll(c);
		
		elem = col.getElements();
		for(int i = 0; i < elem.length; i++) {
			System.out.println(elem[i]);
		}
	}
}
