package hr.fer.zemris.java.hw14;
import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface DAO {

	/**
	 * Dohvaća sve postojeće unose u bazi za glasanje.
	 * 
	 * @return listu unosa za glasanje
	 * @throws DAOException u slučaju pogreške
	 */
	public List<PollEntry> getPollEntryList() throws DAOException;
	
	/**
	 * Dohvaća Unos za zadani id. Ako unos ne postoji, vraća <code>null</code>.
	 * @param id
	 * @return
	 * @throws DAOException
	 */
	public Unos dohvatiUnos(long id) throws DAOException;
	
}
