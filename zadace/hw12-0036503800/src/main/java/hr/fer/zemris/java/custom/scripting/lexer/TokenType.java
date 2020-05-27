package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Enumeracija vrsta tokena od kojih se sastoji dokument.
 * 
 * @author Antonio Kuzminski
 *
 */
public enum TokenType {

	// običan tekst
	WORD,
	// broj s točkom ili bez
	NUMBER,
	// aritmetički operatori
	OPERATOR,
	// vrsta taga u kojem se leksički analizator nalazi
	TAG_NAME,
	// navodnici
	QUOTEMARK,
	// kosa crta ulijevo
	BACKSLASH,
	// oznaka početka imena funkcije, @
	FUNCTION_START,
	// kraj dokumenta
	EOF,
	// znak za početak taga {$
	TAG_START,
	// znak za kraj taga $}
	TAG_END,
	// praznine
	SPACES
}
