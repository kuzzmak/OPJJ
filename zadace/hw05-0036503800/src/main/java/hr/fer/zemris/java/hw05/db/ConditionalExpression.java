package hr.fer.zemris.java.hw05.db;

public class ConditionalExpression {
	
	private IFieldValueGetter fieldValueGetter;
	private String stringLiteral;
	private IComparisonOperator comparisonOperator;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param vg sučelje za dohvat informacija o zapisu
	 * @param literal string koji se koristi u usporedbama
	 * @param co operator usporedbe
	 */
	public ConditionalExpression(
			IFieldValueGetter fieldValueGetter, 
			String stringLiteral, 
			IComparisonOperator comparisonOperator) {

		this.fieldValueGetter = fieldValueGetter;
		this.stringLiteral = stringLiteral;
		this.comparisonOperator = comparisonOperator;
	}

	public IFieldValueGetter getFieldValueGetter() {
		return fieldValueGetter;
	}

	public String getStringLiteral() {
		return stringLiteral;
	}

	public IComparisonOperator getComparisonOperator() {
		return comparisonOperator;
	}
}
