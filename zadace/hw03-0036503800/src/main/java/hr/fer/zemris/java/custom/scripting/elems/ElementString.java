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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ElementString other = (ElementString) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.strip().equals(other.value.strip()))
			return false;
		return true;
	}
}
