package hr.fer.zemris.java.hw14.servlets;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import hr.fer.zemris.java.hw14.DAOProvider;
import hr.fer.zemris.java.hw14.PollEntry;

/**
 * Servlet za prikaz rezultata glasanja za najbolji bend u obliku grafa.
 * 
 * @author Antonio Kuzminski
 *
 */
@WebServlet(name="glasanje-grafika", urlPatterns = {"/servleti/glasanje-grafika"})
public class GlasanjeGrafikaServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		Long pollID = Long.parseLong(String.valueOf(req.getSession().getAttribute("pollID")));
		
		List<PollEntry> entries = DAOProvider.getDao().getPollEntryList(pollID);
		
		DefaultPieDataset data = new DefaultPieDataset();
		
		for(PollEntry entry: entries) {
			data.setValue(entry.getTitle(), entry.getVotesCount());
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
