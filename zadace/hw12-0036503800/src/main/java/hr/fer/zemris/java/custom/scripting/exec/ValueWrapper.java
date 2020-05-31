package hr.fer.zemris.java.custom.scripting.exec;

/**
 * Razred koji predstavlja strukturu podatka omotača oko 
 * bilo koje druge vrste podatka. Moguće je izvoditi osnovne
 * artimetičke operacije i operaciju usporedbe. Aritmetičke 
 * operacije su samo moguće nad objektima tipa {@code Integer},
 * {@code Double} i {@code null} koji se tretira kao {@code Integer.valueOf(0)}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ValueWrapper {

	private Object value;

	/**
	 * Konstruktor.
	 * 
	 * @param value inicijalna vrijednost
	 */
	public ValueWrapper(Object value) {
		this.value = value;
	}

	/**
	 * Metoda za zbrajanje vrijednosti {@code incValue} interno zapamćenoj
	 * vrijednosti.
	 * 
	 * @param incValue vrijednost koja se pribraja
	 */
	public void add(Object incValue) {
		execute(incValue, Operator.ADD);
	}

	/**
	 * Metoda za oduzimanje vrijednosti {@code decValue} od interno zapamćene
	 * vrijednosti.
	 * 
	 * @param decValue vrijednost koja se oduzima
	 */
	public void subtract(Object decValue) {
		execute(decValue, Operator.SUB);
	}

	/**
	 * Metoda za množenje vrijednosti {@code mulValue} s interno zapamćenom
	 * vrijednosti.
	 * 
	 * @param mulValue vrijednost koja množi
	 */
	public void multiply(Object mulValue) {
		execute(mulValue, Operator.MUL);
	}

	/**
	 * Metoda koja dijeli interno zapamćenu vrijednost vrijednošću {@code divValue}.
	 * 
	 * @param divValue vrijednost koja dijeli
	 */
	public void divide(Object divValue) {
		execute(divValue, Operator.DIV);
	}

	/**
	 * Metoda za usporedbu interne vrijednosti s vrijednošću {@code withValue}
	 * 
	 * @param withValue vrijednost koja se uspoređuje s interno zapamćenom
	 *                  vrijednošću
	 * @return -1 ako je interna vrijednost manja od {@code withValue}, 1 ako je
	 *         veća, a 0 ako su jednake
	 */
	public int numCompare(Object withValue) {
		
		checkArgument(withValue);
		
		Number n1 = getArgument(value);
		Number n2 = getArgument(withValue);
		
		if (n1 instanceof Integer && n2 instanceof Integer) {
			return Integer.compare(n1.intValue(), n2.intValue());
		} else {
			return Double.compare(n1.doubleValue(), n2.doubleValue());
		}
	}

	/**
	 * Metoda za dohvat interno zapamćene vrijednosti.
	 * 
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Metoda za postavljanje trenutne vrijednosti.
	 * 
	 * @param value
	 */
	public void setValue(Object value) {
		this.value = value;
	}

	/**
	 * Metoda za provjeru tipa predanog argumenta. Ukoliko je predana kriva vrsta
	 * (bilo što osim String, Integer, Double ili null), baca se greška
	 * {@code RuntimeException}.
	 * 
	 * @param arg argument za koji se provjerava ispravnost tipa
	 * @throws RuntimeException ukoliko je predna pogršan tip argumenta
	 */
	private void checkArgument(Object arg) {

		boolean ok = arg == null || arg instanceof String || arg instanceof Integer || arg instanceof Double;

		if (!ok)
			throw new RuntimeException("Argument krivog tipa: " + String.valueOf(arg.getClass()));
	}

	/**
	 * Metoda za parsiranje predanog objekta {@code arg} u ispravni tip objekta.
	 * Ukoliko je predani string s točkom ili slovom E, pretvara se u tip
	 * {@code Double}, inače se pokušava parsirati u {@code Integer}.
	 * 
	 * @param o argument koji se pokušava parsirati
	 * @return parsiran tip
	 * @throws RuntimeException ukoliko predan argument nije parsabilan
	 */
	private Number getArgument(Object o) {

		try {
			if (o instanceof String) {

				String s = (String) o;
				if (s.contains(".") || s.contains("E")) {
					return Double.parseDouble(s);
				}

				return Integer.parseInt(s);

			} else if (o instanceof Integer) {
				return (Integer) o;

			} else if (o instanceof Double) {
				return (Double) o;

			} else
				return Integer.valueOf(0);

		} catch (Exception e) {
			throw new RuntimeException("Predana kriva vrsta argumenta.");
		}
	}

	/**
	 * Metoda za izvršavanje operacije.
	 * 
	 * @param arg drugi argument operacije
	 * @param op operator koji se koristi u operaciji
	 */
	private void execute(Object arg, Operator op) {

		checkArgument(arg);

		Number n1 = getArgument(value);
		Number n2 = getArgument(arg);

		if (n1 instanceof Integer && n2 instanceof Integer) {
			value = op.apply(n1.intValue(), n2.intValue());
		} else {
			value = op.apply(n1.doubleValue(), n2.doubleValue());
		}
	}

}
