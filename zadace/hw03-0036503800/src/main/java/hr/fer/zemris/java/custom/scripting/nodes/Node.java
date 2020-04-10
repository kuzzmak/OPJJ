package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

public class Node {

	private  ArrayIndexedCollection children;
	
	/**
	 * Metoda za dodavanje djece u kolekciju.
	 * 
	 * @throws NullPointerException ako je predano dijete <code>null</code>
	 * @param child dijete koje se dodaje
	 */
	public void addChild(Node child) {
		
		if(child == null) throw new NullPointerException("Nije moguće dodati null.");
		
		if(children == null) children = new ArrayIndexedCollection();
		
		children.add(child);
	}
	
	/**
	 * Metoda za dohvat veličine interne kolekcije djece.
	 * 
	 * @return veličina kolekcije djece
	 */
	public int numberOfChildren() {
		return children.size();
	}
	
	/**
	 * Metoda za dohvat djeteta na indeksu <code>index</code> ako je indeks valjan.
	 * 
	 * @param index indeks na kojem se pokušava dohvatiti dijete
	 * @throws IndexOutOfBoundsException ako <code>index</code> nije valjan
	 * @return dijete na indeksu <code>index</code>
	 */
	public Node getChild(int index) {
		
		if(index < 0 || index > children.size() - 1)
			throw new IndexOutOfBoundsException("Indeks izvan granica.");
		
		return (Node)children.get(index);
	}
}
