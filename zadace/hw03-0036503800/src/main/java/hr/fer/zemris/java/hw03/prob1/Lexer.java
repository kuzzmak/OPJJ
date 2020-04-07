package hr.fer.zemris.java.hw03.prob1;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;

/**
 * Razred koji predstavlja jednostavni jezični procesor. Ulaz ovog
 * podsustava je izvorni tekst programa, a izlaz je niz tokena. 
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
		if(token != null && token.getType() == TokenType.EOF) {
			throw new LexerException("Nema više tokena.");
		}
		
		skipBlanks();
		
		// slučaj kada više nema znakova
		if(currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}
		
		// ako sadrži neki od simbola
		if(symbols.contains(data[currentIndex])) {
			token = new Token(TokenType.SYMBOL, data[currentIndex]);
			currentIndex++;
			return token;
		}
		
		if(Character.isLetter(data[currentIndex])) {
			
			int start = currentIndex;
			currentIndex++;
			
			while(currentIndex < data.length && 
					Character.isLetter(data[currentIndex])) {
				currentIndex++;
			}
			
			int end = currentIndex;
			String value = new String(data, start, end - start);
			
			token = new Token(TokenType.WORD, value);
			return token;
			
		}
		
		if(Character.isDigit(data[currentIndex])) {
			double number = extractNumber();
			token = new Token(TokenType.NUMBER, number);
			return token;
		}
			
		
		
		if(data[currentIndex] == '\\') {
			
		// "\\1\\2 ab \\\\\\2c\\3\\4d"	
		// "\1\2 ab\\\2c\3\4d"
			
			currentIndex++;
			
			if(Character.isDigit(data[currentIndex])) {
				double number = extractNumber();
				System.out.println(number);
			}
			
			int start = currentIndex;
			
			while(currentIndex < data.length && data[currentIndex] == '\\') {
				currentIndex++;
			}
			
			int end = currentIndex;
			String value;
			
			if((end - start) % 2 == 0) value = new String();
			
		}
		return null;
		
	}
	
	private double extractNumber() {
		
		int start = currentIndex;
		currentIndex++;
		
		while(currentIndex < data.length &&
				Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}
		
		int end = currentIndex;
		
		String value = new String(data, start, end - start);
		
		if(start == end) throw new LexerException("Neispravan decimalni broj.");
		
		return Double.parseDouble(value);
	}
	
	public Token getToken() {
		return token;
	}
	
	/**
	 * Metoda za peskakanje bilo kakvih razmaka i praznina u predanom teksu
	 * pomičući trenutnu kazaljku.
	 * 
	 */
	private void skipBlanks() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if(c == ' ' || c == '\t' || c == '\r' || c == '\n') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
}
