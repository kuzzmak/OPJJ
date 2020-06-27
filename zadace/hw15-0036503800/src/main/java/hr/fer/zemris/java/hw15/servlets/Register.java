package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za registraciju korisnika.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="register", urlPatterns={"/servleti/register"})
public class Register extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
	}
}