package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeracija za razred <code>SmartScriptLexer</code> gdje <code>BASIC</code> označava
 * normalni režim rada gdje se generira samo text, a <code>EXTENDED</code> označava
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
