package hr.fer.zemris.java.hw05.db;

/**
 * Sučelje koje modelira filter nad objektima tipa <code>StudentRecord</code>.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface IFilter {

	/**
	 * Metoda za provjeru zadovoljava li određeni zapis studenta
	 * uvjete koji su definirani u samoj metodi.
	 * 
	 * @param record zapis koji se provjerava
	 * @return istina ako zapis zadovoljava, laž inače
	 */
	public boolean accepts(StudentRecord record);
}
