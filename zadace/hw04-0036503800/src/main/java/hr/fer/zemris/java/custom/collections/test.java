package hr.fer.zemris.java.custom.collections;

import java.util.ArrayList;

public class test {

//	ArrayList<Integer> ar = new ArrayList<>();

	public static void main(String[] args) {

		ArrayIndexedCollection<String> col1 = new ArrayIndexedCollection<>();
		col1.add("1");
		
		ArrayIndexedCollection<Integer> col2 = new ArrayIndexedCollection<>();
		col2.add(2);
		
		Processor<String> p = new Processor<>() {
			@Override
			public void process(String value) {
				
				System.out.println(value);
				
			}
		};
		
		col1.forEach(p);
	}
}
