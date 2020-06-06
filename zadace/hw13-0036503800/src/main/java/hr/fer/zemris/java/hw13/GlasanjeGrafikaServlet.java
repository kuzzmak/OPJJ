package hr.fer.zemris.java.hw13;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * Servlet za prikaz rezultata glasanja za najbolji bend u obliku grafa.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns = {"/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@SuppressWarnings("unchecked")
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// rezultati glasanja
		Map<Long, Integer> results = (Map<Long, Integer>) req.getServletContext().getAttribute("results");
		// popis bendova
		Map<Long, String> bands = (Map<Long, String>) req.getServletContext().getAttribute("bands");
		
		DefaultPieDataset data = new DefaultPieDataset();
		
		for(Map.Entry<Long, String> entry: bands.entrySet()) {
			data.setValue(entry.getValue(), results.get(entry.getKey()));
		}
		
		JFreeChart chart = ChartFactory.createPieChart3D("Rezultati glasanja", data, true, true, false);
		
		BufferedImage objBufferedImage = chart.createBufferedImage(500, 500);
		
		ByteArrayOutputStream bas = new ByteArrayOutputStream();
        try {
            ImageIO.write(objBufferedImage, "png", bas);
        } catch (IOException e) {
            e.printStackTrace();
        }

		byte[] byteArray = bas.toByteArray();
		
		resp.setContentType("image/png");
		resp.getOutputStream().write(byteArray);
	}

}
