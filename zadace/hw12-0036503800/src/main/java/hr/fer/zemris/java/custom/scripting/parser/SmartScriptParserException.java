package hr.fer.zemris.java.custom.scripting.parser;

/**
 * Razred iznimke kojii se generira prilikom parsiranje dokumenta
 * razreda <code>SmartScriptParser</code>.
 * 
 * @see SmartScriptParser
 * @author Antonio Kuzminski
 *
 */
public class SmartScriptParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SmartScriptParserException() {

	}

	public SmartScriptParserException(String message) {
		super(message);
	}

	public SmartScriptParserException(Throwable cause) {
		super(cause);
	}

	public SmartScriptParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
