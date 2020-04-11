package hr.fer.zemris.java.custom.scripting.elems;

/**
 * Razred koji predstavlja neku vrstu operatora.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ElementOperator extends Element {

	private String symbol;
	
	public ElementOperator(String symbol) {
		this.symbol = symbol;
	}
	
	@Override
	public String asText() {
		return symbol;
	}
}
