package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	private BarChart chart;

	private static final int XOFFSET = 50;
	private static final int YOFFSET = 50;

	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
		setPreferredSize(new Dimension(500, 500));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawMesh(g);
		drawRectangles(g, Color.green, 2);
		drawYaxis(g);
		drawXAxis(g, 2);
	}

	public void drawCenteredString(Graphics g, String text, Rectangle rect) {

		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(text, x, y);
	}

	public void drawRightCenteredString(Graphics g, String text, Rectangle rect) {

		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int x = rect.width - metrics.stringWidth(text);
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.yellow);
		g.drawString(text, x, y);
		g.setColor(Color.black);
	}

	public void drawXAxis(Graphics g, int lineSpacing) {

		int valuesSize = chart.getValues().size();
		Dimension size = getSize();

		int x = 50 + lineSpacing / 2;
		for (int i = 0; i < chart.getValues().size(); i++) {

			int lineWidthNormalized = (size.width - 50 - 10) / valuesSize;

			drawCenteredString(g, String.valueOf(i + 1), new Rectangle(x + lineWidthNormalized * i,
					size.height - XOFFSET, lineWidthNormalized - lineSpacing, 20));
		}

		g.drawLine(XOFFSET, size.height - XOFFSET, size.width - 10, size.height - XOFFSET);

		drawCenteredString(g, chart.getxName(),
				new Rectangle(YOFFSET, size.height - XOFFSET + 20, size.width - 10 - YOFFSET, 25));
	}
	
	public void drawMesh(Graphics g) {
		
		Dimension size = getSize();
		int yMax = chart.getMaxY();
		int diff = chart.getDiff();
		
		int lineHeightNormalized = (size.height - XOFFSET) / yMax;
		
		for(int i = 0; i <= yMax; i += diff) {
			g.drawLine(XOFFSET - 5, size.height - YOFFSET - i * lineHeightNormalized, size.width - 10, size.height - YOFFSET - i * lineHeightNormalized);
		}
	}

	public void drawYaxis(Graphics g) {

		Dimension size = getSize();
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform defaultAt = g2d.getTransform();

		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		drawCenteredString(g, chart.getyName(), new Rectangle(-(size.height + XOFFSET) / 2, 2, 100, 20));

		at.rotate(Math.PI / 2);
		g2d.setTransform(at);

		int yMax = chart.getMaxY();
		int yMin = chart.getMinY();
		int diff = chart.getDiff();

		int x1 = YOFFSET;
		int y1 = 10;
		int x2 = YOFFSET;
		int y2 = size.height - XOFFSET;

		g.drawLine(x1, y1, x2, y2);
		
		int lineHeightNormalized = (size.height - XOFFSET) / yMax;

		for (int i = 0; i <= yMax; i += diff) {
			int x = 25;
			int y = size.height - XOFFSET - lineHeightNormalized / 2;
			int width = 20;
			int height = lineHeightNormalized;
			
			drawCenteredString(g, String.valueOf(i), new Rectangle(x, y - lineHeightNormalized * i, width, height));
			
		}
	}

	/**
	 * Metoda za crtanje linija svake vrijednosti.
	 * 
	 * @param g           komponenta po kojoj se crta
	 * @param color       boja linija
	 * @param lineSpacing razmak među pojedinim linijama
	 */
	private void drawRectangles(Graphics g, Color color, int lineSpacing) {

		List<XYValue> values = chart.getValues();

		int yMax = chart.getMaxY();

		// veličina trenutnog prozora
		Dimension size = getSize();

		// širina pojedine linije, oduzeto s lijeve strane 50 za naziv y osi
		// i vrijednosti y osi i 10 s desne strane za strelicu x osi
		int lineWidthNormalized = (size.width - 50 - 10) / values.size();
		// iznos jedinične vrijednosti
		int lineHeightNormalized = (size.height - XOFFSET) / yMax;

		Color oldColor = g.getColor();
		g.setColor(color);

		// početak prve linije
		int x = 50 + lineSpacing / 2;
		for (int i = 0; i < values.size(); i++) {

			int lineHeight = lineHeightNormalized * values.get(i).getY();
			int y = size.height - XOFFSET - lineHeight;
			int width = lineWidthNormalized - lineSpacing;

			g.fillRect(x + lineWidthNormalized * i, y, width, lineHeight);
		}

		g.setColor(oldColor);
	}

	private void drawText(Graphics g, String text, int x, int y, Color color) {

		Font oldFont = g.getFont();
		Color oldColor = g.getColor();

		Font font = new Font("Serif", Font.PLAIN, 18);
		g.setFont(font);
		g.setColor(color);
		g.drawString(text, x, y);
		g.setFont(oldFont);
		g.setColor(oldColor);
	}

}
