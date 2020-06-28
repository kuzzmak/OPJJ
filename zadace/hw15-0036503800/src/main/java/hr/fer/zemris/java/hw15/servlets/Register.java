package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOException;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.forms.BlogUserForm;

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
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		
		BlogUserForm buf = new BlogUserForm();
		buf.fromHttpRequest(req);
		
		try{
			DAOProvider.getDAO().registerNewUser(buf);
		}catch (DAOException e) {
			req.setAttribute("error", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/pages/Register.jsp").forward(req, resp);
			return;
		}
		
		req.getSession().setAttribute("current.user.nick", buf.getUsername());
		req.getSession().setAttribute("current.user.fn", buf.getName());
		req.getSession().setAttribute("current.user.ln", buf.getSurname());
		
		resp.sendRedirect(req.getContextPath() + "/servleti/author?nick=" + buf.getUsername());
	}
	
}
