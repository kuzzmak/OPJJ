package hr.fer.zemris.java.hw14.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw14.DAOProvider;

/**
 * Servlet za glasanje najboljeg benda.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-glasaj", urlPatterns = {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		// identifikacijski broj pojedinog unosa
		Long entryID = Long.parseLong(req.getParameter("entryID"));
		
		DAOProvider.getDao().voteFor(DAOProvider.getDao().getPollEntryById(entryID));
		resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
	}
	
}
