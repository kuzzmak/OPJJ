package hr.fer.zemris.java.hw15.dao.jpa;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Konkretna implementacija DAO.
 * 
 * @author Antonio Kuzminski
 *
 */
public class JPADAOImpl implements DAO {

	@Override
	public BlogEntry getBlogEntry(Long id) throws DAOException {
		BlogEntry blogEntry = JPAEMProvider.getEntityManager().find(BlogEntry.class, id);
		return blogEntry;
	}

	@Override
	public void registerNewUser(BlogUserForm buf) throws DAOException {
		EntityManager em = JPAEMProvider.getEntityManager();
		em.persist(buf.getBlogUser());
		em.close();
	}

}
