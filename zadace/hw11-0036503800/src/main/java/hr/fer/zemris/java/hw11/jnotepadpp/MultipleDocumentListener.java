package hr.fer.zemris.java.hw11.jnotepadpp;

/**
 * Sučelje koje modelira slušaća razreda {@code MultipleDocumentModel}.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface MultipleDocumentListener {

	/**
	 * Metoda koja se poziva prilikom nastalih promjena na dokumentu.
	 * 
	 * @param previousModel prijašnje stanje dokumenta
	 * @param currentModel trenutno stanje dokumenta
	 */
	void currentDocumentChanged(SingleDocumentModel previousModel, SingleDocumentModel currentModel);
	
	/**
	 * Metoda koja se poziva prilikom otvaranja ili stvaranja novog dokumenta.
	 * 
	 * @param model dokument koji je dodan 
	 */
	void documentAdded(SingleDocumentModel model);
	
	/**
	 * Metoda za obavještavanje pretplaćenih slušaća da je uklonjen dokument.
	 * 
	 * @param model dokument koji je uklonjen
	 */
	void documentRemoved(SingleDocumentModel model);
}
