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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
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
		ElementOperator other = (ElementOperator) obj;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.strip().equals(other.symbol.strip()))
			return false;
		return true;
	}
}
