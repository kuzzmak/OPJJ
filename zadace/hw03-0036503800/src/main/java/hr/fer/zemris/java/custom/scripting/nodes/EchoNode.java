package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;

public class EchoNode extends Node {

	Element[] elements;
	
	ArrayIndexedCollection col;
	
	public EchoNode() {
		
		this.col = new ArrayIndexedCollection();
	}
	
	public void addElement(Element element) {
		
		if(element == null)
			throw new NullPointerException("Nije moguće dodati null kao element.");
		
		if(!(element instanceof Element))
			throw new IllegalArgumentException("Nije moguće dodati objekt koji nije tipa Element.");
		
		col.add(element);
	}
	
	public Element[] getElements() {
		
		elements = new Element[col.size()];
		
		for(int i = 0; i < col.size(); i++) {
			elements[i] = (Element)col.get(i);
		}
		
		return elements;
	}
}
