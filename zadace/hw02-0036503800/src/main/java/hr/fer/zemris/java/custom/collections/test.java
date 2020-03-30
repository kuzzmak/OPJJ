package hr.fer.zemris.java.custom.collections;

public class test {
	
	public static void main(String[] args) {
		
		ArrayIndexedCollection col = new ArrayIndexedCollection();
		
		
		for(int i = 0; i < 16; i++) {
			col.add(i);
		}
		
		System.out.println(col);
		System.out.println();
		
		System.out.println(col.remove(null));
		
		System.out.println(col);
	}
}
