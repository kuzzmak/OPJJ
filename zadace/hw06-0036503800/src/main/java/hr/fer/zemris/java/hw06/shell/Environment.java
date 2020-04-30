package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Sučelje za komunikaciju s {@code MyShell}
 * 
 * @author Antonio Kuzminski
 *
 */
public interface Environment {

	/**
	 * Metoda za čitanje unosa korisnika.
	 * 
	 * @return string reprezentacija unesenog retka
	 * @throws ShellIOException ukoliko je došlo do greške prilikom čitanja
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Metoda za ispis u konzolu gdje se ne prelazi u novi redak.
	 * 
	 * @param text tekst koji je potrebno ispisati u konzolu
	 * @throws ShellIOException ako je došlo do greške prilikom ispisa
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Metoda za ispis u konzolu gdje se prelazi u novi redak.
	 * 
	 * @param text tekst koji se ispisuje u konzolu
	 * @throws ShellIOException ako je došlo do greške prilikom ispisa
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * Metoda za dohvat mogućih naredbi.
	 * 
	 * @return mapa ključ: ime naredbe, vrijednost: naredba tipa {@code ShellCommand}
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Metoda za dohvat simbola koji se koristi za string
	 * koji se proteže na više redaka. Svaki redak osim prvog
	 * mora na početku imati ovaj simbol.
	 * 
	 * @return simbol
	 */
	Character getMultiLineSymbol();
	
	/**
	 * Metoda za postavljanje simbola koji se koristi za string
	 * koji se proteže na više redaka. Svaki redak osim prvog
	 * mora na početku imati ovaj simbol.
	 * 
	 * @param symbol
	 */
	void setMultiLineSymbol(Character symbol);
	
	/**
	 * Metoda za dohvat simbola koji je na početku retka za unos.
	 * 
	 * @return simbol
	 */
	Character getPromptSymbol();
	
	/**
	 * Metoda koja se koristi za postavljanje simbola koji 
	 * se nalazi na početku retka za unosa.
	 * 
	 * @param symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * Metoda za dohvat simbola koji označava da se string
	 * proteže u sljedeći redak.
	 * 
	 * @return simbol
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Metoda za postavljanje simbola koji označava da se string
	 * proteže u sljedeći redak.
	 * 
	 * @param symbol simbol
	 */
	void setMoreLinesSymbol(Character symbol);
}
