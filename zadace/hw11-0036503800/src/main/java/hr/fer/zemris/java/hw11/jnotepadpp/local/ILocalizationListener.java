package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Sučelje koje opisuje slušaća promjene lokalizacijskih postavaka.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface ILocalizationListener {
	
	/**
	 * Metoda koja se izvodi prilikom promjene lokalizacijskih
	 * postavki, npr. promjena teksta labele, imena akcije... 
	 * 
	 */
	void localizationChanged();
}
