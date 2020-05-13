package hr.fer.zemris.java.gui.layouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Razred koji predstavlja jednu poziciju u {@code CalcLayout} manageru.
 * 
 * @author kuzmi
 * @see CalcLayout
 *
 */
public class RCPosition {

	private int row;
	private int column;
	// prvi indeks 
	public static RCPosition FIRST = new RCPosition(1, 1);
	// nedozvoljeni indeksi jer prvi redak zauzima 5 pozicija prva ćelija
	public static List<RCPosition> invalidPositions = new ArrayList<>(Arrays.asList(new RCPosition(1, 2),
			new RCPosition(1, 3), new RCPosition(1, 4), new RCPosition(1, 5)));

	/**
	 * Konstruktor.
	 * 
	 * @param row redak u koji se želi ubaciti novu komponentu
	 * @param column stupac u koji se želi ubaciti novu komponentu
	 */
	public RCPosition(int row, int column) {
		super();
		this.row = row;
		this.column = column;
	}

	/**
	 * Metoda za parsiranje stringa koji predstavlja redak i stupac smještaja nove
	 * komponente.
	 * 
	 * @param text string oblika "1,2"
	 * @return objekt tipa {@code RCPosition} koji predstavlja poziciju u
	 *         {@code CalcLayout} manageru
	 */
	public static RCPosition parse(String text) {

		String noSpaces = text.replaceAll("//s+", "");

		String[] splitted = noSpaces.split(",");

		try {
			int row = Integer.parseInt(splitted[0]);
			int column = Integer.parseInt(splitted[1]);
			
			return new RCPosition(row, column);
			
		} catch (NumberFormatException | IndexOutOfBoundsException e) {
			throw new CalcLayoutException("Krivi unos pozicije.");
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return "(" + row + "," + column + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
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
		RCPosition other = (RCPosition) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
	
}
