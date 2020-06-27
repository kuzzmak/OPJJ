package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
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
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/main");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BlogUserForm buf = new BlogUserForm();
		buf.fromHttpRequest(req);
		
		BlogUser bu = buf.getBlogUser();
		
		// korisnik iz baze s korisničkim imenom od "bu"
		BlogUser buInDatabase = DAOProvider.getDAO().findUserByUsername(bu.getNick());
		
		// ako nije pronađen korisnik s takvim korisničkim imenom ili se sažetci lozinka ne slažu,
		// preusmjerava se opet na početnu stranicu
		if(buInDatabase == null || !buInDatabase.getPasswordHash().equals(bu.getPasswordHash())) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/servleti/login");
		}else {
			
			req.getSession().setAttribute("current.user.id", buInDatabase.getId());
			req.getSession().setAttribute("current.user.nick", buInDatabase.getNick());
			req.getSession().setAttribute("current.user.fn", buInDatabase.getFirstName());
			req.getSession().setAttribute("current.user.ln", buInDatabase.getLastName());
			
			resp.sendRedirect(req.getContextPath() + "/servleti/author?nick=" + buInDatabase.getNick());
		}
	}

}
