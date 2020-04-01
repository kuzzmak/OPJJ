package hr.fer.zemris.java.hw02;
import java.lang.Math;

public class ComplexNumber {
	
	private double real;
	private double imaginary;
	
	/**
	 * Inicijalni konstruktor koji prima realni i imaginarni dio kompleksnog broja.
	 * 
	 * @param real realni dio kompleksnog broja
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
	 * Metoda za dobivanje kompleksnog broja iz poznate apsolutne 
	 * vrijednosti i kuta u radijanima.
	 * 
	 * @param magnitude apsolutna vrijednost kompleksnog broja
	 * @param angle kut između realnog i imaginarnog dijela broja
	 * @return objekt tipa ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}

	public static ComplexNumber parse(String s) {
		
		double c = Double.parseDouble(s);
		System.out.println(c);
		
		return new ComplexNumber(0, 0);
	}
	
	
	
	
	
	@Override
	public String toString() {
		return "ComplexNumber [real=" + real + ", imaginary=" + imaginary + "]";
	}
}
