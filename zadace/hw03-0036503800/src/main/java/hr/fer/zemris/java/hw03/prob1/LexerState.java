package hr.fer.zemris.java.hw03.prob1;

/**
 * Enumeracija koja predstavlja režime rada parsera. Pojavom simbola # prelazi se
 * u prošireni način rada te to sve traje do ponovne pojave istog kada se prelazi
 * u osnovno i tako redom.
 * 
 * @author Antonio Kuzminski
 *
 */
public enum LexerState {

	// osnovno stanje
	BASIC,
	// prošireno stanje
	EXTENDED
}
