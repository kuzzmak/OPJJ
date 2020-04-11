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
		
		StringBuilder sb = new StringBuilder();
		
		ElementsGetter eg = this.children.createElementsGetter();
		
		while(eg.hasNextElement()) {
			sb.append(eg.getNextElement().toString()).append("\n");
		}
		
		return sb.toString();
	}
}
