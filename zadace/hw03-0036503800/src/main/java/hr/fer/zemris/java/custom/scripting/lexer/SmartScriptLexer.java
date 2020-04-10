package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

/**
 * Razred koji ima ulogu leksičke analize predanog teksta. Leksičkom 
 * analizom nastaju tokeni koji se kasnije predaju parseru na obradu. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class SmartScriptLexer {

	private char[] data;
	
	private SmartScriptLexerState state;

	private int currentIndex;

	// trenutni token
	private Token token;
	// aritmetički operatori
	private static ArrayIndexedCollection operators;
	// praznine
	private static ArrayIndexedCollection spaces;
	// vrste tagova
	private static ArrayIndexedCollection tagNames;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param text dokument koji se treba leksički analizirati
	 */
	public SmartScriptLexer(String text) {

		data = text.toCharArray();
		state = SmartScriptLexerState.TEXT;
		currentIndex = 0;

		operators = new ArrayIndexedCollection();
		operators.add('+');
		operators.add('-');
		operators.add('*');
		operators.add('/');
		operators.add('^');

		spaces = new ArrayIndexedCollection();
		spaces.add(' ');
		spaces.add('\t');
		spaces.add('\n');
		spaces.add('\r');
		
		tagNames = new ArrayIndexedCollection();
		tagNames.add("FOR");
		tagNames.add("END");
		tagNames.add("=");
	}

	/**
	 * Metoda za generiranje svih tokena dokumenta.
	 * 
	 */
	public ArrayIndexedCollection tokenize() {
		
		ArrayIndexedCollection tokens = new ArrayIndexedCollection();
		
		nextToken();
		tokens.add(token);
		
		while(token.getType() != TokenType.EOF) {
			nextToken();
			tokens.add(token);
		}
		
		return tokens;
	}
	
	/**
	 * Metoda za dohvat sljedećeg tokena ako postoji.
	 * 
	 * @throws SmartScriptingLexerException ako je došlo do nekakve 
	 *         greške prilikom analize dokumenta
	 * @return sljedeći token tipa <code>Token</code>
	 */
	private Token nextToken() {

		if (token != null && token.getType() == TokenType.EOF) {
			throw new SmartScriptingLexerException("Nema više tokena.");
		}

		skipBlanks();

		if (currentIndex >= data.length) {
			token = new Token(TokenType.EOF, null);
			return token;
		}

		// početak taga
		if (data[currentIndex] == '{' && data[currentIndex + 1] == '$') {

			state = SmartScriptLexerState.TAG;

			token = new Token(TokenType.TAG_START, "{$");
			currentIndex++;
			currentIndex++;
			return token;
		}

		// tekst izvan taga
		if (state == SmartScriptLexerState.TEXT) {
			
			String text = extractText();
			token = new Token(TokenType.WORD, text);
			return token;
			
		} else {
			
			// operatori unutar taga
			if(operators.contains(data[currentIndex])) {
				
				// negativan broj
				if(data[currentIndex] == '-' && 
						Character.isDigit(data[currentIndex + 1])){
					
					String number = extractNumber();
					token = new Token(TokenType.NUMBER, number);
					return token;
				}
				// neki od operatora 
				token = new Token(TokenType.OPERATOR, data[currentIndex]);
				currentIndex++;
				return token;
			}
			
			// kraj taga
			if (data[currentIndex] == '$') {

				token = new Token(TokenType.TAG_END, "$}");
				currentIndex++;
				currentIndex++;
				state = SmartScriptLexerState.TEXT;
				return token;
			}
			
			// slova i riječi unutar taga
			if (Character.isLetter(data[currentIndex]) || 
					data[currentIndex] == '\\' || 
					data[currentIndex] == '"' ||
					data[currentIndex] == '@') {
					
				String text = extractText();
				
				if(text.equals("\"")) { // navodnici
					
					token = new Token(TokenType.QUOTEMARK, text);
					return token;
					
				}else if(text.equals("\\")){ // backslash
					
					token = new Token(TokenType.BACKSLASH, text);
					return token;
					
				}else if(text.equals("@")) { // početak funkcije
					
					token = new Token(TokenType.FUNCTION_START, text);
					return token;
					
				}else { // neka druga rječ
					
					if(tagNames.contains(text.toUpperCase())) {
						
						token = new Token(TokenType.TAG_NAME, text.toUpperCase());
						return token;
					}
					
					token = new Token(TokenType.WORD, text);
					return token;
				}
			}
			
			// slučaj kada je samo broj poslije varijable
			if (Character.isDigit(data[currentIndex])) {
				
				String number = extractNumber();
				token = new Token(TokenType.NUMBER, number);
				return token;
			}
			
			// početak taga "="
			if(data[currentIndex] == '=') {
				token = new Token(TokenType.TAG_NAME, "=");
				currentIndex++;
				return token;
			}
		}

		return null;
	}

	/**
	 * Metoda za izvlačenje teksta iz niza znakova. Kada se leksički analizator nalazi
	 * u stanju <code>TAG</code> moguće je kao povratnu vrijednost dobiti poseban znak ili
	 * neki niz znakova koji može predstavljati npr. varijablu.
	 * 
	 * U stanju <code>TEXT</code> kao povratna vrijednost se može dobiti donekle odijeljen 
	 * tekst nekom vrstom praznine.
	 * 
	 * @throws SmartScriptingLexerException ako je došlo do nekakvog niza znakova koji 
	 *         ne bi trebali biti eskejpani ili krivog zapisa stringa
	 * @return string niza znakova
	 */
	private String extractText() {

		if (state == SmartScriptLexerState.TAG) {
			
			if(Character.isLetter(data[currentIndex])) {
				
				String word = extractWord();
				return word;
			}
			
			if (data[currentIndex] == '\\') {

				currentIndex++;
				return String.valueOf("\\");
			}
			
			if(data[currentIndex] == '"') {
				
				currentIndex++;
				return String.valueOf("\"");
			}
			
			if(data[currentIndex] == '@') {
				
				currentIndex++;
				return String.valueOf("@");
			}
			
		} else {
			
			StringBuilder sb = new StringBuilder();
			
			// tekst izvan tagova se ne tokenizira, uzima se sve do prve pojave "{$"
			// i pritom se pazi da se eskejpaju samo legalni znakovi
			while (currentIndex < data.length) {

				if (data[currentIndex] == '\\') {

					if (currentIndex + 1 >= data.length)
						throw new SmartScriptingLexerException("Nije moguće završiti s \\.");

					// trenutni znak je "/" i provjera za sljedeći
					// ako je ispravna eskejp sekvenca, samo se doda
					if (data[currentIndex + 1] == '\\' || data[currentIndex + 1] == '{') {

						sb.append(data[currentIndex + 1]);
						currentIndex++;
						currentIndex++;

					} else
						throw new SmartScriptingLexerException(
								"Greška kod stvaranja tokena. " + "Nije moguće eskejpati: " + data[currentIndex + 1]);

				}
				
				// ako je u običnom tekstu naišao početak taga
				if(data[currentIndex] == '{') {
					
					if(data[currentIndex + 1] == '$') {
						
						return sb.toString();
					}
				}
				
				sb.append(data[currentIndex]);
				currentIndex++;
			}
			
			return sb.toString();
		}
		
		return "";
	}

	/**
	 * Metoda za vađenje riječi iz niza znakova. Riječ je kontinuirani niz
	 * znamenaka engleske abecede, brojeva ili podvlake(""_), a preduvjet 
	 * je da počinje slovom. Korišteno kod izvlačenja imena varijabli.
	 * 
	 * @return riječ
	 */
	private String extractWord() {
		
		int start = currentIndex;
		
		while(currentIndex < data.length && ( 
				Character.isLetter(data[currentIndex]) ||
				Character.isDigit(data[currentIndex]) ||
				data[currentIndex] == '_')) {
			
			currentIndex++;
		}
		
		return new String(data, start, currentIndex - start);
	}

	/**
	 * Metoda za vađenje broja iz niza znamenaka. Broj može bit s negativnim
	 * predznakom, sadržavati ili biti bez decimalne točke.
	 * 
	 * @return string reprezentacija broja
	 */
	private String extractNumber() {

		StringBuilder sb = new StringBuilder();

		// minus za negativne brojeve
		if (data[currentIndex] == '-') {
			sb.append("-");
			currentIndex++;
		} else if (data[currentIndex] == '+') { // plus za pozitivne
			sb.append('+');
			currentIndex++;
		}

		while (Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
		}

		// ako je decimalni broj
		if (data[currentIndex] == '.') {
			sb.append('.');
			currentIndex++;

			// ostatak decimalnog broja poslije decimalne tičke
			while (Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}

		return sb.toString();
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
