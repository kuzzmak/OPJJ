package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.util.Map;

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
 * Servlet za stvaranje .xlxs datoteke s rezultatima glasanja za najbolji bend.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-xls", urlPatterns = {"/glasanje-xls"})
public class GlasanjeXlsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Map<Long, Integer> results = (Map<Long, Integer>) req.getServletContext().getAttribute("results");
		Map<Long, String> bands = (Map<Long, String>) req.getServletContext().getAttribute("bands");
		
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet("Page 1");
		
		int rowCount = 0;
		
		Row row = sheet.createRow(rowCount);
		
		// imena
		Cell cell0 = row.createCell(0);
		cell0.setCellValue("Bend");
		Cell cell1 = row.createCell(1);
		cell1.setCellValue("Broj glasova");
		
		rowCount++;
		
		for(Map.Entry<Long, String> entry: bands.entrySet()) {
			
			row = sheet.createRow(rowCount++);
			
			// ime benda
			cell0 = row.createCell(0);
			cell0.setCellValue(entry.getValue());
			// broj glasova
			cell1 = row.createCell(1);
			cell1.setCellValue(results.get(entry.getKey()));
		}
		
		resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"); 
		resp.setHeader("Content-Disposition", "attachment; rezultati.xlsx"); 
		workbook.write(resp.getOutputStream());
	}

}
