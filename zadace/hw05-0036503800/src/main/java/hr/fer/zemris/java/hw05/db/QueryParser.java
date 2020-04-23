package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

import hr.fer.zemris.java.hw05.querylexer.QueryLexer;
import hr.fer.zemris.java.hw05.querylexer.QueryLexerException;
import hr.fer.zemris.java.hw05.querylexer.Token;
import hr.fer.zemris.java.hw05.querylexer.TokenType;

/**
 * Razred za parsiranje tokena u upite.
 * 
 * @author Antonio Kuzminski
 *
 */
public class QueryParser {

	List<Token> tokens;

	/**
	 * Inicijalni konstruktor.
	 * 
	 * @throws QueryLexerException ako je došlo do greške prilikom leksičke analize
	 * @param query string upita koji je potrebno parsirati
	 */
	public QueryParser(String query) {

		QueryLexer ql;
		try {
			ql = new QueryLexer(query);
			tokens = ql.tokenize();

		} catch (QueryLexerException e) {
			throw new QueryParserException(e.getMessage());
		}
	}

	/**
	 * Metoda za provjeru je li trenutni upit "direct query". Direct query je upit
	 * tipa jmbag="xxx".
	 * 
	 * @return istina ako je trenutni upit "direct query", laž inače
	 */
	public boolean isDirectQuery() {

		// tokeni moraju biti identifikator jmbag, operator usporedbe,
		// neki jmbag i na kraju token EOF
		if (tokens.size() != 4)
			return false;

		boolean jmbag = tokens.get(0).getValue().equals("jmbag");
		boolean operator = tokens.get(1).getValue().equals("=");

		if (jmbag && operator)
			return true;

		return false;
	}

	/**
	 * Metoda za vraćanje jmbaga iz "direct query".
	 * 
	 * @throws IllegalStateException ako upit nije "direct query"
	 * @return jmgag ako je upit "direct query"
	 */
	public String getQueriedJMBAG() {

		if (!isDirectQuery())
			throw new IllegalStateException("Upit nije direct query.");

		return String.valueOf(tokens.get(2).getValue());
	}

	/**
	 * Metoda za parsiranje tokena u upite.
	 * 
	 * @throws QueryParserException ako je došlo do greške prilikom parsiranja
	 * @return lista upita tipa {@code ConditionalExpression}
	 */
	public List<ConditionalExpression> getQuerry() {

		List<ConditionalExpression> expressions = new ArrayList<>();

		if (tokens.size() < 4)
			throw new QueryParserException("Neispravan upit.");

		for (int i = 0; i < tokens.size(); i += 3) {

			if (tokens.get(i).getType() == TokenType.EOF)
				return expressions;

			// logički operator koji povezuje više izraza
			if (tokens.get(i).getType() == TokenType.LOGICAL_OPERATOR
					&& String.valueOf(tokens.get(i).getValue()).toLowerCase().equals("and")) {
				i -= 2;
				continue;
			}

			// ako je neki od sljedeća tri tokena EOF, neispravan upit
			for (int j = 0; j < 3; j++) {
				if (tokens.get(i + j).getType() == TokenType.EOF)
					throw new QueryParserException("Neispravan upit.");
			}

			try {

				String atribute = String.valueOf(tokens.get(i).getValue());

				IFieldValueGetter fieldValueGetter = getFiedlValueGetter(atribute);

				if (fieldValueGetter == null)
					throw new QueryParserException("Neispravan upit.");

				String stringLiteral = String.valueOf(tokens.get(i + 2).getValue());

				String operator = String.valueOf(tokens.get(i + 1).getValue());

				if (operator == null)
					throw new QueryParserException("Neispravan upit.");

				IComparisonOperator comparisonOperator = getComparitsonOperator(operator);

				expressions.add(new ConditionalExpression(fieldValueGetter, stringLiteral, comparisonOperator));

			} catch (IndexOutOfBoundsException e) {
				throw new QueryParserException("Neispravan upit.");
			}
		}
		return expressions;
	}

	/**
	 * Metoda za dohvar ispravnog operatora usporedbe.
	 * 
	 * @param operator string operatora koji se dohvaća
	 * @return operator usporedbe tipa {@code IComparisonOperator}
	 */
	private IComparisonOperator getComparitsonOperator(String operator) {

		if (operator.equals("<"))
			return ComparisonOperators.LESS;
		if (operator.equals(">"))
			return ComparisonOperators.GREATER;
		if (operator.equals(">="))
			return ComparisonOperators.GREATER_OR_EQUALS;
		if (operator.equals("<="))
			return ComparisonOperators.LESS_OR_EQUALS;
		if (operator.equals("="))
			return ComparisonOperators.EQUALS;
		if (operator.equals("!="))
			return ComparisonOperators.NOT_EQUALS;
		if (operator.equals("LIKE"))
			return ComparisonOperators.LIKE;
		return null;
	}

	/**
	 * Metoda za dohvat odgovarajućeg {@code FieldValueGetter} objekta na temelju
	 * teksta koji je predan kao argument.
	 * 
	 * @param atribute tekst na temelju kojeg se dohvaća ispravan
	 *                 {@code FieldValueGetter}
	 * @return {@code FieldValueGetter} objekt
	 */
	public IFieldValueGetter getFiedlValueGetter(String atribute) {

		if (atribute.equals("jmbag"))
			return FieldValueGetters.JMBAG;
		if (atribute.equals("firstName"))
			return FieldValueGetters.FIRST_NAME;
		if (atribute.equals("lastName"))
			return FieldValueGetters.LAST_NAME;
		return null;
	}

}
