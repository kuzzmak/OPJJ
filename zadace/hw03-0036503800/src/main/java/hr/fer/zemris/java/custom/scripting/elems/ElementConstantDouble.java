package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja decimalni broj.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ElementConstantDouble extends Element {

	private double value;
	
	public ElementConstantDouble(double value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return String.valueOf(value);
	}
}
