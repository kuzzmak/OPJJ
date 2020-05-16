package hr.fer.zemris.java.gui.layouts;

/**
 * Razred iznimke koji se poziva ukoliko je došlo do kakve greške prilikom
 * korištenja kalkulatora.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CalcLayoutException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CalcLayoutException() {
		super();
	}
	
	public CalcLayoutException(String s) {
		super(s);
	}
}
