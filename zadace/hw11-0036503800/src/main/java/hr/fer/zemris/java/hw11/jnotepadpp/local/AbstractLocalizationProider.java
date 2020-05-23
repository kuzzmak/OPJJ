package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Apstraktni razred  pružatelja prijevoda koji implementira 
 * svu funkcionalnost osim dohvata prijevoda.
 * 
 * @author Antonio Kuzminski
 *
 */
public abstract class AbstractLocalizationProider implements ILocalizationProvider {

	private List<ILocalizationListener> listeners;
	
	/**
	 * Konstruktor.
	 * 
	 */
	public AbstractLocalizationProider() {
		listeners = new ArrayList<>();
	}
	
	@Override
	public void addLocalizationListener(ILocalizationListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeLocalizationListener(ILocalizationListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Metoda za obavijest pretplaćenih slušaća prijevoda da je
	 * došlo do promjene prijevoda.
	 * 
	 */
	public void fire() {
		listeners.forEach(ILocalizationListener::localizationChanged);
	}
	
}
