package hr.fer.zemris.java.hw05.db;

/**
 * Operatori za usporedbu dva stringa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ComparisonOperators {

	public static final IComparisonOperator LESS = (s1, s2) -> s1.compareTo(s2) < 0;

	public static final IComparisonOperator LESS_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) <= 0;

	public static final IComparisonOperator GREATER = (s1, s2) -> s1.compareTo(s2) > 0;

	public static final IComparisonOperator GREATER_OR_EQUALS = (s1, s2) -> s1.compareTo(s2) >= 0;

	public static final IComparisonOperator EQUALS = (s1, s2) -> s1.compareTo(s2) == 0;

	public static final IComparisonOperator NOT_EQUALS = (s1, s2) -> s1.compareTo(s2) != 0;

	public static final IComparisonOperator LIKE = new IComparisonOperator() {

		@Override
		public boolean satisfied(String value1, String value2) {

			// ako je jedno jednako drugome ili je uzorak podriječ prve riječi
			if(value1.contains(value2)) return true;
			
			if (value2.length() - value1.length() >= 2)
				return false;

			int indexOfAsterisk = -1;

			for (int i = 0; i < value2.length(); i++) {

				if (value2.charAt(i) == '*') {
					indexOfAsterisk = i;
					break;
				}

				if (value2.charAt(i) != value1.charAt(i))
					return false;
			}

			String rest = new String(value1.toCharArray(), indexOfAsterisk, value1.length() - indexOfAsterisk);

			for (int i = 0; i < rest.length(); i++) {

				if (value2.charAt(value2.length() - 1 - i) == '*')
					break;

				if (value2.charAt(value2.length() - 1 - i) != value1.charAt(value1.length() - 1 - i))
					return false;

			}
			return true;
		}
	};
}
