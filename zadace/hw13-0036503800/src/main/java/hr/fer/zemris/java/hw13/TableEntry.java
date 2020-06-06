package hr.fer.zemris.java.hw13;

/**
 * Razred koji predstavlja jedan element liste koja se stvara
 * prilikom izračuna vrijednosti trigonometrijskih funkcija
 * {@code TrigonometricServlet}-a.
 * 
 * @author Antonio Kuzminski
 *
 */
public class TableEntry {

	int number;
	double sinValue;
	double cosValue;
	
	/**
	 * Konstruktor
	 * 
	 * @param number prirodni broj koji je argument sinusa i kosinusa
	 * @param sinValue vrijednost {@code sin(number)}
	 * @param cosValue vrijednost {@code cos(number)}
	 */
	public TableEntry(int number, double sinValue, double cosValue) {
		this.number = number;
		this.sinValue = sinValue;
		this.cosValue = cosValue;
	}

	/**
	 * Dohvat prirodnog broja koji se koristi za izračun 
	 * trigonometrijskih funkcija sin i cos.
	 * 
	 * @return broj
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Dohvat vrijednosti sinusa broja.
	 * 
	 * @return sinus broja
	 */
	public double getSinValue() {
		return sinValue;
	}

	/**
	 * Dohvat vrijednosti kosinusa broja.
	 * 
	 * @return kosinus broja
	 */
	public double getCosValue() {
		return cosValue;
	}
	
}
