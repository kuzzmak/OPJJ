package hr.fer.zemris.java.hw13;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
 * Servlet koji prikazuje korištenje operacijskih sustava
 * u obliku grafa.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name = "reportImage", urlPatterns = { "/reportImage" })
public class ReportImageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		DefaultPieDataset dataset = new DefaultPieDataset();
		dataset.setValue("Linux", 29);
		dataset.setValue("Mac", 20);
		dataset.setValue("Windows", 51);

		JFreeChart chart = ChartFactory.createPieChart3D("OS usage", dataset, true, true, false);

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
