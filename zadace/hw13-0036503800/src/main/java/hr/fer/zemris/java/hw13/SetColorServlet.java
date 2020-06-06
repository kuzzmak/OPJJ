package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za postavljanje boje koja se koristi kao pozadinska kod svih stranica.
 * Jednom postavljena boja je važeća samo za vrijeme trajanja sjednice.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="setcolor", urlPatterns = {"/setcolor"})
public class SetColorServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String selectedColor = req.getParameter("selectedCol");
		
		req.getSession().setAttribute("pickedBgColor", selectedColor); 
		
		req.getRequestDispatcher("/WEB-INF/pages/colors.jsp").forward(req, resp);
	}
}
