package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

public class test {
	
	public static void main(String[] args) {
		
		LinkedListIndexedCollection c = new LinkedListIndexedCollection();
		
		c.add(1);
		
		c.add(2);
		
		c.add(3);
		
		Object[] arr = c.toArray();
		System.out.println(Arrays.toString(arr));
		
		c.remove(2);
		c.remove(0);
		c.remove(0);
		arr = c.toArray();
		System.out.println(Arrays.toString(arr));
//		System.out.println(c);
//		System.out.println(c.size());
	}
}
