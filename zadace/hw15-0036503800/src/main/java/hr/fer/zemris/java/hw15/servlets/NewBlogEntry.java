package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.forms.BlogEntryForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Servlet za stvaranje nove objave.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="newBlogEntry", urlPatterns={"/servleti/newBlogEntry"})
public class NewBlogEntry extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/NewBlogEntry.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		EntityManager em = JPAEMProvider.getEntityManager();
		
		BlogEntryForm bef = new BlogEntryForm();
		bef.fromHttpRequest(req);
		
		BlogEntry be = bef.getBlogEntry();
		
		em.persist(be);
		
		resp.sendRedirect(req.getContextPath() + "/servleti/author?nick=" + req.getSession().getAttribute("current.user.nick"));
	}
	
}
