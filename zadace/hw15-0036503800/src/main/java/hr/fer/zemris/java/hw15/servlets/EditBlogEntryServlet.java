package hr.fer.zemris.java.hw15.servlets;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.hw15.forms.BlogEntryForm;
import hr.fer.zemris.java.hw15.model.BlogEntry;

/**
 * Servlet za uređivanje neke objave.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name = "editBlogEntry", urlPatterns = { "/servleti/editBlogEntry" })
public class EditBlogEntryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String currentUser = (String) req.getSession().getAttribute("current.user.nick");
		String blogId = req.getParameter("blogId");

		BlogEntry be = DAOProvider.getDAO().getBlogEntry(Long.valueOf(blogId));

		boolean author = be.getCreator().getNick().equals(currentUser);

		if (author) {
			req.setAttribute("blogTitle", be.getTitle());
			req.setAttribute("blogText", be.getText());
			req.setAttribute("blogId", be.getId());
			req.getRequestDispatcher("/WEB-INF/pages/EditBlogEntry.jsp").forward(req, resp);
		} else {
			req.setAttribute("error", "Neovlašten pristup izmijeni objave. Prijavite se kao korisnik: "
					+ be.getCreator().getNick() + " kako biste mogli napravit izmjenu na objavi.");

			req.getRequestDispatcher("/WEB-INF/pages/Error.jsp").forward(req, resp);
		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");

		EntityManager em = JPAEMProvider.getEntityManager();
		BlogEntry oldBlogEntry = DAOProvider.getDAO().getBlogEntry(Long.valueOf(req.getParameter("blogId")));

		BlogEntryForm bef = new BlogEntryForm();
		bef.fromHttpRequest(req);

		BlogEntry be = bef.getBlogEntry();
		
		oldBlogEntry.setLastModifiedAt(be.getCreatedAt());
		oldBlogEntry.setText(be.getText());
		oldBlogEntry.setTitle(be.getTitle());
		
		resp.sendRedirect(req.getContextPath() + "/servleti/author?nick=" + req.getSession().getAttribute("current.user.nick"));
	}

}
