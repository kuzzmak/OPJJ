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
		
		Object o1 = value;
		Object o2 = getArgument(withValue);
		
		if (o1 instanceof Integer && o2 instanceof Integer) {
			return Integer.compare((int) o1, (int) o2);
		} else if (o1 instanceof Double && o2 instanceof Integer) {
			return Double.compare((double) o1, (int) o2);
		} else if (o1 instanceof Integer && o2 instanceof Double) {
			return Double.compare((int) o1, (double) o2);
		} else {
			return Double.compare((double) o1, (double) o2);
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
	 * @param arg argument koji se pokušava parsirati
	 * @return parsiran tip
	 * @throws RuntimeException ukoliko predan argument nije parsabilan
	 */
	private Object getArgument(Object arg) {

		try {
			if (arg instanceof String) {

				String arg0 = (String) arg;
				if (arg0.contains(".") || arg0.contains("E")) {
					return Double.parseDouble(arg0);
				}

				return Integer.parseInt(arg0);

			} else if (arg instanceof Integer) {
				return (Integer) arg;

			} else if (arg instanceof Double) {
				return (Double) arg;

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
	 * @param op  operator koji se koristi u operaciji
	 */
	private void execute(Object arg, Operator op) {

		checkArgument(arg);

		Object o1 = value == null ? Integer.valueOf(0) : getArgument(value);
		Object o2 = getArgument(arg);

		if (o1 instanceof Integer && o2 instanceof Integer) {
			value = op.apply((int) o1, (int) o2);
		} else if (o1 instanceof Double && o2 instanceof Integer) {
			value = op.apply((double) o1, (int) o2);
		} else if (o1 instanceof Integer && o2 instanceof Double) {
			value = op.apply((int) o1, (double) o2);
		} else {
			value = op.apply((double) o1, (double) o2);
		}
	}

}