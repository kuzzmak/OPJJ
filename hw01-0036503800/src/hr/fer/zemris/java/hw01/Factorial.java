package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred za izračun faktorijele u rasponu [3, 20]
 * 
 * @author Antonio Kuzminski
 *
 */
public class Factorial {

	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args predani argumenti
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.print("Unesite broj > ");
		
		while (sc.hasNext()) {
			
			String input = sc.next();

			// ako je unos korisnika kraj, izlazi se iz programa
			if (input.toLowerCase().equals("kraj")) {
				System.out.println("Doviđenja");
				break;
			}
			
			try {
				int number = Integer.parseInt(input);
				
				// broj izvan raspona
				if(number < 3 || number > 20) {
					System.out.println(number + " nije u dozvoljenom rasponu.");
				}else {
					// izračun faktorijele
					double fact = factorial(number);
					//System.out.println(String.format("%d! = %.0f", number, fact));
				}
			}catch(IllegalArgumentException e) { // neispravan unos
				
				System.out.println(input + " nije cijeli broj.");
			}
			
			System.out.print("Unesite broj > ");
		}
		sc.close();
	}
	
	/**
	 * Funkcija za izračun faktorijele
	 * 
	 * @param number broj čija se faktorijela računa
	 * @return faktorijela od <code>number</code>
	 */
	private static double factorial(int number) {
		
		double fact = 1;
		
		for(int i = 1; i <= number; i++) {
			fact *= i;
		}
		
		return fact;
	}
}
