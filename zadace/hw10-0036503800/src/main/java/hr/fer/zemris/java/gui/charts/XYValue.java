package hr.fer.zemris.java.gui.charts;

/**
 * Razred koji predstavlja jednu vrijednost grafa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class XYValue {
	
	private int x;
	private int y;
	
	/**
	 * Konstruktor.
	 * 
	 * @param x x komponenta točke
	 * @param y y komponenta točke
	 */
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}

	@Override
	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
}
