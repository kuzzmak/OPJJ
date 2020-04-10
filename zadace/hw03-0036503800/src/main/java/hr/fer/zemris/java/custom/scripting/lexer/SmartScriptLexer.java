package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;

public class SmartScriptLexer {

	private char[] data;
	private SmartScriptLexerState state;

	private int currentIndex;

	private ArrayIndexedCollection tokens;
	private Token token;

	private static ArrayIndexedCollection operators;
	private static ArrayIndexedCollection spaces;
	private static ArrayIndexedCollection tagNames;
	
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
		
		
		tokens = new ArrayIndexedCollection();
	}

	public void parse() {
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
		nextToken();
		System.out.println(token);
	}

	public Token nextToken() {

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
				
				if(data[currentIndex] == '-' && 
						Character.isDigit(data[currentIndex + 1])){
					
					String number = extractNumber();
					token = new Token(TokenType.NUMBER, number);
					return token;
				}
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
					
				}else if(text.equals("@")) {
					
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
			
			if(data[currentIndex] == '=') {
				token = new Token(TokenType.TAG_NAME, "=");
				currentIndex++;
				return token;
			}
		}

		return null;
	}

	public String extractText() {

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
			
			while (currentIndex < data.length && !spaces.contains(data[currentIndex])) {

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
				
				

				sb.append(data[currentIndex]);
				currentIndex++;
			}
			
			return sb.toString();
		}
		
		return "";
	}

	public String extractWord() {
		
		int start = currentIndex;
		
		while(currentIndex < data.length && Character.isLetter(data[currentIndex])) {
			currentIndex++;
		}
		
		return new String(data, start, currentIndex - start);
	}
	
	public void extractTag() {
		// u metodu se ulazi kada je pročitana prva lijeva vitičasta zagrada
		// ako poslije toga ne slijedi $ izaziva se iznimka
		skipBlanks();

		// ako dokument završava s {
		if (currentIndex + 1 >= data.length)
			throw new SmartScriptingLexerException("Greška prilikom parsiranja.");

		if (data[currentIndex + 1] != '$')
			throw new SmartScriptingLexerException(
					"Greška prilikom parsiranje taga. index: " + String.valueOf(currentIndex + 1));

		// pozicija s znakom $
		currentIndex++;

		currentIndex++;

		token = new Token(TokenType.TAG_START, "{$");
		tokens.add(token);

		// ako ima praznina između $ i "=" ili "FOR"
		skipBlanks();

		if (data[currentIndex] == '=') { // tag "="

		} else if (Character.isLetter(data[currentIndex])) { // ako je slovo koje počinje na "E" ili "F"

			String tagName = extractTagName();

			token = new Token(TokenType.TAG_NAME, tagName);
			tokens.add(token);

			// ulazi se metodu odmah znak nakon imena taga
			Node tag = extractTag(tagName);

			System.out.println();

		} else
			throw new SmartScriptParserException("Neispravan tag.");
	}

	/**
	 * Funkcija koja slži za dobivanje imena taga kojeg treba generirati.
	 * 
	 * @return ime taga za generiranje
	 */
	public String extractTagName() {

		int start = currentIndex;

		while (!spaces.contains(data[currentIndex])) {
			currentIndex++;
		}

		int end = currentIndex;

		return new String(data, start, end - start);
	}

	public Node extractTag(String tagName) {

		if (tagName.equals("FOR")) { // FOR tag

			ForLoopNode fln = extractFOR();

			return fln;
		} else { // END tag

			Node END = extractEND();

			return END;
		}
	}

	public ForLoopNode extractFOR() {

		Element variable = extractVariableName();

		Element startExpression = extractExpression();

		Element endExpression = extractExpression();

		Element stepExpression = extractExpression();

		skipBlanks();

		if (data[currentIndex] != '$')
			throw new SmartScriptParserException("Neispravna FOR sintaksa.");

		ForLoopNode fln = new ForLoopNode((ElementVariable) variable, startExpression, endExpression, stepExpression);

		return fln;
	}

	public Node extractEND() {

		return null;
	}

	public Element extractVariableName() {

		skipBlanks();

		int start = currentIndex;

		if (!Character.isLetter(data[currentIndex]))
			throw new SmartScriptParserException(
					"Neispravan početak imena varijable. " + "\"" + String.valueOf(data[currentIndex]) + "\"");

		// dodavanje prvog slova
		currentIndex++;
		// ostatak imena varijable ako ga ima, dopušteno slovo, broj ili "_"
		while (Character.isLetter(data[currentIndex]) || Character.isDigit(data[currentIndex])
				|| data[currentIndex] == '_') {

			currentIndex++;
		}

		int end = currentIndex;

		return new ElementVariable(new String(data, start, end - start));
	}

	public Element extractExpression() {

		skipBlanks();

		// ako nema step dijela u FOR petlji
		if (data[currentIndex] == '$')
			return null;

		if (data[currentIndex] == '@')
			throw new SmartScriptParserException("Neispravna sintaksa.");

		// slučaj kada je samo broj poslije varijable
		// slučaj kada je minus i odmah nakon neka znamenka
		// slučaj kada je + i odmah nakon neka znamenka
		if (Character.isDigit(data[currentIndex])
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))
				|| (data[currentIndex] == '-' && Character.isDigit(data[currentIndex + 1]))) {

			String number = extractNumber();

			if (number.contains(".")) {
				return new ElementConstantDouble(Double.parseDouble(number));
			}

			return new ElementConstantInteger(Integer.parseInt(number));

		} else if (Character.isLetter(data[currentIndex])) {

			// ako je trenutni znak slovo, vrijede ista pravila
			// kao i kod imena varijable
			return extractVariableName();

		} else if (data[currentIndex] == '"') {

			currentIndex++;
			String content = extractFromQuoteMarks();
			currentIndex++; // micanje na znak poslije "
			return new ElementString(content);
		}

		throw new SmartScriptParserException("Neispravna sintaksa.");
	}

	public String extractNumber() {

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

	public String extractFromQuoteMarks() {

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
