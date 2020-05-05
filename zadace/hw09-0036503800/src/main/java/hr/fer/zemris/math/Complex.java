package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

public class Complex {
	
	private double re;
	private double im;

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * Metoda za izračun apsolutne vrijednosti kompleksnog broja.
	 * 
	 * @return apsolutna vrijdnost kompleksnog broja
	 */
	public double module() {
		if (this.re == 0 && this.im== 0)
			return 0;
		return Math.sqrt(Math.pow(this.re, 2) + Math.pow(this.im, 2));
	}
	
	/**
	 * Metoda za izračun kuta kompleksnog broja u rasponu [0, 2pi].
	 * 
	 * @throw ArithmeticException kada je imaginarni i realni dio 0
	 * @return kut kompleksnog broja
	 */
	public double angle() {

		if(this.im < 0) {
			return Math.atan2(this.im, this.re) + 2 * Math.PI;
		}
		return Math.atan2(this.im, this.re);
	}
	
	/**
	 * Metoda za zbrajanje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji se pribraja trenutnom
	 * @throws NullPointerException ako je predan null
	 * @return zbroj koji je novi objekt tipa {@code Complex}
	 */
	public Complex add(Complex other) {

		if (other == null)
			throw new NullPointerException("Nemoguće zbrojiti s null.");

		return new Complex(this.re + other.re, this.im + other.im);
	}

	/**
	 * Metoda za oduzimanje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji se oduzima od trenutnog
	 * @throws NullPointerException ako je predan null
	 * @return razlika koja je novi objekt tipa {@code Complex}
	 */
	public Complex sub(Complex other) {
		if (other == null)
			throw new NullPointerException("Nemoguće oduzeti s null.");

		return new Complex(this.re - other.re, this.im - other.im);
	}

	/**
	 * Metoda za množenje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji množi trenutno
	 * @throws NullPointerException ako je predan null
	 * @return umnožak koji je novi objekt tipa {@code Complex}
	 */
	public Complex multiply(Complex other) {
		if (other == null)
			throw new NullPointerException("Nemoguće pomnožiti s null.");
		
		double real = this.re * other.re - this.im * other.im;
		double imaginary = this.re * other.im + this.im * other.re;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Metoda za vraćanje minus vrijednosti trenutnog kompleksnog broja.
	 * 
	 * @return minus vrijednost trenutnog kompleksnog broja
	 */
	public Complex negate() {
		return new Complex(-this.re, -this.im);
	}
	
	/**
	 * Metoda za vraćanje kompleksno konjugiranog broja trenutnog broja.
	 * 
	 * @return kompleksno konjugirani broj trenutnog broja
	 */
	public Complex conjugate() {
		return new Complex(this.re, -this.im);
	}
	
	/**
	 * Metoda za dijeljenje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji dijeli trenutni
	 * @throws NullPointerException ako je predan null
	 * @throws ArithmeticException ako se pokušava podijeliti s 0
	 * @return količnik koji je novi objekt tipa {@code Complex}
	 */
	public Complex divide(Complex other) {
		
		if (other == null)
			throw new NullPointerException("Nemoguće pomnožiti s null.");
		if(other.toString().equals("0"))
			throw new ArithmeticException("Nemoguće dijeliti s 0.");
		
		Complex conjugate = other.conjugate();
		Complex nominator = this.multiply(conjugate);
		Complex denominator = other.multiply(conjugate);
		
		double real = nominator.re / denominator.re;
		double imaginary = nominator.im / denominator.re;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Metoda za potenciranje kompleksnog broja.
	 * 
	 * @param n potencija na koju se diže kompleksni broj
	 * @return novi kompleksni broj dignut na potenciju {@code n}
	 */
	public Complex power(int n) {
		
		if(n == 0) return new Complex(1, 0);
		if(n == 1) return new Complex(this.re, this.im);
		
		double magnitute = this.module();
		double angle = this.angle();
		double magnitudeN = Math.pow(magnitute, n);
		double cos = Math.cos(angle * n);
		double sin = Math.sin(angle * n);
		
		return new Complex(magnitudeN * cos, magnitudeN * sin);
	}
	
	/**
	 * Metoda za računanje n-tog korijena kompleksnog broja.
	 * 
	 * @param n red korijena koji se računa
	 * @throws IllegalArgumentException ako je argument manji od 1
	 * @return lista korijena veličine {@code n}
	 */
	public List<Complex> root(int n) {
		
		if(n < 1) throw new IllegalArgumentException("Kao argument je moguće predati samo prirodne brojeve.");
		
		List<Complex> roots = new ArrayList<>();
		
		double angle = this.angle();
		double magnitudeN = this.module();
		
		magnitudeN = Math.pow(magnitudeN, 1. / n);

		for(int i = 0; i < n; i++) {
			
			double real = magnitudeN * Math.cos((angle + 2 * Math.PI * i) / n);
			double imaginary = magnitudeN * Math.sin((angle + 2 * Math.PI * i) / n);
			
			roots.add(new Complex(real, imaginary));
		}
		
		return roots;
	}

	@Override
	public String toString() {
		return "(" + re + ", " + im + ")";
	}
	
}
