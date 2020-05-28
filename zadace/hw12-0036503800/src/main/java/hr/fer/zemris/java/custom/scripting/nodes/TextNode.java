package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Razred koji predstavlja tekstualni zapis unutar nekog taga.
 * 
 * @author Antonio Kuzminski
 *
 */
public class TextNode extends Node {

	String text;
	
	/**
	 * Konstruktor. 
	 * 
	 * @param text tekst koji se ispisuje
	 */
	public TextNode(String text) {
		this.text = text;
	}
	
	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitTextNode(this);
	}

	@Override
	public String toString() {
		return text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TextNode other = (TextNode) obj;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.strip().equals(other.text.strip()))
			return false;
		return true;
	}

}
