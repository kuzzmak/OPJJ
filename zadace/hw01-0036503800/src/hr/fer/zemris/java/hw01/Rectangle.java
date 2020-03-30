package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Razred za unos širine i visine pravokutnika te izračun površine i opsega.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Rectangle {

	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args predani argumenti
	 */
	public static void main(String[] args) {
		
		// predani argumenti preko naredbenog retka
		if(args.length > 0) {
			
			// moraju biti predana točno dva argumenta
			if(args.length != 2) {
				
				System.out.printf("Predan pogrešan broj argmenata. [%d] umjesto 2", args.length);
				System.exit(1);
			}else {
				
				try {
					// parsiranje širine i visine pravokutnika
					double width = Double.parseDouble(args[0]);
					double height = Double.parseDouble(args[1]);
					
					// ako je unesena negativna širina ili visina, izlazi se iz programa
					if(width < 0 || height < 0) {
						System.out.println("Širina ili visina pravokutnika ne mogu biti negativne.");
						System.exit(1);
					}
					
					// ispis poruke
					System.out.println(messageString(width, height));
					
				}catch(IllegalArgumentException e) { // argumente nije moguce parsirati jer nisu brojevi
					
					System.out.println("Predani su argumenti koji nisu brojevi.");
					System.exit(1);
				}
			}
		}else { // nisu predani nikakvi argumenti, pita se korisnika
			
			Scanner sc = new Scanner(System.in);
			
			// parsiranje širine i visine pravokutnika
			double width = parse(sc, "širinu");
			double height = parse(sc, "visinu");
			
			System.out.println(messageString(width, height));
		}
	}
	
	/**
	 * Funkcija za parsiranje pojedine stranice pravokutnika. 
	 * 
	 * @param sc referenca Scanner razreda koja služi za čitanje pojedinog reda
	 * @param s string koji služi za ispis odgovarajuće poruke prilikom unosa
	 * @return double vrijednost unesene stranice
	 */
	private static double parse(Scanner sc, String s) {
		
		System.out.print("Unesite " + s + " > ");
		
		while(sc.hasNextLine()) {
			
			String line = sc.nextLine();
			
			try {
				double value = Double.parseDouble(line.trim());
				
				// ako je vrijednost negativna
				if(value < 0) {
					System.out.println("Unijeli ste negativnu vrijednost.");
					System.out.print("Unesite " + s + " > ");
				}else {	
					return value;
				}
				
			}catch(IllegalArgumentException e) {
				
				System.out.println(line.trim() + " se ne može protumačiti kao broj.");
				System.out.print("Unesite " + s + " > ");
			}
		}
		
		return -1;
	}
	
	/**
	 * Funkcija za izračun površine pravokutnika. 
	 * 
	 * @param width širina pravokutnika
	 * @param height visina pravokutnika
	 * @return površina pravokutnika
	 */
	private static double area(double width, double height) {
		return width * height;
	}
	
	/**
	 * Funkcija za izračun opsega pravokutnika.
	 * 
	 * @param width širina pravokutnika
	 * @param height visina pravokutnika
	 * @return opseg pravokutnika
	 */
	private static double perimeter(double width, double height) {
		return 2 * width + 2 * height;
	}
	
	/**
	 * Funkcija za ispis površine i opsega pravokutnika.
	 * 
	 * @param width širina pravokutnika
	 * @param height visina pravokutnika
	 * @return string
	 */
	private static String messageString(double width, double height) {
		StringBuilder sb = new StringBuilder();
		sb.append("Pravokutnik širine ");
		sb.append(width);
		sb.append(" i visine ");
		sb.append(height);
		sb.append(" ima površinu ");
		// ograničavanje na dva najznačajnija mjesta iza decimalne točke
		sb.append(String.format("%.2f", area(width, height)));	
		sb.append(" te opseg ");
		sb.append(String.format("%.2f", perimeter(width, height)));
		sb.append(".");
		return sb.toString();
	}
}
