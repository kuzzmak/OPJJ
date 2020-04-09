package hr.fer.zemris.java.custom.scripting.lexer;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

public class SmartScriptLexer {

	private char[] data;
	private SmartScriptLexerState state;
	
	private int currentIndex;
	
	private static ArrayIndexedCollection operators;
	private static ArrayIndexedCollection spaces;
	
	public SmartScriptLexer(String text) {
		
		data = text.toCharArray();
		state = SmartScriptLexerState.BASIC;
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
	}
	
	public void parse() {
		nextToken();
		nextToken();
	}
	
	public void nextToken() {
		
		skipBlanks();
		
		if(data[currentIndex] == '{') { // početak taga
			
			Node node = extractNode();
			
			
			
		}else { // tekst
			
			
				
			TextNode textNode = extractTextNode();
			System.out.println(textNode);
				
			
		}
	}
	
	public TextNode extractTextNode() {
		
		StringBuilder sb = new StringBuilder();
		
		while(currentIndex < data.length && data[currentIndex] != '{') {
			
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		return new TextNode(sb.toString());
	}
	
	public Node extractNode() {
		// u metodu se ulazi kada je pročitana prva lijeva vitičasta zagrada
		// ako poslije toga ne slijedi $ izaziva se iznimka
		skipBlanks();
		
		// ako dokument završava s {
		if(currentIndex + 1 >= data.length)
			throw new SmartScriptParserException("Greška prilikom parsiranja.");
		
		if(data[currentIndex + 1] != '$') 
			throw new SmartScriptParserException("Greška prilikom parsiranje taga. index: " + String.valueOf(currentIndex + 1)); 
		
		// pozicija s znakom $
		currentIndex++;
		
		currentIndex++;
		
		// ako ima praznina između $ i "=" ili "FOR"
		skipBlanks();
		
		if(data[currentIndex] == '=') { // tag "="
			
		}else if(Character.isLetter(data[currentIndex])) { // ako je slovo koje počinje na "E" ili "F"
			
			String tagName = extractTagName();
			
			// ulazi se  metodu odmah znak nakon imena taga
			Node tag = extractTag(tagName);
			
			
			
		}else throw new SmartScriptParserException("Neispravan tag.");
		
		return null;
	}
	
	/**
	 * Funkcija koja slži za dobivanje imena taga kojeg treba generirati.
	 * 
	 * @return ime taga za generiranje
	 */
	public String extractTagName() {
		
		int start = currentIndex;
		
		while(!spaces.contains(data[currentIndex])) {
			currentIndex++;
		}
		
		int end = currentIndex;
		
		return new String(data, start, end - start);
	}
	
	public Node extractTag(String tagName) {
		
		if(tagName.equals("FOR")) { // FOR tag
			
			Node FOR = extractFOR();
			
			return FOR;
		}else { // END tag
			
			Node END = extractEND();
			
			return END;
		}
	}
	
	public Node extractFOR() {
		
		Element variable = extractVariableName();
		
		Element startExpression = extractExpression();
		
		Element endExpression = extractExpression();
		
		Element stepExpression = extractExpression();
		
		skipBlanks();
		
		if(data[currentIndex] != '$') 
			throw new SmartScriptParserException("Neispravna FOR sintaksa.");
			
		Node FOR = new ForLoopNode(
				(ElementVariable)variable, 
				startExpression, 
				endExpression, 
				stepExpression);
		
		return FOR;
	}
	
	public Node extractEND() {
		
		
		return null;
	}
	
	public Element extractVariableName() {
		
		skipBlanks();
		
		int start = currentIndex;
		
		if(!Character.isLetter(data[currentIndex]))
			throw new SmartScriptParserException("Neispravan početak imena varijable. " 
		+ "\"" + String.valueOf(data[currentIndex]) + "\"");
		
		// dodavanje prvog slova
		currentIndex++;
		// ostatak imena varijable ako ga ima, dopušteno slovo, broj ili "_"
		while(Character.isLetter(data[currentIndex]) || 
				Character.isDigit(data[currentIndex]) || 
				data[currentIndex] == '_') {
			
			currentIndex++;
		}
		
		int end = currentIndex;
		
		return new ElementVariable(new String(data, start, end - start));
	}
	
	public Element extractExpression() {
		
		skipBlanks();
		
		if(data[currentIndex] == '@') 
			throw new SmartScriptParserException("Neispravna sintaksa.");
		
		
		// slučaj kada je samo broj poslije varijable
		// slučaj kada je minus i odmah nakon neka znamenka
		// slučaj kada je + i odmah nakon neka znamenka
		if(Character.isDigit(data[currentIndex]) ||
				(data[currentIndex] == '-'&& Character.isDigit(data[currentIndex + 1])) ||
				(data[currentIndex] == '-'&& Character.isDigit(data[currentIndex + 1]))) {
			
			String number = extractNumber();
			
			if(number.contains(".")) {
				return new ElementConstantDouble(Double.parseDouble(number));
			}
			
			return new ElementConstantInteger(Integer.parseInt(number));
			
		}else if(Character.isLetter(data[currentIndex])){
			
			// ako je trenutni znak slovo, vrijede ista pravila 
			// kao i kod imena varijable
			return extractVariableName();
			
		}else if(data[currentIndex] == '"') {
			
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
		if(data[currentIndex] == '-') {
			sb.append("-");
			currentIndex++;
		}else if(data[currentIndex] == '+') { // plus za pozitivne
			sb.append('+');
			currentIndex++;
		}
		
		while(Character.isDigit(data[currentIndex])) {
			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		// ako je decimalni broj
		if(data[currentIndex] == '.') {
			sb.append('.');
			currentIndex++;
			
			// ostatak decimalnog broja poslije decimalne tičke
			while(Character.isDigit(data[currentIndex])) {
				sb.append(data[currentIndex]);
				currentIndex++;
			}
		}
		
		return sb.toString();
	}
	
	public String extractFromQuoteMarks() {
		
		int start = currentIndex;
		
		while(currentIndex < data.length && data[currentIndex] != '"') {
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
