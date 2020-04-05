package hr.fer.zemris.java.hw02;

import java.lang.Math;

/**
 * Razred za prikaz i izvođenje osnovnih aritmetičkih operacija s kompleksnim brojevima.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ComplexNumber {

	private double real;
	private double imaginary;

	/**
	 * Inicijalni konstruktor koji prima realni i imaginarni dio kompleksnog broja.
	 * 
	 * @param real      realni dio kompleksnog broja
	 * @param imaginary imaginarni dio kompleksnog broja
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
	}

	/**
	 * Metoda za dobivanje kompleksnog broja iz realnog.
	 * 
	 * @param real realni dio kompleksnog broja
	 * @return objekt tipa ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real, 0);
	}

	/**
	 * Metofa za dobivanje kompleksnog broja iz imaginarnog.
	 * 
	 * @param imaginary imaginarni dio kompleksnog broja
	 * @return objekt tipa ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}

	/**
	 * Metoda za dobivanje kompleksnog broja iz poznate apsolutne vrijednosti i kuta
	 * u radijanima.
	 * 
	 * @param magnitude apsolutna vrijednost kompleksnog broja
	 * @param angle     kut između realnog i imaginarnog dijela broja
	 * @return objekt tipa ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {

		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
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
	public static ComplexNumber parse(String s) {

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
						return new ComplexNumber(real, imaginary);

					} else { // oblik kompleksnog broja "-1 + i"

						double real = Double.parseDouble(realAndImaginary[0]);
						double imaginary = parseImaginary(realAndImaginary[1]);
						return new ComplexNumber(real, imaginary);
					}
				}
			} else { // kompleksni brojevi s minusom

				String[] realAndImaginary = noSpaces.split("-");

				// oblik broja 2i, 1.1i
				if (realAndImaginary.length == 1) {

					double imaginary = parseImaginary(realAndImaginary[0]);

					return new ComplexNumber(0, imaginary);

				} else if (realAndImaginary.length == 2) {

					// oblik broja -2i
					if (realAndImaginary[0].equals("")) { // kada se splita -2i, dobije se ["", 2i]
						double imaginary = -parseImaginary(realAndImaginary[1]);

						return new ComplexNumber(0, imaginary);

					} else {

						if (realAndImaginary[0].contains("i")) { // oblik broja i - 1

							double real = -Double.parseDouble(realAndImaginary[1]);
							double imaginary = parseImaginary(realAndImaginary[0]);
							return new ComplexNumber(real, imaginary);

						} else { // oblik broja 1 - i

							double real = Double.parseDouble(realAndImaginary[0]);
							double imaginary = -parseImaginary(realAndImaginary[1]);
							return new ComplexNumber(real, imaginary);
						}
					}

				} else { // broj oblika -1 -i, -2.1i - 2, split napravi ["", 2.1, 2]

					if (realAndImaginary[1].contains("i")) {

						double real = -Double.parseDouble(realAndImaginary[2]);
						double imaginary = -parseImaginary(realAndImaginary[1]);
						return new ComplexNumber(real, imaginary);

					} else {

						double real = -Double.parseDouble(realAndImaginary[1]);
						double imaginary = -parseImaginary(realAndImaginary[2]);
						return new ComplexNumber(real, imaginary);
					}
				}
			}

		} else { // kompleksni broj koji nema imaginarni dio
			try { // ako je umjesto i korišten neki drugi znak, događa se iznimka
				double real = Double.parseDouble(noSpaces);
				return new ComplexNumber(real, 0);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	/**
	 * Metoda za izračun apsolutne vrijednosti kompleksnog broja.
	 * 
	 * @return apsolutna vrijdnost kompleksnog broja
	 */
	public double getMagnitude() {
		if (this.real == 0 && this.imaginary == 0)
			return 0;
		return Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
	}

	/**
	 * Metoda za izračun kuta kompleksnog broja u rasponu [0, 2pi].
	 * 
	 * @throw ArithmeticException kada je imaginarni i realni dio 0
	 * @return kut kompleksnog broja
	 */
	public double getAngle() {

		if(this.imaginary < 0) {
			return Math.atan2(this.imaginary, this.real) + 2 * Math.PI;
		}
		return Math.atan2(this.imaginary, this.real);
	}

	/**
	 * Metoda za zbrajanje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji se pribraja trenutnom
	 * @throws NullPointerException ako je predan null
	 * @return zbroj koji je novi objekt tipa ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber other) {

		if (other == null)
			throw new NullPointerException("Nemoguće zbrojiti s null.");

		return new ComplexNumber(this.real + other.real, this.imaginary + other.imaginary);
	}

	/**
	 * Metoda za oduzimanje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji se oduzima od trenutnog
	 * @throws NullPointerException ako je predan null
	 * @return razlika koja je novi objekt tipa ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber other) {
		if (other == null)
			throw new NullPointerException("Nemoguće oduzeti s null.");

		return new ComplexNumber(this.real - other.real, this.imaginary - other.imaginary);
	}

	/**
	 * Metoda za množenje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji množi trenutno
	 * @throws NullPointerException ako je predan null
	 * @return umnožak koji je novi objekt tipa ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber other) {
		if (other == null)
			throw new NullPointerException("Nemoguće pomnožiti s null.");
		
		double real = this.real * other.real - this.imaginary * other.imaginary;
		double imaginary = this.real * other.imaginary + this.imaginary * other.real;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Metoda za vraćanje kompleksno konjugiranog broja trenutnog broja.
	 * 
	 * @return kompleksno konjugirani broj trenutnog broja
	 */
	public ComplexNumber conjugate() {
		return new ComplexNumber(this.real, -this.imaginary);
	}
	
	/**
	 * Metoda za dijeljenje dva kompleksna broja.
	 * 
	 * @param other kompleksni broj koji dijeli trenutni
	 * @throws NullPointerException ako je predan null
	 * @throws ArithmeticException ako se pokušava podijeliti s 0
	 * @return količnik koji je novi objekt tipa ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber other) {
		if (other == null)
			throw new NullPointerException("Nemoguće pomnožiti s null.");
		if(other.toString().equals("0"))
			throw new ArithmeticException("Nemoguće dijeliti s 0.");
		
		ComplexNumber conjugate = other.conjugate();
		ComplexNumber nominator = this.mul(conjugate);
		ComplexNumber denominator = other.mul(conjugate);
		
		double real = nominator.real / denominator.real;
		double imaginary = nominator.imaginary / denominator.real;
		
		return new ComplexNumber(real, imaginary);
	}
	
	/**
	 * Metoda za potenciranje kompleksnog broja.
	 * 
	 * @param n potencija na koju se diže kompleksni broj
	 * @return novi kompleksni broj dignut na potenciju <code>n</code>
	 */
	public ComplexNumber power(int n) {
		
		if(n == 0) return new ComplexNumber(1, 0);
		if(n == 1) return new ComplexNumber(this.real, this.imaginary);
		
		double magnitute = this.getMagnitude();
		double angle = this.getAngle();
		double magnitudeN = Math.pow(magnitute, n);
		double cos = Math.cos(angle * n);
		double sin = Math.sin(angle * n);
		
		return new ComplexNumber(magnitudeN * cos, magnitudeN * sin);
	}
	
	/**
	 * Metoda za računanje n-tog korijena kompleksnog broja.
	 * 
	 * @param n red korijena koji se računa
	 * @throws IllegalArgumentException ako je argument manji od 1
	 * @return polje korijena veličine <code>n</code>
	 */
	public ComplexNumber[] root(int n) {
		
		if(n < 1) throw new IllegalArgumentException("Kao argument je moguće predati samo prirodne brojeve.");
		
		ComplexNumber[] roots = new ComplexNumber[n];
		
		double angle = this.getAngle();
		double magnitudeN = this.getMagnitude();
		
		magnitudeN = Math.pow(magnitudeN, 1. / n);

		for(int i = 0; i < n; i++) {
			
			double real = magnitudeN * Math.cos((angle + 2 * Math.PI * i) / n);
			double imaginary = magnitudeN * Math.sin((angle + 2 * Math.PI * i) / n);
			
			roots[i] = new ComplexNumber(real, imaginary);
		}
		
		return roots;
	}
	
	public double getReal() {
		return real;
	}

	public double getImaginary() {
		return imaginary;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		if (this.real != 0) {
//			sb.append(String.format("%.2f", this.real));
			sb.append(this.real);
			if (this.imaginary == 0)
				return sb.toString();

			if (this.imaginary > 0) {
				sb.append("+");
			}
		}

		if (this.imaginary != 0) {
			if (this.imaginary == 1)
				sb.append("i");
			else if (this.imaginary == -1)
				sb.append("-i");
			else {
//				sb.append(String.format("%.2f", this.imaginary));
				sb.append(this.imaginary);
				sb.append("i");
			}
		}

		if (this.real == 0 && this.imaginary == 0)
			sb.append(0);

		return sb.toString();
	}
}
