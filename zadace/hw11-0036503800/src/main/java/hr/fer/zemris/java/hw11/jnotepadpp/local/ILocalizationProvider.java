package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Sučelje koje modelira objekt za dohvat lokaliziranih naziva
 * pojedinih imena labela, akcija i dr.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface ILocalizationProvider {

	/**
	 * Metoda za registraciju novog slušaća promjene lokalizacije.
	 * 
	 * @param l slušać koji se dodaje
	 */
	void addLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda za uklanjanje postojećeg slušaća promjene lokalizacijskih
	 * postavki.
	 * 
	 * @param l slušać koji se uklanja
	 */
	void removeLocalizationListener(ILocalizationListener l);
	
	/**
	 * Metoda za dohvat lokaliziranog naziva.
	 * 
	 * @param s ključ prema kojem se dohvaća lokalizirani naziv
	 * @return lokalizirani naziv
	 */
	String getString(String s);
}
