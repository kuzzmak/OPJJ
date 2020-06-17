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
	 * Dohvaća listu mogućih anketa za glasanje.
	 * 
	 * @return lista anketa za glasanje
	 * @throws DAOException ukoliko je došlo do greške
	 */
	public List<Poll> getPollList() throws DAOException;

	/**
	 * Dohvaća sve postojeće unose u bazi za glasanje.
	 * 
	 * @param pollID identifikacijski broj ankete
	 * @return listu unosa za glasanje
	 * @throws DAOException u slučaju pogreške
	 */
	public List<PollEntry> getPollEntryList(Long pollID) throws DAOException;
	
	/**
	 * Dohvat određenog unosa u anketi za glasanje.
	 * 
	 * @param pollID identifikacijski broj ankete
	 * @param entryID identifikacijski broj unosa
	 * @return objekt tipa {@code PollEntry}
	 * @throws DAOException ukoliko je došlo do greške
	 */
	public PollEntry getPollEntryById(Long pollID, Long entryID) throws DAOException;
	
	/**
	 * Metoda za glasanje na određenoj anketi.
	 * 
	 * @param entryID identifikacijski broj unosa za koji se glasalo
	 * @throws DAOException ukoliko je došlo do greške
	 */
	public void voteFor(PollEntry entry) throws DAOException;
	
}
