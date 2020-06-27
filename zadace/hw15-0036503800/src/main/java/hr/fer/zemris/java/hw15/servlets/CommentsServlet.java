package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;

/**
 * Servlet koji dohvaća komentare objave te ih sprema u kontekst sjednice.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="comments", urlPatterns={"/servleti/comments"})
public class CommentsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long blogId = Long.valueOf(req.getParameter("blogId"));
		List<BlogComment> blogEntryComments = DAOProvider.getDAO().getBlogEntryComments(blogId);
		
		req.getSession().setAttribute("blogEntryComments", blogEntryComments);
		req.getRequestDispatcher("/WEB-INF/pages/Comments.jsp").forward(req, resp);
	}
	
}
