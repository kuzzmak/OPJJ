package demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;

public class Test {
	
	public static void main(String[] args) {
		
		Collection col1 = new ArrayIndexedCollection();
//		Collection col2 = new LinkedListIndexedCollection();
		
		col1.add("Ivo");
		col1.add("Ana");
		col1.add("Jasna");
		
//		col2.add("Jasmina");
//		col2.add("Štefanija");
//		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
//		ElementsGetter getter2 = col1.createElementsGetter();
//		ElementsGetter getter3 = col2.createElementsGetter();
		
		System.out.println("Jedan element: " + getter1.getNextElement());
		System.out.println("Jedan element: " + getter1.getNextElement());
		
		col1.clear();
		System.out.println("Jedan element: " + getter1.getNextElement());
		
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
	}
	
}
