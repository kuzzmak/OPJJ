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
		
		if(text == null) throw new NullPointerException("Tekst ne može biti null.");
		
		this.currentIndex = 0;

		this.data = text.toCharArray();

		symbols = new ArrayIndexedCollection();
		symbols.add('.');
		symbols.add('-');
		symbols.add(',');
		symbols.add('?');
		symbols.add('#');
		symbols.add('!');
		symbols.add(';');
	}

	/**
	 * Metoda za dohvat sljedećeg tokena ako postoji.
	 * 
	 * @throws LexerException ako je došlo do kakve greške prilikom parsiranja
	 * @return sljedeći token 
	 */
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
			
			long number = extractNumber(false);
			
			token = new Token(TokenType.NUMBER, number);
			
			return token;
		}

		if (data[currentIndex] == '\\') {

			currentIndex++;

			String result = extractEscaped();
			
			if(currentIndex +  1 < data.length) {
				
				String result2 = word();
				result = result + result2;
			}
			
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
				
				// ako je poslije slova escaped znamenka
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
		
		if(currentIndex >= data.length) 
			throw new LexerException("Program ne može završavati s \"\\\".");
		
		if(Character.isLetter(data[currentIndex])) 
			throw new LexerException("Nije moguće predati \"\\\" ispred slova.");
		
		StringBuilder sb = new StringBuilder();
		
		// ako je u prethodnom koraku bio "/"
		boolean oneSlash = false;

		while (Character.isDigit(data[currentIndex]) || data[currentIndex] == '\\') {

			// prvi puta se može izvesti tek nakon druge iteracije, ako je npr. \1\2
			if(data[currentIndex] == '\\') {
				
				// ako je stigao drugi / poslije broja, onda se broji kao znak "/"
				if(oneSlash) {
					currentIndex--;
					return sb.toString();
				}
				
				currentIndex++;
				oneSlash = true;
			
			}else {
				// ako nije "/", onda je broj
				long number = extractNumber(true);
				oneSlash = false;
				sb.append(number);
				return sb.toString();
			}
			
			if(currentIndex >= data.length) return sb.toString();
		}
		
		// ako se prvo pojavi npr. /1/2 i zatim poslije toga tekst
		if(Character.isLetter(data[currentIndex])) sb.append(word());
		
		return sb.toString();
	}

	/**
	 * Metoda za parsiranje broja.
	 * 
	 * @param escaped ako se metoda koristi za escaped znamenke 
	 * @throws LexerException ako je neispravno zapisan decimalni broj u tekstu
	 * @return long reprezentacija parsiranog broja
	 */
	private long extractNumber(boolean escaped) {
		
		// ako je znamenka escaped onda se samo doda trenutni broj i vrati
		if(escaped) {
			String num = new String(data, currentIndex, 1);
			long number = Long.valueOf(num);
			currentIndex++;
			return number;
		}

		// slučaj kada je normalan broj
		int start = currentIndex;
		currentIndex++;

		while (currentIndex < data.length && Character.isDigit(data[currentIndex])) {
			currentIndex++;
		}

		int end = currentIndex;

		String value = new String(data, start, end - start);
		
		if (start == end)
			throw new LexerException("Neispravan decimalni broj.");

		try {
			return Long.valueOf(value);
		}catch(NumberFormatException e) {
			throw new LexerException("Broj prevelik, nemoguće parsirati u double.");
		}
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
