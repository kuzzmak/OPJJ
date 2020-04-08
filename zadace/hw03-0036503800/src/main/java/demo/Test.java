package demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.Collection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.LinkedListIndexedCollection;
import hr.fer.zemris.java.custom.collections.List;

public class Test {
	
	public static void main(String[] args) {
		
		List col1 = new LinkedListIndexedCollection();
//		Collection col2 = new ArrayIndexedCollection();
		
		col1.add("Ivo");
//		col1.add("Ana");
//		col1.add("Jasna");
		
		
//		col2.add("Jasmina");
//		col2.add("Štefanija");
//		col2.add("Karmela");
		
		ElementsGetter getter1 = col1.createElementsGetter();
		
//		System.out.println(getter1.hasNextElement());
//		ElementsGetter getter2 = col1.createElementsGetter();
//		ElementsGetter getter3 = col2.createElementsGetter();
//		getter1.getNextElement();
//		System.out.println(getter1.hasNextElement());
//		getter1.getNextElement();
//		System.out.println(getter1.hasNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
//		System.out.println("Jedan element: " + getter1.getNextElement());
		
		
		getter1.processRemaining(System.out::println);
		
//		System.out.println("Jedan element: " + getter2.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
//		System.out.println("Jedan element: " + getter3.getNextElement());
	}
	
}
