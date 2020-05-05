package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Razred koji modelira polinom nad kompleksnim brojevima oblika f(z) = z_n *
 * z^{n} + z_{n-1}*z^{n-1} + ... + z_2 * z^2 + z_1 * z + z_0 gdje su z_0 do z_n
 * koeficijenti koji pišu uz odgovarajuće potencije z.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ComplexPolynomial {

	List<Complex> factors;

	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param factors faktori uz pojedine potencije polinoma
	 */
	public ComplexPolynomial(Complex... factors) {

		this.factors = new ArrayList<>();

		for (Complex c : factors) {
			this.factors.add(c);
		}
	}

	/**
	 * Metoda za dohvat reda polinoma.
	 * 
	 * @return red polinoma
	 */
	public short order() {

		return (short) (factors.size() - 1);
	}

	/**
	 * Metoda za množenje dvaju polinoma.
	 * 
	 * @param p drugi polinom koji množi trenutni
	 * @return umnožak trenutnog polinoma i polnoma {@code p}
	 * @throws NullPointerException ako je predani polinom {@code null}
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		
		if(p == null)
			throw new NullPointerException("Predani polinom ne može biti null.");
		
		int size = this.order() + p.order() + 1;
		
		Complex[] result = new Complex[size];
		Arrays.fill(result, Complex.ZERO);

		for(int i = 0; i < this.factors.size(); i++) {
			for(int j = 0; j < p.factors.size(); j++) {
				
				Complex c1 = this.factors.get(i);
				Complex c2 = p.factors.get(j);
				Complex mul = c1.multiply(c2);
				
				result[i + j] = result[i + j].add(mul);
			}
		}
		
		ComplexPolynomial cp = new ComplexPolynomial(result);
		return cp;
	}

	/**
	 * Metoda za izvršavanje operacije derivacije. Jedan
	 * poziv metoda snižava stupanj polinoma za jedan.
	 * 
	 * @return derivacija trenutnog polinoma
	 */
	public ComplexPolynomial derive() {

		List<Complex> derivedFactors = new ArrayList<>();

		for (int i = 0; i < factors.size(); i++) {

			if (i == 0)
				continue;

			Complex fact = factors.get(i);

			derivedFactors.add(new Complex(fact.getRe() * i, fact.getIm() * i));
		}
		
		ComplexPolynomial cp;
		if(derivedFactors.size() > 0) {
			cp = new ComplexPolynomial(derivedFactors.toArray(new Complex[0]));
		}else {
			cp = new ComplexPolynomial(Complex.ZERO);
		}

		return cp;
	}

	/**
	 * Metoda za izračun vrijednosti polinoma u točki {@code z}.
	 * 
	 * @param z točka u kojoj se računa vrijednost polinoma
	 * @return vrijednost polinoma u točki {@code z}
	 * @throws NullPointerException ako je predani kompleksni broj {@code null}
	 */
	public Complex apply(Complex z) {
		
		if(z == null)
			throw new NullPointerException("Ako je predani kompleksni broj null.");

		Complex result = new Complex(factors.get(0).getRe(), factors.get(0).getIm());

		for (int i = 1; i < factors.size(); i++) {

			result.add(factors.get(i).multiply(z.power(i)));
		}

		return result;
	}

	@Override
	public String toString() {

		if(factors.size() == 1) return factors.get(0).toString();
		
		StringBuilder sb = new StringBuilder();

		int tempOrder = order();
		
		sb.append(factors.get(factors.size() - 1));
		sb.append("*z^");
		sb.append(tempOrder);
		tempOrder--;

		for (int i = factors.size() - 2; i >= 0; i--) {

			if (tempOrder > 0) {

				if (!factors.get(i).equals(Complex.ZERO)) {
					sb.append("+");	
					sb.append(factors.get(i));
					sb.append("*z^");
					sb.append(tempOrder);
				}

				tempOrder--;

			}else {
				
				if (!factors.get(i).equals(Complex.ZERO)) {
					if(sb.length() > 0) sb.append("+");
					sb.append(factors.get(i));
				}
			}
		}
		
		return sb.toString();
	}

}
