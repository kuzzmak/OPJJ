package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje koje modelira objekt koji ima mogućnost obraditi predan objekt na neki način
 * specificaran metodom process.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface Processor {

	/**
	 * Metoda koja obavlja nekakvu zadaću nad predanim objektom.
	 * 
	 * @param value objekt nad kojim se izvršava neka zadaća
	 * @throws NullPointerException ako je predana vrijednost <code>null</code>
	 */
	public void process(Object value);
}
