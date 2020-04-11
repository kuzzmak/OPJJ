package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja neku funkciju. Ispravno ime funkcije
 * počinje slovom, a slijedi 0 ili više brojeva, slova ili "_".
 * 
 * @author Antonio Kuzminski
 *
 */
public class ElementFunction extends Element {

	private String name;
	
	public ElementFunction(String name) {
		this.name = name;
	}
	
	@Override
	public String asText() {
		return "@" + name;
	}
}
