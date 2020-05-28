package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

/**
 * Razred koji predstavlja naredbu for. Sastoji se od
 * početnog, uvjetnog, završnog izraza i varijable 
 * po kojoj se iterira. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class ForLoopNode extends Node {

	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression; // može biti null

	/**
	 * Konstruktor.
	 * 
	 * @param variable varijabla po kojoj se iterira
	 * @param startExpression početni izraz od kojeg kreće iteracija
	 * @param endExpression završni izraz
	 * @param stepExpression veličina koraka
	 */
	public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression,
			Element stepExpression) {

		this.variable = variable;
		this.startExpression = startExpression;
		this.endExpression = endExpression;
		this.stepExpression = stepExpression;
	}
	
	/**
	 * Metoda za dohvat varijable po kojoj se iterira.
	 * 
	 * @return varijabla po kojoj se iterira
	 */
	public ElementVariable getVariable() {
		return variable;
	}

	/**
	 * Metoda za dohvat početnog izraza.
	 * 
	 * @return početni izraz
	 */
	public Element getStartExpression() {
		return startExpression;
	}

	/**
	 * Metoda za dohvar završnog izraza.
	 * 
	 * @return završni izraz
	 */
	public Element getEndExpression() {
		return endExpression;
	}

	/**
	 * Metoda za dohvat veličine koraka.
	 * 
	 * @return veličina koraka
	 */
	public Element getStepExpression() {
		return stepExpression;
	}

	@Override
	public void accept(INodeVisitor visitor) {
		visitor.visitForLoopNode(this);
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

		if (children != null) {
			// dodavanje djece ako ima
			ElementsGetter<Node> eg = this.children.createElementsGetter();

			while (eg.hasNextElement()) {
				sb.append(eg.getNextElement().toString()).append("\n");
			}
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
