package hr.fer.zemris.math;

public class Vector3 {
	
	private double x, y, z;

	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param x prva koponenta vektora
	 * @param y druga komponenta vektora
	 * @param z treća komponenta vektora
	 */
	public Vector3(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * Metoda za dobivanje norme vektora.
	 * 
	 * @return norma vektora
	 */
	public double norm() {
		
		if(this.x != 0 || this.y != 0 || this.z != 0) {
			return Math.sqrt(
					Math.pow(this.x, 2) + 
					Math.pow(this.y, 2) + 
					Math.pow(this.z, 2));
		}else {
			return 0;
		}
	}

	/**
	 * Metoda za normalizaciju vektora.
	 * 
	 * @return normalizirani vektor
	 */
	public Vector3 normalized() {

		double norm = this.norm();
		
		if(this.x != 0 || this.y != 0 || this.z != 0) {
			return new Vector3(
					this.x / norm, 
					this.y / norm, 
					this.z / norm);
		}else {
			return new Vector3(0, 0 ,0);
		}
	}

	/**
	 * Metoda za zbrajanje dvaju vektora.
	 * 
	 * @param other drugi vektor koji se pribraja trenutnom
	 * @return novi objekt tipa {@code Vector3} koji je zbroj
	 * @throws NullPointerException ako je predani vektor {@code other null}
	 */
	public Vector3 add(Vector3 other) {
		
		if(other == null)
			throw new NullPointerException("Predani vektor ne može biti null.");
		
		return new Vector3(
				this.x + other.x, 
				this.y + other.y, 
				this.z + other.z);
	}
	
	/**
	 * Metoda za oduzimanje dvaju vektora.
	 * 
	 * @param other drugi vektor koji se oduzima od trenutnom
	 * @return novi objekt tipa {@code Vector3} koji je razlika
	 * @throws NullPointerException ako je predani vektor {@code other null}
	 */
	public Vector3 sub(Vector3 other) {
		
		if(other == null)
			throw new NullPointerException("Predani vektor ne može biti null.");
		
		return new Vector3(
				this.x - other.x, 
				this.y - other.y, 
				this.z - other.z);
	}
	
	/**
	 * Metoda za skalarni produkt dvaju vektora.
	 * 
	 * @param other drugi vektor koji množi trenutni
	 * @return vrijednost skalarnog umnoška
	 * @throws NullPointerException ako je predani vektor {@code other null}
	 */
	public double dot(Vector3 other) {
		
		if(other == null)
			throw new NullPointerException("Predani vektor ne može biti null.");
		
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}
	
	/**
	 * Metoda za vektorski umnožak dvaju vektora.
	 * 
	 * @param other drugi vektor koji množi trenutni
	 * @return novi objekt tipa {@code Vector3} koji predstavlja vektorski umnožak
	 * @throws NullPointerException ako je predani vektor {@code other null}
	 */
	public Vector3 cross(Vector3 other) {
		
		if(other == null)
			throw new NullPointerException("Predani vektor ne može biti null.");
		
		double x = this.y * other.z - this.z * other.y;
		double y = -this.x * other.z + this.z * other.x;
		double z = this.x * other.y - this.y * other.x;
		
		return new Vector3(x, y, z);
	}
	
	/**
	 * Metoda za množenje vektora skalarom.
	 * 
	 * @param s skalar kojim se množi trenutni vektor
	 * @return novi objekt tipa {@code Vector3} koji je uvećan {@code s} puta
	 */
	public Vector3 scale(double s) {
		
		return new Vector3(this.x * s, this.y * s, this.z * s);
	}
	
	/**
	 * Metoda za dobivanje kosinusa kuta među dva vektora.
	 * 
	 * @param other drugi vektor
	 * @return kosinus kuta među vektorima
	 * @throws NullPointerException ako je predani vektor {@code other null}
	 */
	public double cosAngle(Vector3 other) {
		
		if(other == null)
			throw new NullPointerException("Predani vektor ne može biti null.");
		
		return this.dot(other) / (this.norm() * other.norm());
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	/**
	 * Metoda za vraćanje koponenta vektora u polju.
	 * 
	 * @return polje koje sadrži komponente vektora
	 */
	public double[] toArray() {
		
		return new double[] {this.x, this.y, this.z};
	}
	
	@Override
	public String toString() {
		return "(" + x + ", " + y + ", " + z + ")";
	}
	
}
