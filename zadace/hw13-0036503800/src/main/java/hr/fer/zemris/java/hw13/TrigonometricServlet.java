package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet za ispis vrijednosti funkcija sinusa i kosinusa određenog
 * raspona prirodnih brojeva.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="trigonometric", urlPatterns = {"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
	
		Object oa = req.getParameter("a");
		Object ob = req.getParameter("b");
		
		oa = oa == null ? 0 : oa;
		ob = ob == null ? 360 : ob;
		
		int a = Integer.parseInt(String.valueOf(oa));
		int b = Integer.parseInt(String.valueOf(ob));
		
		if(a > b) {
			int c = a;
			a = b;
			b = c;
		}
		if(b > a + 720) {
			b = a + 720;
		}
		
		List<TableEntry> entries = new ArrayList<>();
		for(int i = a; i <= b; i++) {
			entries.add(new TableEntry(
					i, 
					Math.sin(Math.toRadians(i)), 
					Math.cos(Math.toRadians(i))));
		}
		req.setAttribute("entries", entries);
		
		req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
	}
}
