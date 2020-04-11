package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja konstantu cijelog broja.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ElementConstantInteger extends Element {

	private int value;
	
	public ElementConstantInteger(int value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
