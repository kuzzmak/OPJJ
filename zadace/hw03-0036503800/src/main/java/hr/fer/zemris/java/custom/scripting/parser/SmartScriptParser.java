package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantDouble;
import hr.fer.zemris.java.custom.scripting.elems.ElementConstantInteger;
import hr.fer.zemris.java.custom.scripting.elems.ElementFunction;
import hr.fer.zemris.java.custom.scripting.elems.ElementOperator;
import hr.fer.zemris.java.custom.scripting.elems.ElementString;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptLexer;
import hr.fer.zemris.java.custom.scripting.lexer.SmartScriptingLexerException;
import hr.fer.zemris.java.custom.scripting.lexer.Token;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.nodes.EchoNode;
import hr.fer.zemris.java.custom.scripting.nodes.ForLoopNode;
import hr.fer.zemris.java.custom.scripting.nodes.Node;
import hr.fer.zemris.java.custom.scripting.nodes.TextNode;

public class SmartScriptParser {

	private DocumentNode documentNode;

	private ArrayIndexedCollection tokens;

	// index trenutnog tokena
	private int index = 0;

	/**
	 * Inicijalni konstruktor kojim se automatski vrši tokenizacija i parsiranje
	 * predanog teksta.
	 * 
	 * @param text dokument koji se parsira
	 */
	public SmartScriptParser(String text) {

		SmartScriptLexer lexer = new SmartScriptLexer(text);

		try {
			tokens = lexer.tokenize();
		} catch (SmartScriptingLexerException e) {
			throw new SmartScriptParserException("Greška kod leksičke analize teksta.");
		}

		documentNode = parse();
	}

	public DocumentNode getDocmentNode() {

		return documentNode;
	}

	/**
	 * Metoda za parsiranje tokena.
	 * 
	 * @throws SmartScriptParserException ako je prilikom parsiranja došlo do
	 *                                    pogreške
	 * @return objekt tipa DocumentNode koji predstavlja dokument podijeljen u manje
	 *         semantičke cjeline
	 */
	public DocumentNode parse() {

		ObjectStack nodeStack = new ObjectStack();

		DocumentNode documentNode = new DocumentNode();
		nodeStack.push(documentNode);

		Node currentNode = documentNode;

		Token token = (Token) tokens.get(index);

		while (index < tokens.size() && token.getType() != TokenType.EOF) {

			// token za početak taga
			if (token.getType() == TokenType.TAG_START) {

				if (index + 1 >= tokens.size())
					throw new SmartScriptParserException("Početak taga ne može bit na kraju dokumenta.");

				String tagName = String.valueOf(((Token) tokens.get(index + 1)).getValue());

				if (tagName.toUpperCase().equals("FOR")) {

					// pozicija FOR tokena
					index++;
					Node node = extractFOR();
					currentNode.addChild(node);
					nodeStack.push(node);
					currentNode = node;

				} else if (tagName.toUpperCase().equals("END")) {

					index++; // pomak na END token

					try {
						Node node = (Node) nodeStack.pop();

						// ako se uzme DocumentNode prije kraja parsiranje onda je greška
						if (node instanceof DocumentNode)
							throw new SmartScriptParserException("Greška prilikom parsiranja.");

					} catch (EmptyStackException e) {
						throw new SmartScriptParserException("Greška prilikom parsiranje.");
					}

					currentNode = (Node) nodeStack.peek();
					index++; // pomak ne tagEND token
					index++; // pomak na token nakon ako postoji

				} else if (tagName.equals("=")) {

					index++;
					EchoNode node = extractEquals();
					currentNode.addChild(node);

				} else
					throw new SmartScriptParserException("Neispravan tag.");

			} else { // ako nije nikakav tag onda je tekst

				TextNode textNode = new TextNode(String.valueOf(token.getValue()));
				currentNode.addChild(textNode);
				index++;
			}

			token = (Token) tokens.get(index);
		}

		return documentNode;
	}

