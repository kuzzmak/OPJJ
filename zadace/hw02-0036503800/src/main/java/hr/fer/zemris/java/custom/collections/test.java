package hr.fer.zemris.java.custom.collections;

public class test {
	
	public static void main(String[] args) {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		for(int i = 0; i < 20; i++) {
			col.add(i);
		}
		
		System.out.println(col.size());
		//System.out.println(col.elements.length);
	}
}
