package hr.fer.zemris.java.hw06.shell;

/**
 * Iznimka koja se poziva prilikom nekakve greške za vrijeme
 * izvođenja {@code MyShell}
 * 
 * @see MyShell
 * @author Antonio Kuzminski
 *
 */
public class ShellIOException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ShellIOException() {

	}

	public ShellIOException(String message) {
		super(message);
	}

	public ShellIOException(Throwable cause) {
		super(cause);
	}

	public ShellIOException(String message, Throwable cause) {
		super(message, cause);
	}
}
