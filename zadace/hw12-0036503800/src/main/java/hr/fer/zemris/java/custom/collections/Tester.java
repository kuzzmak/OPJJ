package hr.fer.zemris.java.custom.collections;

/**
 * Sučelje koje modelira objekte koji primaju neki drugi objekt 
 * i ispitaju je li objekt prihvatljiv ili nije.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface Tester {

	/**
	 * Metoda koja provjerava je li testni objekt prihvatljiv ili ne.
	 * 
	 * @param obj objekt koji se testira
	 * @return istina ako je prihvatljiv, laž inače
	 */
	boolean test(Object obj);
}
