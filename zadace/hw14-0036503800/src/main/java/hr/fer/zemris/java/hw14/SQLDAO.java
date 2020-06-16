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
	public List<PollEntry> getPollEntryList() throws DAOException {
		
		List<PollEntry> entries = new ArrayList<>();
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		
		try {
			pst = con.prepareStatement("SELECT id, optionTitle, optionLink, pollId FROM PollOptions ORDER BY id");
			try {
				ResultSet rs = pst.executeQuery();
				try {
					while(rs!=null && rs.next()) {
						PollEntry entry = new PollEntry();
						entry.setId(rs.getLong(1));
						entry.setTitle(rs.getString(2));
						entry.setLink(rs.getString(3));
						entry.setPollId(rs.getLong(4));
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
	public Unos dohvatiUnos(long id) throws DAOException {
		Unos unos = null;
		Connection con = SQLConnectionProvider.getConnection();
		PreparedStatement pst = null;
		try {
			pst = con.prepareStatement("select id, title, message, createdOn, userEMail from Poruke where id=?");
			pst.setLong(1, Long.valueOf(id));
			try {
				ResultSet rs = pst.executeQuery();
				try {
					if(rs!=null && rs.next()) {
						unos = new Unos();
						unos.setId(rs.getLong(1));
						unos.setTitle(rs.getString(2));
						unos.setMessage(rs.getString(3));
						unos.setCreatedOn(rs.getTimestamp(4));
						unos.setUserEMail(rs.getString(5));
					}
				} finally {
					try { rs.close(); } catch(Exception ignorable) {}
				}
			} finally {
				try { pst.close(); } catch(Exception ignorable) {}
			}
		} catch(Exception ex) {
			throw new DAOException("Pogreška prilikom dohvata korisnika.", ex);
		}
		return unos;
	}

}
