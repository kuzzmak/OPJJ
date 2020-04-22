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
	
	public static final IComparisonOperator LIKE = new IComparisonOperator() {
		
		@Override
		public boolean satisfied(String value1, String value2) {
			
			for(int i = 0, j = 0; i < value2.length() && j < value2.length();) {
				
				if(i > value1.length() - 1) return false; // kada je prvi string kraći od drugog
				
				if(value2.charAt(j) == '*') {
					j++;
					continue;
				}
				
				if(value2.charAt(j) != value1.charAt(i)) 
					return false;
				i++;
				j++;
			}
			return true;
		}
	};
}
