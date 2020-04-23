package hr.fer.zemris.java.hw05.db;

import java.util.List;

/**
 * Razred koji predstavlja implementaciju filtera zapisa u bazi.
 * 
 * @author Antonio Kuzminski
 *
 */
public class QueryFilter implements IFilter{

	List<ConditionalExpression> expressions;
	
	public QueryFilter(List<ConditionalExpression> expressions) {
		this.expressions = expressions;
	}
	
	@Override
	public boolean accepts(StudentRecord record) {
		
		for(ConditionalExpression ce: expressions) {
			
			boolean recordSatisfies = ce.getComparisonOperator() // operator usporedbe
					.satisfied(
							ce.getFieldValueGetter().get(record), // atribut
							ce.getStringLiteral()); // string koji se uspoređuje
			
			if(!recordSatisfies) return false;
		}
		return true;
	}
}
