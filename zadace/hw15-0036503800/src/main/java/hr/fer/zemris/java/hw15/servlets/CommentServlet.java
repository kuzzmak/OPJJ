package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.forms.BlogCommentForm;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Servlet za komentiranje neke objave.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="comment", urlPatterns={"/servleti/comment"})
public class CommentServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// postavljanje id-ja BlogEntryja koji se komentira
		req.getSession().setAttribute("blogId", req.getParameter("blogId"));
		req.getRequestDispatcher("/WEB-INF/pages/Comment.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		BlogCommentForm bcf = new BlogCommentForm();
		bcf.fromHttpRequest(req);
		
		BlogComment bc = bcf.getBlogComment();
		
		BlogEntry be = DAOProvider.getDAO().getBlogEntry(Long.valueOf((String) req.getSession().getAttribute("blogId"))); 
//		EntityManager em = JPAEMProvider.getEntityManager();
//		em.persist(bc);
		be.getComments().add(bc);
		resp.sendRedirect(req.getContextPath() + "/servleti/comments?blogId=" + req.getSession().getAttribute("blogId"));
	}
	
}
