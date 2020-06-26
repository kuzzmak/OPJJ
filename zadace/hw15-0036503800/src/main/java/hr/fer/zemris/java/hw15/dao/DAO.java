package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;
	
	/**
	 * Registracija novog korisnika u bazu podataka.
	 * 
	 * @param buf forma korisnika
	 * @throws DAOException uslijed nastale greške
	 */
	public void registerNewUser(BlogUserForm buf) throws DAOException;
	
}