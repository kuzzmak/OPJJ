package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {

	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression; // može biti null
	
	public ForLoopNode(ElementVariable variable, 
			Element startExpression, 
			Element endExpression,
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
		
		if(stepExpression != null) {
			sb.append(" ");
			sb.append(stepExpression.asText());
		}
		
		sb.append("$}");

		return sb.toString();
	}
}
