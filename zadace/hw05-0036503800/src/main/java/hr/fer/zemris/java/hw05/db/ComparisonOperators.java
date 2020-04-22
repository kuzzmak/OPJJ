package hr.fer.zemris.java.hw05.db;

/**
 * Operatori za usporedbu dva stringa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ComparisonOperators{

	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;
	
	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;

	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;
	
	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;

	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;
	
	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;
}
