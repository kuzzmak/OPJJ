package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.DAOProvider;

/**
 * Servlet za glasanje.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje", urlPatterns = {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// dohvat identifikacijskog broja unosa pojedine ankete
		Long pollID = Long.valueOf(String.valueOf(req.getParameter("pollID")));
		// dohvat ankete
		req.setAttribute("poll", DAOProvider.getDao().getPollList().stream().filter(p -> p.getId() == pollID).findFirst().get());
		req.setAttribute("entries", DAOProvider.getDao().getPollEntryList(pollID));
		req.getSession().setAttribute("pollID", pollID);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp); 
	}

}
