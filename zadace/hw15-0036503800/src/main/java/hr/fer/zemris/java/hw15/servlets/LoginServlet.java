package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.forms.BlogUserForm;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Servlet koji obrađuje prijavu korisnika.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="login", urlPatterns={"/servleti/login"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BlogUserForm buf = new BlogUserForm();
		buf.fromHttpRequest(req);
		
		BlogUser bu = buf.getBlogUser();
		
		
		System.out.println(buf);
	}

}
