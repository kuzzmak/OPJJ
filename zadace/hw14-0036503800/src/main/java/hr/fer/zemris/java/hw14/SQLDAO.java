package hr.fer.zemris.java.hw14;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter 
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *  
 * @author marcupic
 */
public class SQLDAO implements DAO {

	@Override
	public List<PollEntry> getPollEntryList(Long pollID) throws DAOException {
		
		List<PollEntry> entries = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, pollId, votesCount FROM PollOptions WHERE pollID=? ORDER BY id");
			pst.setLong(1, pollID);
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						
						PollEntry entry = new PollEntry();
						
						entry.setId(rs.getLong(1));
						entry.setTitle(rs.getString(2));
						entry.setLink(rs.getString(3));
						entry.setPollId(rs.getLong(4));
						entry.setVotesCount(rs.getLong(5));
						
						entries.add(entry);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste bendova za glasanje.", ex);
		}
		
		return entries;
	}

	@Override
	public List<Poll> getPollList() throws DAOException {
		
		List<Poll> polls = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, title, message FROM Polls ORDER BY id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs != null && rs.next()) {
						
						Poll poll = new Poll();
						
						poll.setId(rs.getLong(1));
						poll.setTitle(rs.getString(2));
						poll.setMessage(rs.getString(3));
						
						polls.add(poll);
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata liste mogućih anketi za glasanje.", ex);
		}
		
		return polls;
	}

	@Override
	public void voteFor(PollEntry entry) throws DAOException {
		
		try {
			
			Connection con = SQLConnectionProvider.getConnection();
			PreparedStatement pst = con.prepareStatement("SELECT votesCount FROM PollOptions WHERE id=?");
			pst.setLong(1, entry.getId());
			
			try{
				ResultSet rs = pst.executeQuery();
				try {
					if(rs != null && rs.next()) {
						
						pst = con.prepareStatement("UPDATE PollOptions SET votesCount=? WHERE id=?");
						pst.setLong(1, rs.getLong(1) + 1);
						pst.setLong(2, entry.getId());
						pst.execute();
						
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
				
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
			
		}catch(Exception e) {
			throw new DAOException("Pogreška prilikom glasanja.", e);
		}
	}

	@Override
	public PollEntry getPollEntryById(Long pollID, Long entryID) throws DAOException {
		return getPollEntryList(pollID).stream().filter(p -> p.getId() == entryID).findFirst().get();
	}

}
