package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Razred iznimke koji se generira prilikom obrade neispravnih tokena.
 * 
 * @author Antonio Kuzminski
 *
 */
public class SmartScriptingLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public SmartScriptingLexerException() {

	}

	public SmartScriptingLexerException(String message) {
		super(message);
	}

	public SmartScriptingLexerException(Throwable cause) {
		super(cause);
	}

	public SmartScriptingLexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
