package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja niz znakova u nekom tagu.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ElementString extends Element {

	private String value;
	
	public ElementString(String value) {
		this.value = value;
	}
	
	@Override
	public String asText() {
		return "\"" + value + "\"";
	}
}
