package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji modelira jedan kompleksni broj. Razred također
 * omogućuje izvođenje svih operacije među kompleksnim brojevima
 * popu zbrajanja, oduzimanje, potenciranje, korjenovanje i drugih.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Complex {
	
	private double re;
	private double im;

	public static final Complex ZERO = new Complex(0, 0);
	public static final Complex ONE = new Complex(1, 0);
	public static final Complex ONE_NEG = new Complex(-1, 0);
	public static final Complex IM = new Complex(0, 1);
	public static final Complex IM_NEG = new Complex(0, -1);
	
	public Complex() {
		
	}
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param re realni dio kompleksnog broja
	 * @param im imaginarni dio kompleksnog broja
	 */
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
	private double angle() {

		if(this.im < 0) {
			return Math.atan2(this.im, this.re) + 2 * Math.PI;
		}
		return Math.atan2(this.im, this.re);
	}
	
	/**
	 * Metoda za zbrajanje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji se pribraja trenutnom
	 * @return zbroj koji je novi objekt tipa {@code Complex}
	 * @throws NullPointerException ako je predan null
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
	 * @return razlika koja je novi objekt tipa {@code Complex}
	 * @throws NullPointerException ako je predan null
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
	 * @return umnožak koji je novi objekt tipa {@code Complex}
	 * @throws NullPointerException ako je predan null
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
		
		double real;
		double imaginary;
		
		if(re == 0) real = 0;
		else real = -re;
		
		if(im == 0) imaginary = 0;
		else imaginary = -im;
		
		return new Complex(real, imaginary);
	}
	
	/**
	 * Metoda za vraćanje kompleksno konjugiranog broja trenutnog broja.
	 * 
	 * @return kompleksno konjugirani broj trenutnog broja
	 */
	private Complex conjugate() {
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
	
	/**
	 * Metoda za stvaranje kopije trenutnog kompleksnog broja.
	 * 
	 * @return kopija trenutnog kompleksnog broja
	 */
	public Complex copy() {
		
		return new Complex(re, im);
	}
	
	/**
	 * Funkcija za parsiranje imaginarnog dijela kompleksnog broja oblika (i, -i,
	 * 2i, 2.1i, -2.1i...)
	 * 
	 * @param s imaginarni dio koji se parsira
	 * @return double vrijednost imaginarnog dijela
	 */
	public static double parseImaginary(String s) {

		if (s.equals("i")) {
			return 1;
		} else if (s.equals("-i")) {
			return -1;
		} else {
			return Double.parseDouble(s.replace("i", ""));
		}
	}

	/**
	 * Metoda za parsiranje kompleksnog broja zadanog preko stringa. Valjani zapisi
	 * su:
	 * <p>
	 * "351", "+351", "-351", "3.51", "1+i", "1 + i", "+2.1+1.1i", "-i", "+i" ...
	 * </p>
	 * 
	 * Nedozvoljeni zapisi su:
	 * <p>
	 * "+-351", "i3.51", "-2.71+-3.1i" ...
	 * </p>
	 * 
	 * @param s string koji se parsira
	 * @throws NumberFormatException ako je umjesto i korišten neki znak
	 * @return objekt tipa ComplexNumber
	 */
	public static Complex parse(String s) {

		// uklonjene sve praznine iz broja
		String noSpaces = s.replaceAll("\\s+", "");

		// imaginarni broj
		if (noSpaces.contains("i")) {

			if (noSpaces.startsWith("+")) {
				noSpaces = noSpaces.replaceFirst("\\+", "");
			}

			// ako ima neki plus nakon potencijalnog micanja prvog plusa
			// (brojevi s plusom između realnom i imaginarnog dijela)
			if (noSpaces.contains("+")) {

				String[] realAndImaginary = noSpaces.split("\\+");

				if (realAndImaginary.length == 2) {

					// ako je prvi dio kompleksnog broja imaginarni dio, npr. "-i + 1"
					if (realAndImaginary[0].contains("i")) {

						double real = Double.parseDouble(realAndImaginary[1]);
						double imaginary = parseImaginary(realAndImaginary[0]);
						return new Complex(real, imaginary);

					} else { // oblik kompleksnog broja "-1 + i"

						double real = Double.parseDouble(realAndImaginary[0]);
						double imaginary = parseImaginary(realAndImaginary[1]);
						return new Complex(real, imaginary);
					}
				}
			} else { // kompleksni brojevi s minusom

				String[] realAndImaginary = noSpaces.split("-");

				// oblik broja 2i, 1.1i
				if (realAndImaginary.length == 1) {

					double imaginary = parseImaginary(realAndImaginary[0]);

					return new Complex(0, imaginary);

				} else if (realAndImaginary.length == 2) {

					// oblik broja -2i
					if (realAndImaginary[0].equals("")) { // kada se splita -2i, dobije se ["", 2i]
						double imaginary = -parseImaginary(realAndImaginary[1]);

						return new Complex(0, imaginary);

					} else {

						if (realAndImaginary[0].contains("i")) { // oblik broja i - 1

							double real = -Double.parseDouble(realAndImaginary[1]);
							double imaginary = parseImaginary(realAndImaginary[0]);
							return new Complex(real, imaginary);

						} else { // oblik broja 1 - i

							double real = Double.parseDouble(realAndImaginary[0]);
							double imaginary = -parseImaginary(realAndImaginary[1]);
							return new Complex(real, imaginary);
						}
					}

				} else { // broj oblika -1 -i, -2.1i - 2, split napravi ["", 2.1, 2]

					if (realAndImaginary[1].contains("i")) {

						double real = -Double.parseDouble(realAndImaginary[2]);
						double imaginary = -parseImaginary(realAndImaginary[1]);
						return new Complex(real, imaginary);

					} else {

						double real = -Double.parseDouble(realAndImaginary[1]);
						double imaginary = -parseImaginary(realAndImaginary[2]);
						return new Complex(real, imaginary);
					}
				}
			}

		} else { // kompleksni broj koji nema imaginarni dio
			try { // ako je umjesto i korišten neki drugi znak, događa se iznimka
				double real = Double.parseDouble(noSpaces);
				return new Complex(real, 0);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return null;
	}
	
	public double getRe() {
		return re;
	}

	public double getIm() {
		return im;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(im);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(re);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Complex other = (Complex) obj;
		if (Double.doubleToLongBits(im) != Double.doubleToLongBits(other.im))
			return false;
		if (Double.doubleToLongBits(re) != Double.doubleToLongBits(other.re))
			return false;
		return true;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();
		sb.append("(");

		sb.append(re);
		
		if(im >= 0) {
			sb.append("+");
			sb.append(im);
			sb.append("i");
		}else {
			sb.append(im);
			sb.append("i");
		}
		
		sb.append(")");
		return sb.toString();
	}
	
}
