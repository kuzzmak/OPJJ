package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje modelira neki od operatora usporedbe. 
 * 
 * @author Antonio Kuzminski
 *
 */
public interface IComparisonOperator {

	/**
	 * Metoda koja se poziva nad operatorom usporedbe gdje se provjerava uvjet zadovoljivosti.
	 * 
	 * @param value1 prva vrijednost usporedbe
	 * @param value2 druga vrijednost usporedbe
	 * @return istina ako vrijednosti zadovoljavaju, laž inače
	 */
	public boolean satisfied(String value1, String value2);
}
