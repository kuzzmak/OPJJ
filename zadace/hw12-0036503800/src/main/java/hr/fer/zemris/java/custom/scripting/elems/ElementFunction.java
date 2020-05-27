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
		ElementFunction other = (ElementFunction) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.strip().toLowerCase().equals(other.name.strip().toLowerCase()))
			return false;
		return true;
	}
}
