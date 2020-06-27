package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet za odjavljivanje.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="logout", urlPatterns={"/servleti/logout"})
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();  
		
		if(session != null) {
			session.invalidate();  
		}
        
		req.getRequestDispatcher("servlets/main").forward(req, resp);
	}

}
