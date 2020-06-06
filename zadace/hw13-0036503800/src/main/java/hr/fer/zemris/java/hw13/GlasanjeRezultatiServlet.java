package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za prikaz stranice rezultata glasanja za najbolji bend.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-rezultati", urlPatterns = {"/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Map<Long, Integer> results = (Map<Long, Integer>) req.getServletContext().getAttribute("results");
		
		results = results.entrySet()
                .stream()
                .sorted((Map.Entry.<Long, Integer>comparingByValue().reversed()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		req.getServletContext().setAttribute("results", results);
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp); 
	}
	
}
