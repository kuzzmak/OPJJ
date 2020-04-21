package hr.fer.zemris.math;

/**
 * Razred koji predstavlja dvodimenzionalni vektor čije su komponente x i y.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Vector2D {
	
	private double x;
	private double y;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param x - x komponenta vektora
	 * @param y - y komponenta vektora
	 */
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Metoda za translatiranje postojećeg vektora.
	 * 
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 * @param offset vektor za koji se translatira trenutni vektor
	 */
	public void translate(Vector2D offset) {
		
		if(offset == null)
			throw new NullPointerException("Nije moguće predati null kao vrijednost.");
		
		x += offset.getX();
		y += offset.getY();
	}
	
	/**
	 * Metoda za translaciju trenutnog vektora.
	 * 
	 * @param offset vektor za koji se translatira trenutni vektor
	 * @throws NullPointerException ako je predani vektor <code>null</code>
	 * @return trenutni vektor translatiran za <code>offset</code>
	 */
	public Vector2D translated(Vector2D offset) {
		
		if(offset == null)
			throw new NullPointerException("Nije moguće predati null kao vrijednost.");
		
		this.translate(offset);
		
		return this;
	}
	
	/**
	 * Metoda za rotaciju trenutnog vektora u smjeru obrnutom od smjera kazaljke
	 * na satu u desnom koordinatnom sustavu za kut <code>angle</code> u radijanima.
	 * 
	 * @param angle kut za koji se zakreće vektor
	 */
	public void rotate(double angle) {
		
		double tempX = x * Math.cos(angle) - y * Math.sin(angle);
		double tempY = x * Math.sin(angle) + y * Math.cos(angle);
	
		x = tempX;
		y = tempY;
	}
	
	/**
	 * Metoda za rotaciju trenutnog vektora u smjeru obrnutom od smjera kazaljke
	 * na satu u desnom koordinatnom sustavu za kut <code>angle</code> u radijanima.
	 * 
	 * @param angle kut za koji se zakreće vektor
	 * @return zakrenuti vektor
	 */
	public Vector2D rotated(double angle) {
		
		this.rotate(angle);
		
		return this;
	}
	
	/**
	 * Metoda za množenje trenutnog vektora skalarom <code>scalar</code>.
	 * 
	 * @param scalar vrijednost kojom se množi vektor
	 */
	public void scale(double scalar) {
		
		x *= scalar;
		y *= scalar;
	}
	
	/**
	 * Metoda za množenje trenutnog vektora skalarom <code>scalar</code>.
	 * 
	 * @param scalar vrijednost kojom se množi vektor
	 * @return skaliran vektor
	 */
	public Vector2D scaled(double scalar) {
		
		this.scale(scalar);
		return this;
	}
	
	/**
	 * Metoda za stvaranje novog objekta trenutnog vektora.
	 * 
	 * @return nova kopija vektora
	 */
	public Vector2D copy() {
		
		return new Vector2D(x, y);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	@Override
	public String toString() {
		return "x=" + x + ", y=" + y + "]";
	}
}
