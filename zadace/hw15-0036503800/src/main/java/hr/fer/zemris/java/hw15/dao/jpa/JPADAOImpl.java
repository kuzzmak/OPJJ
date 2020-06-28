package hr.fer.zemris.java.hw15.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

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
		
		BlogUser bu = findUserByUsername(buf.getUsername());
		
		if(bu != null) throw new DAOException("Korisnik već postoji s korisničkim imenom: " + buf.getUsername());
		else em.persist(buf.getBlogUser());
	}

	@Override
	public BlogUser findUserByUsername(String username) throws DAOException {

		EntityManager em = JPAEMProvider.getEntityManager();
		
		@SuppressWarnings("unchecked")
		List<BlogUser> user = (List<BlogUser>) em
			.createQuery("SELECT bu FROM BlogUser AS bu WHERE bu.nick=:username")
			.setParameter("username", username)
			.getResultList();
		
		if(user.size() != 1) return null;
		else return user.get(0);
	}

	@Override
	public List<BlogUser> getAllUsers() throws DAOException {

		EntityManager em = JPAEMProvider.getEntityManager();
	
		@SuppressWarnings("unchecked")
		List<BlogUser> users = (List<BlogUser>) em
				.createQuery("SELECT bu FROM BlogUser bu")
				.getResultList();
		
		return users;
	}

	@Override
	public List<BlogEntry> getUserEntries(String nick) throws DAOException {
		
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogUser bu = findUserByUsername(nick);
		
		@SuppressWarnings("unchecked")
		List<BlogEntry> userEntries = (List<BlogEntry>) em
				.createQuery("SELECT be FROM BlogEntry AS be WHERE be.creator=:creator")
				.setParameter("creator", bu)
				.getResultList();
		
		return userEntries;
	}

	@Override
	public List<BlogComment> getBlogEntryComments(Long blogId) throws DAOException {
		
//		EntityManager em = JPAEMProvider.getEntityManager();
//		
//		@SuppressWarnings("unchecked")
//		List<BlogComment> blogEntryComments = (List<BlogComment>) em
//				.createQuery("SELECT bc FROM BlogComment AS bc WHERE bc.blogEntry.id=:blogId")
//				.setParameter("blogId", blogId)
//				.getResultList();
		
//		return blogEntryComments;
		
		return getBlogEntry(blogId).getComments();
	}
	
}
