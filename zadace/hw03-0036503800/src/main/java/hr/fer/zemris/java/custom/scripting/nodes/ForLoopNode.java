package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {

	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression; // može biti null

	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("{$");
		sb.append(" FOR ");
		sb.append(variable.asText());
		sb.append(" ");
		sb.append(startExpression.asText());
		sb.append(" ");
		sb.append(endExpression.asText());

		if (stepExpression != null) {
			sb.append(" ");
			sb.append(stepExpression.asText());
		}

		sb.append(" ");
		sb.append("$}");

		// dodavanje djece ako ima
		ElementsGetter eg = this.children.createElementsGetter();

		while (eg.hasNextElement()) {
			sb.append(eg.getNextElement().toString()).append("\n");
		}
		
		sb.append("{$END$}");

		return sb.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((endExpression == null) ? 0 : endExpression.hashCode());
		result = prime * result + ((startExpression == null) ? 0 : startExpression.hashCode());
		result = prime * result + ((stepExpression == null) ? 0 : stepExpression.hashCode());
		result = prime * result + ((variable == null) ? 0 : variable.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ForLoopNode other = (ForLoopNode) obj;
		if (endExpression == null) {
			if (other.endExpression != null)
				return false;
		} else if (!endExpression.equals(other.endExpression))
			return false;
		if (startExpression == null) {
			if (other.startExpression != null)
				return false;
		} else if (!startExpression.equals(other.startExpression))
			return false;
		if (stepExpression == null) {
			if (other.stepExpression != null)
				return false;
		} else if (!stepExpression.equals(other.stepExpression))
			return false;
		if (variable == null) {
			if (other.variable != null)
				return false;
		} else if (!variable.equals(other.variable))
			return false;
		return true;
	}
}
