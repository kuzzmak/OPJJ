package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ElementsGetter;

/**
 * Razred koji predstavlja dokument parsiran u Node-ove kao cjelinu.
 * 
 * @author Antonio Kuzminski
 *
 */
public class DocumentNode extends Node {

	@Override
	public String toString() {

		if (children != null) {

			StringBuilder sb = new StringBuilder();

			ElementsGetter eg = this.children.createElementsGetter();

			while (eg.hasNextElement()) {
				sb.append(eg.getNextElement().toString()).append("\n");
			}

			return sb.toString();
		}
		
		return "";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DocumentNode other = (DocumentNode) obj;
		if (children == null) {
			if (other.children != null)
				return false;
		} else if (!children.equals(other.children))
			return false;
		return true;
	}
}
