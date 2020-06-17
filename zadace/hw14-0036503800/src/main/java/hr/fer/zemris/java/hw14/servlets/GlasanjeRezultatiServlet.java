package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.DAOProvider;
import hr.fer.zemris.java.hw14.PollEntry;

/**
 * Servlet za prikaz stranice rezultata glasanja.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-rezultati", urlPatterns = {"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long pollID = Long.parseLong(String.valueOf(req.getSession().getAttribute("pollID")));
		
		// unosi pojedine ankete
		List<PollEntry> entries = DAOProvider.getDao().getPollEntryList(pollID);
		// sortirani po broju glasova
		entries = entries.stream().sorted().collect(Collectors.toList());
		
		req.setAttribute("pollResults", entries);
		
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp); 
	}
	
}
