package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.DAOProvider;
import hr.fer.zemris.java.hw14.Poll;

/**
 * Servlet za početnu stranicu.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="index1", urlPatterns={"/servleti/index.html"})
public class IndexServlet1 extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		List<Poll> polls = DAOProvider.getDao().getPollList();
		
		req.setAttribute("polls", polls);
		
		req.getRequestDispatcher("/WEB-INF/pages/index.jsp").forward(req, resp);
	}
}
