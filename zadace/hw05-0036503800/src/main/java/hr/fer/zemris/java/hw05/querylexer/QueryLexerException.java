package hr.fer.zemris.java.hw05.querylexer;

/**
 * Razred iznimke koji se poziva prilikom greške kod leksičke analize upita.
 * 
 * @author Antonio Kuzminski
 *
 */
public class QueryLexerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public QueryLexerException() {

	}

	public QueryLexerException(String message) {
		super(message);
	}

	public QueryLexerException(Throwable cause) {
		super(cause);
	}

	public QueryLexerException(String message, Throwable cause) {
		super(message, cause);
	}
}
