package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Sučelje koje modelira slušaća za dokument tipa {@code SingleDocumentModel}.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface SingleDocumentListener {
	
	/**
	 * Metoda koja se poziva uslijed neke vrste modifikacije dokumenta.
	 * 
	 * @param model dokument na kojem je napravljena modifikacija
	 */
	void documentModifyStatusUpdated(SingleDocumentModel model);
	
	/**
	 * Metoda koja se poziva uslijed promjene staze dokumenta.
	 * 
	 * @param model dokument čija je staza promijenjena
	 */
	void documentFilePathUpdated(SingleDocumentModel model);
}