	/**
	 * Metoda za parsiranje tokena vezanih uz tag "=".
	 * 
	 * @throws SmartScriptParserException ako je došlo prilikom parsiranja do kakve
	 *                                    greške
	 * @return objetk tipa EchoNode
	 */
	public EchoNode extractEquals() {

		index++; // token poslije "="
		Token token = (Token) tokens.get(index);

		EchoNode equals = new EchoNode();

		while (index < tokens.size() && token.getType() != TokenType.TAG_END) {

			// slučaj varijable
			if (token.getType() == TokenType.WORD) {

				ElementVariable variable = new ElementVariable(String.valueOf(token.getValue()));
				equals.addElement(variable);
				index++;

			} else if (token.getType() == TokenType.NUMBER) {

				String number = String.valueOf(token.getValue());

				if (number.contains(".")) {

					try {
						ElementConstantDouble num = new ElementConstantDouble(Double.valueOf(number));
						equals.addElement(num);
						index++;

					} catch (NumberFormatException e) {
						throw new SmartScriptParserException("Nije moguće parsirati broj.");
					}
				} else {

					try {

						ElementConstantInteger num = new ElementConstantInteger(Integer.parseInt(number));
						equals.addElement(num);
						index++;

					} catch (NumberFormatException e) {
						throw new SmartScriptParserException("Nije moguće parsirati broj.");
					}
				}

			} else if (token.getType() == TokenType.QUOTEMARK) { // neki izraz u navodnicima

				index++; // token poslije navodnika
				token = (Token) tokens.get(index);

				StringBuilder sb = new StringBuilder();

				// sve dok se ne dođe do sljedećeg navodnika koji nije eskejpan
				while (index < tokens.size() && token.getType() != TokenType.QUOTEMARK) {

					// eskejpa se neki znak, backslash ili navodnik
					if (token.getType() == TokenType.BACKSLASH) {

						index++; // pomak na taj znak
						token = (Token) tokens.get(index);
						sb.append(String.valueOf(token.getValue()));
						sb.append(" ");
						index++; // pomak na tag poslije eskejpanog znaka
					}

					// broj ili riječ, tretira se isto unutar navodnika
					if (token.getType() == TokenType.WORD || token.getType() == TokenType.NUMBER) {

						sb.append(String.valueOf(token.getValue()));
						sb.append(" ");
						index++;
					}

					token = (Token) tokens.get(index);
				}

				index++; // token poslije navodnika
				ElementString string = new ElementString(sb.toString());
				equals.addElement(string);

			} else if (token.getType() == TokenType.FUNCTION_START) { // početak funkcije

				index++; // pomak na tag neposredno nakon
				token = (Token) tokens.get(index);

				boolean ok = checkVarable(token); // ime funkcije ok?

				if (ok) {

					ElementFunction function = new ElementFunction(String.valueOf(token.getValue()));
					equals.addElement(function);
					index++;
				} else
					throw new SmartScriptParserException("Neispravno ime funkcije.");

			} else if (token.getType() == TokenType.OPERATOR) { // neki od operatora

				ElementOperator operator = new ElementOperator(String.valueOf(token.getValue()));
				equals.addElement(operator);
				index++;
			}

			token = (Token) tokens.get(index);
		}

		index++; // pomicanje na tokene poslije završnog taga ako postoji

		return equals;
	}

