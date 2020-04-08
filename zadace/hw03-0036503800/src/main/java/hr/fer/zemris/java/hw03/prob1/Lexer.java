package hr.fer.zemris.java.hw03.prob1;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Razred koji predstavlja jednostavni jezični procesor. Ulaz ovog podsustava je
 * izvorni tekst programa, a izlaz je niz tokena.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Lexer {

	// ulazni tekst
	private char[] data;
	// trenutni token
	private Token token;
	// indeks prvog neobrađenog znaka
	private int currentIndex;

	private ArrayIndexedCollection symbols;

	public Lexer(String text) {
		this.currentIndex = 0;

		this.data = text.toCharArray();

		symbols = new ArrayIndexedCollection();
		symbols.add('.');
		symbols.add(',');
		symbols.add(':');
		symbols.add('_');
	}

	public Token nextToken() {

		// dohvat tokena, ako je već dohvaćen zadnji, izaziva iznimku
		if (token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Nema više tokena.");
		}

		skipBlanks();

		// slučaj kada više nema znakova
		if (currentIndex >= data.length) {
			
			token = new Token(TokenType.EOF, null);
			
			return token;
		}

		// ako sadrži neki od simbola
		if (symbols.contains(data[currentIndex])) {
			
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			
			return token;
		}

		if (Character.isLetter(data[currentIndex])) {

			String value = word();

			token = new Token(TokenType.WORD, value);
			return token;
		}

		if (Character.isDigit(data[currentIndex])) {
			
			double number = extractNumber();
			token = new Token(TokenType.NUMBER, number);
			
			return token;
		}

		if (data[currentIndex] == '\\') {

			// "\\1\\2 ab \\\\\\2c\\3\\4d"
			// "\1\2 ab\\\2c\3\4d"

			currentIndex++;

			String result = extractEscaped();
			token = new Token(TokenType.WORD, result);
			return token;

		}
		return null;
	}
	
	/**
	 * Metoda za parsiranje jedne riječi u tekstu. Riječ može sadržavati i escaped znakove.
	 * 
	 * @return string reprezentacija parsirane riječi
	 */
	public String word() {
		
		StringBuilder sb = new StringBuilder();
		
		while (currentIndex < data.length && 
				(Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\')) {
			
			// normalno slovo
			if(Character.isLetter(data[currentIndex])) {
				
				sb.append(data[currentIndex]);
				currentIndex++;
				continue;
			}
			
			// znamenke koje su escaped
			if(data[currentIndex] == '\\') {
				
				// ako se \ nalazi na kraju teksta, a nije escaped
				if(currentIndex + 1 >= data.length) throw new LexerException("Program ne može završavati s \"\\\"");
				
				currentIndex++;
				
				if(Character.isDigit(data[currentIndex])) {
					
					String value = extractEscaped();
					sb.append(value);
					continue;
				}
				
				// ako je \ escaped
				if(data[currentIndex] == '\\') {
					
					sb.append("\\");
					currentIndex++;
					
				}else throw new LexerException("Ispred slova ne može stajati \"\\\".");
				
			}
			
			if(currentIndex >= data.length) return sb.toString();
		}

		return sb.toString();
	}

	/**
	 * Metoda za parsiranje "escaped" znamenaka.
	 * 
	 * @return string reprezentacija escaped znamenaka
	 */
	public String extractEscaped() {
		
		StringBuilder sb = new StringBuilder();

		while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\') {

			// prvi puta se može izvesti tek nakon druge iteracije, ako je npr. \1\2
			if(data[currentIndex] == '\\') {
				currentIndex++;
			}else {
				
				double number = extractNumber();
				// ako je broj integer znači da može biti više znamenaka escapeano
				if(number % 1 == 0) {
					sb.append((int)number);
				}else { // decimalni broj, kada se ekstraktira nema više znamenaka
					sb.append(number);
					break;
				}
			}
		}
		
		return sb.toString();
	}

	/**
	 * Metoda za parsiranje broja.
	 * 
	 * @throws LexerException ako je neispravno zapisan decimalni broj u tekstu
	 * @return double reprezentacija parsiranog broja
	 */
	private double extractNumber() {

		int start = currentIndex;
		currentIndex++;

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}

		int end = currentIndex;

		String value = new String(data, start, end - start);

		if (start == end)
			throw new LexerException("Neispravan decimalni broj.");

		return Double.parseDouble(value);
	}

	public Token getToken() {
		return token;
	}

	/**
	 * Metoda za peskakanje bilo kakvih razmaka i praznina u predanom teksu pomičući
	 * trenutnu kazaljku.
	 * 
	 */
	private void skipBlanks() {
		while (currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
}
