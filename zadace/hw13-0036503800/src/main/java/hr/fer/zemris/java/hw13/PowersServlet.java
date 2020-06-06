package hr.fer.zemris.java.hw13;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Servlet koji stvara .xlxs datoteku s {@code n} stranica
 * i gdje je na svakoj stranici raspon brojeva od {@code a} 
 * do {@code b} te je svaki broj dignut na potenciju trenutne 
 * stranice. Navedeni {@code n, a, b} dobivaju se preko alatne 
 * trake te njihove vrijednosti moraju biti u sljedećim inervalima:
 * {@code -100<=a<=100, -100<=b<=100, 1<=n<=5}.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name = "powers", urlPatterns = { "/powers" })
public class PowersServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		try {

			int a = Integer.parseInt(String.valueOf(req.getParameter("a")));
			int b = Integer.parseInt(String.valueOf(req.getParameter("b")));
			int n = Integer.parseInt(String.valueOf(req.getParameter("n")));

			if (a > 100 || a < -100 || b > 100 || b < -100 || n > 5 || n < 1) {
				req.getRequestDispatcher("/WEB-INF/pages/errorOnPowers.jsp").forward(req, resp);
				System.out.println("jebo ti isusa");
			}

			// objekt .xlxs knjige
			XSSFWorkbook workbook = new XSSFWorkbook();

			int pageNumber = 1;
			// stranice
			for (int i = 0; i < n; i++) {
				
				XSSFSheet sheet = workbook.createSheet("Page " + String.valueOf(pageNumber));

				int rowCount = 0;
				// retci
				for(int j = a; j <= b; j++) {
					
					Row row = sheet.createRow(rowCount++);
					// prvi stupac -> broj
					Cell cell0 = row.createCell(0);
					cell0.setCellValue(j);
					// drugi stupac -> broj na potenciju stranice
					Cell cell1 = row.createCell(1);
					cell1.setCellValue(Math.pow(j, pageNumber));
				}
				
				pageNumber++;
			}

			resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
			resp.setHeader("Content-Disposition", "attachment; knjiga.xlsx"); 
			workbook.write(resp.getOutputStream());
			
			req.getRequestDispatcher("/index").forward(req, resp);

		} catch (Exception e) {
			req.getRequestDispatcher("/WEB-INF/pages/errorOnPowers.jsp").forward(req, resp);
		}
	}

}
