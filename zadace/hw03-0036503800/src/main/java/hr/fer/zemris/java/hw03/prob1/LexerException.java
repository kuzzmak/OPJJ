package hr.fer.zemris.java.hw03.prob1;

public class LexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LexerException() {
		
	}
	
	public LexerException(String message) {
		super(message);
	}
	
	public LexerException(Throwable cause) {
		super(cause);
	}
	
	public LexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
