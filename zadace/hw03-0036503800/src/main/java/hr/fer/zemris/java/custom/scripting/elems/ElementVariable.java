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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ElementVariable other = (ElementVariable) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
