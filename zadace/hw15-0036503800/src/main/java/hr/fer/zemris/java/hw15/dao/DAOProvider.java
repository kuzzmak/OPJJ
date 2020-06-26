package hr.fer.zemris.java.hw15.dao;

import hr.fer.zemris.java.hw15.dao.jpa.JPADAOImpl;

/**
 * Razred za dohvat neke implementacije objekta tipa {@code DAO}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class DAOProvider {

	private static DAO dao = new JPADAOImpl();
	
	public static DAO getDAO() {
		return dao;
	}
	
}
