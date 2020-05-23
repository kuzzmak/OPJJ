package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Konkretna implementacija pružatelja lokalizacijskih naziva koji
 * je napravljen prema oblikovnom obrazcu jedinstveni objekt. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class LocalizationProvider extends AbstractLocalizationProider {

	private String language;
	// objekt za dovat prijevoda izraza
	private ResourceBundle bundle;
	private static final LocalizationProvider instance = new LocalizationProvider();
	
	/**
	 * Konstruktor.
	 * 
	 */
	private LocalizationProvider() {
		// pretpostavljeni jezik je engleski
		language = "en";
		Locale locale = Locale.forLanguageTag(language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.local.prijevodi", locale);
	}
	
	/**
	 * Metoda za dohvat instance pružatelja lokalizacijskih naziva.
	 * 
	 * @return objekt tipa {@code LocalizationProvider}
	 */
	public static LocalizationProvider getInstance() {
		return instance;
	}
	
	@Override
	public String getString(String s) {
		return bundle.getString(s);
	}

	/**
	 * Metoda za postavljanje novog jezika i ažuriranje naziva.
	 * 
	 * @param language novi jezik koji se postavlja
	 */
	public void setLanguage(String language) {
		this.language = language;
		Locale locale = Locale.forLanguageTag(this.language);
		bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw08.vjezba.prijevodi", locale);
		fire();
	}
	
	public String getLanguage() {
		return language;
	}
	
}
