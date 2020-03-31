package hr.fer.zemris.java.custom.collections;


public class test {
	
	public static void main(String[] args) {
		
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		
		c.add(1);
		
		c.add(2);
		
		c.add(3);
		
		System.out.println(c.get(1));
	}
}
