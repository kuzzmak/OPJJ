package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

public class ForLoopNode extends Node {

	ElementVariable variable;
	Element startExpression;
	Element endExpression;
	Element stepExpression; // može biti null
}
