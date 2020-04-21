package hr.fer.zemris.java.custom.collections;

/**
 * Razred iznimke koji predstavlja prazan stog.
 * 
 * @author Antonio Kuzminski
 *
 */
public class EmptyStackException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmptyStackException() {
		
	}
	
	public EmptyStackException(String message) {
		super(message);
	}
	
	public EmptyStackException(Throwable cause) {
		super(cause);
	}
	
	public EmptyStackException(String message, Throwable cause) {
		super(message, cause);
	}
}
