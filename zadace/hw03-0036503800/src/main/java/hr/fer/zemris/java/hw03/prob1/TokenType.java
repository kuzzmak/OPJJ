package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeracija vrsta tokena od kojih se sastoji program.
 * 
 * @author Antonio Kuzminski
 *
 */
public enum TokenType {
	// označava da nema više tokena
	EOF, 
	// označava riječ
	WORD, 
	// označava broj
	NUMBER, 
	// označava simbol
	SYMBOL
}
