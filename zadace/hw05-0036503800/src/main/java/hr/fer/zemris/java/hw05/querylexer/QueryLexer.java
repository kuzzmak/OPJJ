package hr.fer.zemris.java.hw05.querylexer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Razred za jednostavnu tokenizaciju upita.
 * 
 * @author Antonio Kuzminski
 *
 */
public class QueryLexer {

	private char[] data;

	private int currentIndex = 0;

	// imena mogućih atributa
	List<String> attributeNames;
	// operatori i početci operatora (<, <=, =, !=, ...)
	List<Character> operatorStart;
	// praznine
	private static List<Character> spaces;
	// popis logičkih operatora
	List<String> logicalOperators;

	private Token token;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param query tekst upita
	 */
	public QueryLexer(String query) {

		attributeNames = new ArrayList<>(Arrays.asList("jmbag", "lastName", "firstName", "finalGrade"));

		operatorStart = new ArrayList<>(Arrays.asList('<', '>', '!', '='));

		logicalOperators = new ArrayList<>(Arrays.asList("and", "or", "not"));

		spaces = new ArrayList<>(Arrays.asList(' ', '\t', '\n', '\r'));
		
		// odvajanje riječi query od ostatka upita
		data = query.split("\\s+", 2)[1].toCharArray();
		
		List<Token> tokens = tokenize();
		tokens.forEach(System.out::println);

	}

	/**
	 * Metoda za stvaranje tokena upita.
	 * 
	 * @return lista tokena
	 */
	public List<Token> tokenize(){
		
		List<Token> tokens = new ArrayList<>();
		
		nextToken();
		tokens.add(token);

		while (token.getType() != TokenType.EOF) {
			nextToken();
			tokens.add(token);
		}
		return tokens;
	}
	
	/**
	 * Metoda za dohvat sljedećeg tokena.
	 * 
	 * @return sljedeći token 
	 */
	public Token nextToken() {

		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
			
		skipBlanks();
		
		while (currentIndex < data.length) {
			
			if (Character.isLetter(data[currentIndex])) {

				String text = extractWord();

				if (logicalOperators.contains(text.toLowerCase())) {
					token = new Token(TokenType.LOGICAL_OPERATOR, text);
					return token;
				}

				if (text.equals("LIKE")) {
					token = new Token(TokenType.OPERATOR, text);
					return token;
				}

				token = new Token(TokenType.ATTRIBUTE_NAME, text);
				return token;
			}

			// trenutni simbol je operator ili prvi dio operatora
			if (operatorStart.contains(data[currentIndex])) {

				String operator = String.valueOf(data[currentIndex]);
				currentIndex++;

				if (data[currentIndex] == '=') {
					operator = operator + data[currentIndex + 1];
					currentIndex++;
				}

				token = new Token(TokenType.OPERATOR, operator);
				return token;
			}

			// neki izraz u navodnicima
			if (data[currentIndex] == '"') {

				// pomak na mjesto poslije prvog navodnika
				currentIndex++;
				String text = extractFromQuotes();
				currentIndex++;
				
				token = new Token(TokenType.STRING_LITERAL, text);
				return token;
			}
		}
		return null;
	}

	/**
	 * Metoda za izvlačenje riječi iz niza znakova. Riječ je 
	 * neki niz znakova koji moraju biti slova.
	 * 
	 * @return string koji predstavlja izvučeni tekst
	 */
	public String extractWord() {

		int start = currentIndex;

		while (currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			currentIndex++;
		}

		return new String(data, start, currentIndex - start);
	}

	/**
	 * Metoda za izvlačenje nekog niza znakova iz navodnika.
	 * 
	 * @return niz znakova koji se nalazi u navodnicima
	 */
	public String extractFromQuotes() {

		int start = currentIndex;

		while (currentIndex < data.length && data[currentIndex] != '"') {
			currentIndex++;
		}

		return new String(data, start, currentIndex - start);

	}
	
	/**
	 * Metoda za peskakanje bilo kakvih razmaka i praznina u predanom teksu pomičući
	 * trenutnu kazaljku.
	 * 
	 */
	private void skipBlanks() {

		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (spaces.contains(c)) {
				currentIndex++;
				continue;
			}
			break;
		}
	}
}
