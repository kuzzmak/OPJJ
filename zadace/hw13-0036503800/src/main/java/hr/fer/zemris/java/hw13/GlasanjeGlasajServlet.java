package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za glasanje najboljeg benda.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-glasaj", urlPatterns = {"/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Long, Integer> results = (Map<Long, Integer>) req.getServletContext().getAttribute("results");
		
		// identifikacijski broj pojedinog benda
		Long id = Long.parseLong(req.getParameter("id"));
		
		int old = results.get(id);
		results.put(id, old + 1);
		
		req.getServletContext().setAttribute("results", results);
		
		resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
	}
	
}
