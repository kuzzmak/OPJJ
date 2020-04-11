package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja varijablu u nekom tagu.
 * Ta varijabla mora početi slovom, a može slijediti 0 ili više
 * brojeva, slova ili "_".
 * 
 * @author Antonio Kuzminski
 *
 */
public class ElementVariable extends Element {

	private String name;
	
	public ElementVariable(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return name;
	}
}
