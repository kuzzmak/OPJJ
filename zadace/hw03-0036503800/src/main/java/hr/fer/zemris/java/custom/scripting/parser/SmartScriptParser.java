package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;

public class SmartScriptParser {
	
	private DocumentNode documentNode;
	
	public SmartScriptParser(String text) {
		
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		lexer.parse();
	}
	
	public DocumentNode getDocmentNode() {
		
		return documentNode;
	}
}
