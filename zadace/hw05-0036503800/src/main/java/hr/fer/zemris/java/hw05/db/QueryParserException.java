package hr.fer.zemris.java.hw05.db;

/**
 * Razred iznimke koji se poziva ako je prilikom parsiranja
 * tokena došlo do greške.
 * 
 * @author Antonio Kuzminski
 *
 */
public class QueryParserException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QueryParserException() {

	}

	public QueryParserException(String message) {
		super(message);
	}

	public QueryParserException(Throwable cause) {
		super(cause);
	}

	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}
}
