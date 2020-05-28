package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeracija za razred <code>SmartScriptLexer</code> gdje <code>TEXT</code> označava
 * normalni režim rada gdje se generira samo text, a <code>TAG</code> označava
 * režim rada gdje se generiraju tagovi.
 * 
 * 
 * @author Antonio Kuzminski
 *
 */
public enum SmartScriptLexerState {

	TEXT,
	TAG
}
