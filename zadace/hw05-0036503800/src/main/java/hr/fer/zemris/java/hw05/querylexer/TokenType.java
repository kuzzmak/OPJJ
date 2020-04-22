package hr.fer.zemris.java.hw05.querylexer;

/**
 * Enumeracija koja predstavlja sastavne dijelove stvakog upita.
 * 
 * @author Antonio Kuzminski
 *
 */
public enum TokenType {

	// ime atributa u tablici
	ATTRIBUTE_NAME,
	// vrsta operatora
	OPERATOR,
	// vrsta logičkog operatora
	LOGICAL_OPERATOR,
	// string konstanta
	STRING_LITERAL,
	// kraj upita
	EOF
}
