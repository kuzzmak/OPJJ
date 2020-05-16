package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.util.List;

import javax.swing.JComponent;

/**
 * Razred koji grafički prikazuje vrijednosti u obliku stupčastog grafa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	private BarChart chart;

	// pomak x osi od dna prozora
	private static final int XOFFSET = 50;
	// pomak y osi od lijeve strane prozora
	private static final int YOFFSET = 50;
	
	private static final int TOPOFFSET = 10;

	/**
	 * Konstruktor.
	 * 
	 * @param chart objekt koji sadrži vrijednosti grafa i druge važne informacije
	 */
	public BarChartComponent(BarChart chart) {
		super();
		this.chart = chart;
		setPreferredSize(new Dimension(500, 500));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawMesh(g);
		drawRectangles(g, Color.green, 5);
		drawYaxis(g);
		drawXAxis(g, 2);
	}

	/**
	 * Funkcija za crtanje poravnatog teksta u nekom pravokutniku.
	 * 
	 * @param g objekt po kojem se crta
	 * @param text tekst koji se upisuje
	 * @param rect pravokutnik u kojem se tekst centrira
	 * @param allignment vrsta poravnanja
	 * @throws IllegalArgumentException uslijed neispravnog poravnanja
	 */
	public void drawAllignedString(Graphics g, String text, Rectangle rect, String allignment) {

		FontMetrics metrics = g.getFontMetrics(g.getFont());
		int x;
		
		if(allignment.equals("center")) {
			x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
		}else if(allignment.equals("right")){
			x = 20 + rect.width - metrics.stringWidth(text);
		}else
			throw new IllegalArgumentException("Neispravno poravnanje.");
		
		int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
		g.drawString(text, x, y);
	}

	/**
	 * Metoda za crtanje x osi grafa.
	 * 
	 * @param g objekt po kojem se crta
	 * @param lineSpacing razmak među pojedinim stupcima grafa
	 */
	public void drawXAxis(Graphics g, int lineSpacing) {

		int valuesSize = chart.getValues().size();
		Dimension size = getSize();

		// vrijednosti osi x
		int x = 50 + lineSpacing / 2;
		for (int i = 0; i < chart.getValues().size(); i++) {

			int lineWidthNormalized = (size.width - 50 - 20) / valuesSize;

			drawAllignedString(g, 
					String.valueOf(i + 1), 
					new Rectangle(
							x + lineWidthNormalized * i,
							size.height - XOFFSET + TOPOFFSET, 
							lineWidthNormalized - lineSpacing, 
							20 + TOPOFFSET),
					"center");
		}

		//os x
		g.drawLine(XOFFSET, size.height - XOFFSET + TOPOFFSET, size.width, size.height - XOFFSET + TOPOFFSET);

		drawAllignedString(g, chart.getxName(),
				new Rectangle(YOFFSET, size.height - XOFFSET + 30, size.width - 10 - YOFFSET, 20), "center");
		
		// strelica
		g.drawLine(size.width, size.height - XOFFSET + TOPOFFSET, size.width - 5, size.height - XOFFSET + 5 +TOPOFFSET);
		g.drawLine(size.width, size.height - XOFFSET + TOPOFFSET, size.width - 5, size.height - XOFFSET - 5 +TOPOFFSET);
	}
	
	/**
	 * Metoda za crtanje mreže grafa.
	 * 
	 * @param g objekt po kojem se crta
	 */
	public void drawMesh(Graphics g) {
		
		Dimension size = getSize();
		int yMax = chart.getMaxY();
		int yMin = chart.getMinY();
		int diff = chart.getDiff();
		
		int lineHeightNormalized = (size.height - XOFFSET) / (yMax - yMin);
		
		// horizontalne linije
		for(int i = 0; i <= yMax - yMin; i += diff) {
			g.drawLine(YOFFSET - 5, 
					size.height - XOFFSET - i * lineHeightNormalized + TOPOFFSET, 
					size.width - 20, 
					size.height - XOFFSET - i * lineHeightNormalized + TOPOFFSET);
		}
		
		int valuesSize = chart.getValues().size();
		int lineWidthNormalized = (size.width - 50 - 20) / valuesSize;
		
		// vertikalne linije
		for(int i = 0;  i < valuesSize; i++) {
			int x1 = YOFFSET;
			int y1 = TOPOFFSET;
			int x2 = YOFFSET;
			int y2 = size.height - XOFFSET + TOPOFFSET;
			g.drawLine(x1 + i * lineWidthNormalized, y1, x2 + i * lineWidthNormalized, y2);
		}
	}

	/**
	 * Metoda za crtanje y osi.
	 * 
	 * @param g objekt po kojem se crta
	 */
	public void drawYaxis(Graphics g) {

		Dimension size = getSize();
		Graphics2D g2d = (Graphics2D) g;

		// okret koordinatnog sustava udesno za 90 stupnjeva
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);

		drawAllignedString(g, chart.getyName(), new Rectangle(-(size.height + XOFFSET) / 2, 2, 100, 20), "center");

		at.rotate(Math.PI / 2);
		g2d.setTransform(at);

		int yMax = chart.getMaxY();
		int yMin = chart.getMinY();
		int diff = chart.getDiff();

		// os y
		int x1 = YOFFSET;
		int y1 = 0;
		int x2 = YOFFSET;
		int y2 = size.height - XOFFSET;
		g.drawLine(x1, y1, x2, y2);
		
		// vrijednosti osi y
		int lineHeightNormalized = (size.height - XOFFSET) / (yMax - yMin);
		for (int i = yMin; i <= yMax; i += diff) {
			int x = 25;
			int y = size.height - XOFFSET - lineHeightNormalized / 2 + yMin * lineHeightNormalized + TOPOFFSET;
			int width = 20;
			int height = lineHeightNormalized;
			
			drawAllignedString(g, String.valueOf(i), new Rectangle(x, y - lineHeightNormalized * i, width, height), "right");
		}
		
		// strelica
		g.drawLine(YOFFSET, 0, YOFFSET + 5, 5);
		g.drawLine(YOFFSET, 0, YOFFSET - 5, 5);
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
		int yMin = chart.getMinY();

		// veličina trenutnog prozora
		Dimension size = getSize();

		// širina pojedine linije, oduzeto s lijeve strane 50 za naziv y osi
		// i vrijednosti y osi i 20 s desne strane za strelicu x osi
		int lineWidthNormalized = (size.width - 50 - 20) / values.size();
		// iznos jedinične vrijednosti
		int lineHeightNormalized = (size.height - XOFFSET) / (yMax - yMin);

		Color oldColor = g.getColor();
		g.setColor(color);

		// početak prve linije
		int x = 50 + lineSpacing / 2;
		for (int i = 0; i < values.size(); i++) {

			int lineHeight = lineHeightNormalized * values.get(i).getY();
			int y = size.height - XOFFSET - lineHeight + TOPOFFSET;
			int width = lineWidthNormalized - lineSpacing / 2;

			g.fillRect(x + lineWidthNormalized * i, 
					y + yMin * lineHeightNormalized, 
					width, 
					lineHeight - yMin * lineHeightNormalized);
		}

		g.setColor(oldColor);
	}

}
