package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje za dohvat pojedinih podataka studentskog zapisa.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface IFieldValueGetter {

	/**
	 * Metoda za dohvat nekog podatka studenta.
	 * 
	 * @param record zapis iz kojeg se dohvaća podatak
	 * @return podatak koji se želi dohvatiti
	 */
	public String get(StudentRecord record);
}
