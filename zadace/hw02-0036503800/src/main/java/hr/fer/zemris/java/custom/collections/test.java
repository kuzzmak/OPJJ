package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class test {

	public static void main(String[] args) {

//		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(1, Math.toRadians(30));
//		System.out.println(c);

		String[] numbers = new String[] {"+351", "-351", "3.15", "-3.15", "1+i", "+1 +i", "1 + i", "-1 - i",
				"-1-i", "+1.1i", "-1.1i", "2-1.1i", "+2.1+i", "-12.1-1.9i"};
		
		for(String s: numbers) {
			parse(s);
		}

		
	}

	/**
	 * Funkcija za parsiranje imaginarnog dijela kompleksnog broja 
	 * oblika (i, -i, 2i, 2.1i, -2.1i...)
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
	
	
	
	public static void parse(String s) {

		System.out.println(s);
		// uklonjene sve praznine iz broja
		String noSpaces = s.replaceAll("\\s+", "");

		// imaginarni broj
		if (noSpaces.contains("i")) {

			if (noSpaces.startsWith("+")) {
				noSpaces = noSpaces.replaceFirst("\\+", "");
			}

			// ako ima neki plus nakon potencijalnog micanja prvog plusa
			if (noSpaces.contains("+")) {

				String[] realAndImaginary = noSpaces.split("\\+");

				if (realAndImaginary.length == 2) {

					// ako je prvi dio kompleksnog broja imaginarni dio, npr. "-i + 1"
					if (realAndImaginary[0].contains("i")) {

						double real = Double.parseDouble(realAndImaginary[1]);
						double imaginary = parseImaginary(realAndImaginary[0]);
						
						System.out.println("real: " + real + ", imaginary: " + imaginary);
						
//						return new ComplexNumber(real, imaginary);

					} else { // oblik kompleksnog broja "-1 + i"

						double real = Double.parseDouble(realAndImaginary[0]);
						double imaginary = parseImaginary(realAndImaginary[1]);

						System.out.println("real: " + real + ", imaginary: " + imaginary);
//						return new ComplexNumber(real, imaginary);
					}
				}
			}else {
				
				String[] realAndImaginary = noSpaces.split("-");
				
				// oblik broja 2i, 1.1i
				if(realAndImaginary.length == 1) {
					
					double imaginary = parseImaginary(realAndImaginary[0]);
					
					System.out.println("real: " + 0 + ", imaginary: " + imaginary);
					
				}else if(realAndImaginary.length == 2) {
					
					// oblik broja -2i
					if(realAndImaginary[0].equals("")) { // kada se splita -2i, dobije se ["", 2i]
						double imaginary = -parseImaginary(realAndImaginary[1]);
						
						System.out.println("real: " + 0 + ", imaginary: " + imaginary);
						
					}else {
						
						if (realAndImaginary[0].contains("i")) { // oblik broja i - 1

							double real = -Double.parseDouble(realAndImaginary[1]);
							double imaginary = parseImaginary(realAndImaginary[0]);
							System.out.println("real: " + real + ", imaginary: " + imaginary);
//							return new ComplexNumber(real, imaginary);

						} else { // oblik broja 1 - i

							double real = Double.parseDouble(realAndImaginary[0]);
							double imaginary = -parseImaginary(realAndImaginary[1]);
							System.out.println("real: " + real + ", imaginary: " + imaginary);
//							return new ComplexNumber(real, imaginary);
						}
						
					}
					
				}else { // broj oblika -1 -i, -2.1i - 2
					
					if (realAndImaginary[1].contains("i")) {

						double real = -Double.parseDouble(realAndImaginary[2]);
						double imaginary = -parseImaginary(realAndImaginary[1]);
						System.out.println("real: " + real + ", imaginary: " + imaginary);
//						return new ComplexNumber(real, imaginary);

					} else { 

						double real = -Double.parseDouble(realAndImaginary[1]);
						double imaginary = -parseImaginary(realAndImaginary[2]);
						System.out.println("real: " + real + ", imaginary: " + imaginary);
//						return new ComplexNumber(real, imaginary);
					}
				}
			}

		} else {
			double real = Double.parseDouble(noSpaces);
			System.out.println("real: " + real + ", imaginary: " + 0);
//			return new ComplexNumber(real, 0);
		}
		

	}
}
