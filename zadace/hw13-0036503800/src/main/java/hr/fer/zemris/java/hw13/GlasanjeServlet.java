package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za odabir najboljeg benda, odnosno glasanje.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje", urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp); 
	}

}
