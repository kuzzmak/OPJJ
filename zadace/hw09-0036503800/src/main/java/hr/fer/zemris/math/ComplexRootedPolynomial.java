package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred koji predstavlja polinom nad kompleksnim brojevima
 * oblika f(z) = z0 * (z - z1) * (z - z2) * ... * (z - zn)
 * gdje z0 predstavlja konstantu, z1 do zn nultočke polinoma.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ComplexRootedPolynomial {

	private Complex constant;
	private List<Complex> roots;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param constant kompleksni broj koji predstavlja konstantu
	 * @param roots kompleksni brojevi koji predstavljaju korijene
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ... roots) {
		
		this.constant = constant;
		this.roots = new ArrayList<>();
		
		for(Complex c: roots) {
			this.roots.add(c);
		}
	}

	/**
	 * Metoda za izračunavanje vrijednosti polinoma u točki {@code z}.
	 * 
	 * @param z točka u kojoj se izračunava vrijednost polinoma
	 * @return vrijednost polinoma
	 * @throws NullPointerException ako je predan {@code null} u funkciju
	 */
	public Complex apply(Complex z) {
		
		if(z == null)
			throw new NullPointerException("Nije moguće predati null.");
		
		Complex result = new Complex(constant.getRe(), constant.getIm());
		
		for(Complex root: roots) {
			result.multiply(z.sub(root));
		}
		
		return result;
	}
	
	/**
	 * Metoda za pretvorbu polinoma iz oblika {@code ComplexRootedPolynomial} 
	 * u {@code ComplexPolynomial}.
	 * 
	 * @return polinom u obliku {@code ComplexPolynomial}
	 */
	public ComplexPolynomial toComplexPolyNomial() {
		
		if(roots.size() == 1) {
			return new ComplexPolynomial(
					constant.multiply(roots.get(0).negate()),
					constant);
		}
		
		ComplexPolynomial cp = new ComplexPolynomial(roots.get(0).negate(), Complex.ONE);
		
		for(int i = 1; i < roots.size(); i++) {
			cp = cp.multiply(new ComplexPolynomial(roots.get(i).negate(), Complex.ONE));
		}
		
		cp = cp.multiply(new ComplexPolynomial(constant));
		
		return cp;
	}
	
	/**
	 * Metoda za dohvat indeksa najbližeg korijena kompleksnog broja {@code z}
	 * koji se nalazi unutar intervala tolerancije [-{@code treshold}, +{@code treshold}].
	 * 
	 * @param z broj koji se uspoređuje s korijenima
	 * @param treshold tolerancija 
	 * @return indeks korijena, -1 ako ne postoji takav korijen
	 * @throws NullPointerException ako je predan {@code null} u funkciju na mjestu kompleksnog broja
	 */
	public int indexOfClosestRootFor(Complex z, double treshold) {
		
		if(z == null)
			throw new NullPointerException("Nije moguće predati null.");
		
		for(int i = 0; i < roots.size(); i++) {
		
			Complex root = roots.get(i);
			
			if(Math.abs(root.getRe() - z.getRe()) <= treshold && 
					Math.abs(root.getIm() - z.getIm()) <= treshold) return i;
		}
		
		return -1;
	}

	@Override
	public String toString() {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(constant);
		
		for(Complex root: roots) {
			sb.append("*(z-");
			sb.append(root);
			sb.append(")");
		}

		return sb.toString();
	}
	
}
