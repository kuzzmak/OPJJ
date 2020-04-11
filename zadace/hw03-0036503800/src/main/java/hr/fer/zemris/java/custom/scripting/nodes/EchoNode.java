package hr.fer.zemris.java.custom.scripting.nodes;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;

/**
 * Node koji predstavlja naredbu koja dinamički generira neki tekstualni izlaz.
 * 
 * @author Antonio Kuzminski
 *
 */
public class EchoNode extends Node {

	Element[] elements;
	
	ArrayIndexedCollection col;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 */
	public EchoNode() {
		
		this.col = new ArrayIndexedCollection();
	}
	
	/**
	 * Metoda za dodavanje elemenata u internu kolekciju.
	 * 
	 * @throws NullPointerException ako je predani element null
	 * @throws IllegalArgumentException ako element nije tipa Element
	 * @param element element koji se pokušava dodati
	 * @see Element
	 */
	public void addElement(Element element) {
		
		if(element == null)
			throw new NullPointerException("Nije moguće dodati null kao element.");
		
		if(!(element instanceof Element))
			throw new IllegalArgumentException("Nije moguće dodati objekt koji nije tipa Element.");
		
		col.add(element);
	}
	
	/**
	 * Metoda za dohvat elemenata iz kolekcije.
	 * 
	 * @return polje elemenata
	 */
	public Element[] getElements() {
		
		elements = new Element[col.size()];
		
		for(int i = 0; i < col.size(); i++) {
			elements[i] = (Element)col.get(i);
		}
		
		return elements;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("{$= ");
		
		Element[] elements = getElements();
		
		for(Element e: elements) sb.append(e.asText()).append(" ");
		
		sb.append(" $}");
		
		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((col == null) ? 0 : col.hashCode());
		result = prime * result + Arrays.hashCode(elements);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		EchoNode other = (EchoNode) obj;
		if (col == null) {
			if (other.col != null)
				return false;
		} else if (!col.equals(other.col))
			return false;
		if (!Arrays.equals(elements, other.elements))
			return false;
		return true;
	}
}