	/**
	 * Metoda za parsiranje tokena u ForLoopNode node.
	 * 
	 * @throws SmartScriptParserException ako je došlo do kakve greške prilikom
	 *                                    parsiranja
	 * @return objekt tipa ForLoopNode
	 */
	public ForLoopNode extractFOR() {

		// tag poslije imena FOR
		index++;
		Token token = (Token) tokens.get(index);
		// brojač parametara for petlje
		int counter = 0;

		ElementVariable variable = null;
		Element startExpression = null;
		Element endExpression = null;
		Element stepExpression = null;

		// sve do kraja taga
		while (index < tokens.size() && token.getType() != TokenType.TAG_END) {

			token = (Token) tokens.get(index);

			if (counter == 4) { // ako su svi parametri FOR popunjeni,
								// a nije token za kraj

				boolean end = token.getType() == TokenType.TAG_END;

				if (!end)
					throw new SmartScriptParserException("Previše argumenata u FOR.");
			}

			if (token.getType() == TokenType.TAG_END) { // kraj FOR
				index++; // prelazak na sljedeći tag ako postoji
				break;
			}

			// treba uzeti varijablu, prva iteracije while petlje
			if (counter == 0) {

				boolean ok = checkVarable(token);

				if (ok) {
					variable = new ElementVariable(String.valueOf(token.getValue()));
					counter++;
					index++;
				} else
					throw new SmartScriptParserException("Neispravno ime varijable.");

			} else if (counter == 1) { // popunjava se startExpression

				startExpression = extractElement();

				if (startExpression == null)
					throw new SmartScriptParserException("Greška prilikom parsiranja FOR.");

				counter++;
				continue;

			} else if (counter == 2) {

				endExpression = extractElement();

				if (endExpression == null)
					throw new SmartScriptParserException("Greška prilikom parsiranja FOR.");

				counter++;
				continue;

			} else if (counter == 3) {

				stepExpression = extractElement();
				counter++;
				continue;
			}
		}

		if (counter < 2)
			throw new SmartScriptParserException("Premalo argumenata u FOR.");

		return new ForLoopNode(variable, startExpression, endExpression, stepExpression);
	}

	/**
	 * Metoda za parsiranje tokena unutar FOR petlje.
	 * 
	 * @return objekt tipa Element koji predstavlja pojedini dio FOR petlje
	 */
	public Element extractElement() {

		Token token = (Token) tokens.get(index);

		Element expression = null;

		if (token.getType() == TokenType.WORD) {

			boolean ok = checkVarable(token);

			if (ok) {

				expression = new ElementVariable(String.valueOf(token.getValue()));
				index++;
			}

		} else if (token.getType() == TokenType.QUOTEMARK) { // konstanta kao string

			index++; // pomak do broja unutar navodnika

			token = (Token) tokens.get(index);

			expression = new ElementString((String) token.getValue());

			index++; // navodnici nakon broja
			index++; // token nakon navodnika

		} else if (token.getType() == TokenType.NUMBER) {

			String number = String.valueOf(token.getValue());

			if (number.contains(".")) { // ako je decimalni broj

				try {

					double num = Double.parseDouble(number);
					expression = new ElementConstantDouble(num);
					index++;

				} catch (NumberFormatException e) {
					throw new SmartScriptParserException("Nije moguće parsirati broj.");
				}
			} else { // integer

				try {

					int num = Integer.parseInt(number);
					expression = new ElementConstantInteger(num);
					index++;

				} catch (NumberFormatException e) {
					throw new SmartScriptParserException("Nije moguće parsirati broj.");
				}
			}
		}

		return expression;
	}

	/**
	 * Funkcija za provjeravanje je li ime varijable ispravno.
	 * 
	 * @param token token koji sadrži ime varijable
	 * @return istina ako je u redu, laž inače
	 */
	public boolean checkVarable(Token token) {

		String variable = String.valueOf(token.getValue());

		char[] symbols = variable.toCharArray();

		// ako ne počinje slovom, ne valja
		if (!Character.isLetter(symbols[0]))
			return false;

		// ostatak simbola imena ako postoje
		if (symbols.length > 1) {

			for (int i = 1; i < symbols.length; i++) {

				boolean ok = Character.isLetter(symbols[i]) || Character.isDigit(symbols[i]) || symbols[i] == '_';

				if (!ok)
					return false;
			}
		}

		return true;
	}
}
