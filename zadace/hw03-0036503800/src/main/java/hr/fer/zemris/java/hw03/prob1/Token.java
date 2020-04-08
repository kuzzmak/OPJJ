package hr.fer.zemris.java.hw03.prob1;

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
