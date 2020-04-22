package hr.fer.zemris.java.hw05.querylexer;

import hr.fer.zemris.java.hw05.querylexer.TokenType;

/**
 * Razred koji predstavlja leksičku jedinicu koja grupira jedan ili više
 * uzastopnih znakova iz ulaznog teksta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;

	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param type vrsta tokena
	 * @param value vrijednost koja se pohranjuje u token
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
		
	}
	
	public Object getValue() {
		return value;
	}
	
	public TokenType getType() {
		return type;
	}

	@Override
	public String toString() {
		return "(" + type + ", " + value + ")";
	}
}

