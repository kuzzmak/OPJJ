package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.collections.Processor;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParser {

	private DocumentNode documentNode;

	private ArrayIndexedCollection tokens;
	
	// index trenutnog tokena
	private int index = 0;

	public SmartScriptParser(String text) {

		SmartScriptLexer lexer = new SmartScriptLexer(text);

		tokens = lexer.tokenize();

		parse();
	}

	public DocumentNode getDocmentNode() {

		return documentNode;
	}

	public void parse() {

		ObjectStack tokenStack = new ObjectStack();

		DocumentNode documentNode = new DocumentNode();
		tokenStack.push(documentNode);

		Node currentNode = documentNode;

		boolean inTag = false;
		boolean textNodeCreated = false;
		
		while(index < tokens.size()) {
			
			Object value = tokens.get(index);
			
			if (value == null)
				throw new NullPointerException("Nije moguće predati null na obradu.");

			if (!(value instanceof Token))
				throw new IllegalArgumentException("Nije moguće obraditi objekte koji nisu Token.");
			
			Token token = (Token)value;
			
			// token za početak taga
			if(token.getType() == TokenType.TAG_START) {
				
				if(index + 1 >= tokens.size())
					throw new SmartScriptParserException("Početak taga ne može bit na kraju dokumenta.");
				
				String tagName = (String)((Token)tokens.get(index + 1)).getValue();
				
				
				if(tagName.equals("FOR")) {
					
					// pozicija FOR tokena
					index++;
					Node node = extractFOR();
					
				}else if(tagName.equals("END")) {
					
				}else if(tagName.equals("=")) {
					
				}else throw new SmartScriptParserException("Neispravan tag.");
			}
		}
	}
	
	public ForLoopNode extractFOR() {
		
		ElementVariable variable;
		Element startExpression;
		Element endExpression;
		Element stepExpression; 
		
		
		
		
	}
}
