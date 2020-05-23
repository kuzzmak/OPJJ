package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Razred koji glumi most između pružatelja prijevoda i
 * objekta koji trebaju prijevode.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LocalizatoniProviderBridge extends AbstractLocalizationProider {

	private boolean connected = false;

	private ILocalizationProvider provider;
	private ILocalizationListener listener;

	/**
	 * Konstruktor.
	 * 
	 * @param provider referenca na pružatelja prijevoda
	 */
	public LocalizatoniProviderBridge(ILocalizationProvider provider) {
		this.provider = provider;
	}

	/**
	 * Metoda koja se poziva prilikom inicijalizacije 
	 * novog prozora kako bi se tome prozoru dodijelio
	 * pružatelj prijevoda.
	 * 
	 */
	public void connect() {

		if (!connected) {
			
			listener = new ILocalizationListener() {

				@Override
				public void localizationChanged() {
					fire();
				}
			};
			
			provider.addLocalizationListener(listener);
			connected = true;
		}
	}

	/**
	 * Metoda koja se poziva prilikom uništavanja prozora
	 * čime se uklanja slušać prijevoda za taj objekt.
	 * 
	 */
	public void disconnect() {

		if(connected) {
			provider.removeLocalizationListener(listener);
			connected = false;
		}
	}

	@Override
	public String getString(String s) {
		return provider.getString(s);
	}

}
