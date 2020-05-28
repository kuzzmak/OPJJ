package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class RequestContext {

	/**
	 * Interni razred koji predstavlja kolačić.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	public static class RCCookie {

		private final String name;
		private final String value;
		private final String domain;
		private final String path;
		private Integer maxAge;

		/**
		 * Konstruktor.
		 * 
		 * @param name
		 * @param value
		 * @param maxAge
		 * @param domain
		 * @param path
		 */
		public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
			this.name = name;
			this.value = value;
			this.domain = domain;
			this.path = path;
			this.maxAge = maxAge;
		}

	}

	private OutputStream outputStream;
	private Charset charset;
	public String encoding;
	public int statusCode;
	public String statusText;
	public String mimeType;
	public Long contentLength;

	private final Map<String, String> parameters;
	private Map<String, String> temporaryParameters;
	private Map<String, String> persistentParameters;
	private List<RCCookie> outputCookies;
	private boolean headerGenerated;

	/**
	 * Konstruktor.
	 * 
	 * @param outputStream ponor podataka koji se koristi za zapis, ne smije biti {@code null}
	 * @param parameters 
	 * @param persistentParameters
	 * @param ouputCookies kolačići
	 */
	public RequestContext(OutputStream outputStream, Map<String, String> parameters,
			Map<String, String> persistentParameters, List<RCCookie> outputCookies) {

		Objects.requireNonNull(outputStream);

		this.outputStream = outputStream;
		this.parameters = parameters == null ? new HashMap<>() : parameters;
		this.persistentParameters = persistentParameters == null ? new HashMap<>() : persistentParameters;
		this.temporaryParameters = new HashMap<>();
		this.outputCookies = outputCookies == null ? new ArrayList<>() : outputCookies;

		encoding = "UTF-8";
		statusCode = 200;
		statusText = "OK";
		mimeType = "text/html";
	}

	/**
	 * Metoda za dohvat vrijednosti iz mape parametara.
	 * 
	 * @param name ključ pod kojim se nalazi parametar
	 * @return vrijednost pod ključem {@code name}, {@code null} ako ključ ne
	 *         postoji
	 */
	public String getParameter(String name) {
		return parameters.getOrDefault(name, null);
	}

	/**
	 * Metoda za dohvat ključeva mape parametara.
	 * 
	 * @return skup ključeva
	 */
	public Set<String> getParameterNames() {
		return parameters.keySet();
	}

	/**
	 * Metoda za dohvat vrijednosti iz mape {@code persistentParameters}.
	 * 
	 * @param name ključ pod kojim se nalazi parametar
	 * @return vrijednost pod ključem {@code name}, {@code null} ako ključ ne
	 *         postoji
	 */
	public String getPersistentParameter(String name) {
		return persistentParameters.getOrDefault(name, null);
	}

	/**
	 * Metoda za dohvat ključeva mape {@code persistentParameters}.
	 * 
	 * @return skup ključeva
	 */
	public Set<String> getPersistentParameterNames() {
		return persistentParameters.keySet();
	}

	/**
	 * Metoda za stavljanje vrijednosti u mapu {@code persistentParameters}.
	 * 
	 * @param name  ključ pod kojim se stavlja vrijednost {@code value}
	 * @param value vrijednost koja se stavlja pod ključem {@code name}
	 */
	public void setPersistentParameter(String name, String value) {
		persistentParameters.put(name, value);
	}

	/**
	 * Metoda za brisanje para ključ, vrijednost iz mape
	 * {@code persistentParameters}.
	 * 
	 * @param name ključ pod kojim se briše par
	 */
	public void removePersistentParameter(String name) {
		persistentParameters.remove(name);
	}

	/**
	 * Metoda za dohvat vrijednosti iz mape {@code temporaryParameters}.
	 * 
	 * @param name ključ pod kojim se dohvaća vrijednost
	 * @return vrijednost pod ključem {@code name}
	 */
	public String getTemporaryParameter(String name) {
		return temporaryParameters.getOrDefault(name, null);
	}

	/**
	 * Metoda za dohvat ključeva mape {@code temporaryParameters}.
	 * 
	 * @return skup ključeva
	 */
	public Set<String> getTemporaryParameterNames() {
		return temporaryParameters.keySet();
	}

	/**
	 * Metoda za dohvat jedinstvenog identifikatora web sjednice korisnika.
	 * 
	 * @return identifikator web sjednice
	 */
	public String getSessionID() {
		return "";
	}

	/**
	 * Metoda za upis nove vrijednosti pod ključem {@code name} u mapu
	 * {@code temporaryParameters}.
	 * 
	 * @param name  ključ pod kojim se upisuje vrijednost {@code value}
	 * @param value vrijednost koja se upisuje pod ključem {@code name}
	 */
	public void setTemporaryParameter(String name, String value) {
		temporaryParameters.put(name, value);
	}

	/**
	 * Metoda za brisanje vrijednosti pod ključem {@code name} iz mape
	 * {@code temporaryParameters}.
	 * 
	 * @param name ključ pod kojim se briše vrijednost
	 */
	public void removeTemporaryParameter(String name) {
		temporaryParameters.remove(name);
	}

	/**
	 * Metoda za zapis polja bajtova u {@code outputStream}.
	 * 
	 * @param data podatci koji se zapisuju
	 * @return objekt tipa {@code RequestContext}
	 * @throws IOException ukoliko je došlo do greške prilikom zapisa
	 */
	public RequestContext write(byte[] data) throws IOException {
		return write(data, 0, data.length);
	}

	/**
	 * Metoda za zapis polja bajtova u {@code outputStream}.
	 * 
	 * @param data podatci koji se zapisuju
	 * @param offset pomak od nulte pozicije
	 * @param len diljina podataka koja se zapisuje
	 * @return objekt tipa {@code RequestContext}
	 * @throws IOException ukoliko je došlo do greške prilikom zapisa
	 */
	public RequestContext write(byte[] data, int offset, int len) throws IOException {

		// ukoliko nije stvoreno zaglavlje, stvara se
		if (!headerGenerated) {

			StringBuilder header = new StringBuilder();

			Charset headerEncoding = StandardCharsets.ISO_8859_1;

			// prvi red
			header.append("HTTP/1.1 ").append(statusCode).append(" ").append(statusText).append(" ").append("\r\n");

			// drugi red
			header.append("Content-type: ").append(mimeType);
			if (mimeType.startsWith("text/")) {
				header.append("; charset=").append(charset);
			}
			header.append("\r\n");

			// treći red
			if (contentLength != null) {
				header.append("Content-length: ").append(contentLength);
			}

			// četvrti red
			if (outputCookies.size() != 0) {
				for (RCCookie c : outputCookies) {
					header.append("Set-Cookie: ");
					header.append(c.name).append("=").append("\"").append(c.value).append("\"");
					if (c.domain != null) {
						header.append("; Domain=").append(c.domain);
					}
					if (c.path != null) {
						header.append("; Path=").append(c.path);
					}
					if (c.maxAge != null) {
						header.append("; Max-Age=").append(c.maxAge);
					}
					header.append("\r\n");
				}
			}

			header.append("\r\n");

			outputStream.write(headerEncoding.encode(header.toString()).array());
			
			headerGenerated = true;
		}

		outputStream.write(data, offset, len);

		return this;
	}

	/**
	 * Metoda za zapis teksta {@code text} u {@code outputStream}.
	 * 
	 * @param text tekst koji se zapisuje
	 * @return objekt tipa {@code RequestContext}
	 * @throws IOException ukoliko je došlo do greške prilikom zapisa
	 */
	public RequestContext write(String text) throws IOException {
		
		if(!headerGenerated) {
			charset = Charset.forName(encoding);
		}
		
		return write(charset.encode(text).array());
	}

	/**
	 * Metoda za postavljanje kodne stranice.
	 * 
	 * @param encoding string kodne stranice koja se postavlja
	 * @throws RuntimeException ukoliko je već generirano zaglavlje
	 */
	public void setEncoding(String encoding) {
		if (!headerGenerated)
			this.encoding = encoding;
		else
			throw new RuntimeException("Nije moguće postaviti kodnu stranicu jednom kada je generirano zaglavlje.");
	}

	/**
	 * Metoda za postavljanje formata datoteke.
	 * 
	 * @param string vrsta formata
	 * @throws RuntimeException ukoliko je već generirano zaglavlje
	 */
	public void setMimeType(String mimeType) {
		if (!headerGenerated)
			this.mimeType = mimeType;
		else
			throw new RuntimeException("Nije moguće postaviti mimeType jednom kada je generirano zaglavlje.");
	}

	/**
	 * Metoda za postavljanje statusnog koda.
	 * 
	 * @param statusCode kod koji se postavlja
	 * @throws RuntimeException ukoliko je već generirano zaglavlje
	 */
	public void setStatusCode(int statusCode) {
		if (!headerGenerated)
			this.statusCode = statusCode;
		else
			throw new RuntimeException("Nije moguće postaviti statusni kod jednom kada je generirano zaglavlje.");
	}
	
	/**
	 * Metoda za postavljanje statusnog tekst.
	 * 
	 * @param statusText statusni tekst koji se postavlja
	 * @throws RuntimeException ukoliko je zaglavlje već genrirano
	 */
	public void setStatusText(String statusText) {
		if(!headerGenerated) 
			this.statusText = statusText;
		else
			throw new RuntimeException("Nije moguće postaviti statusni tekst jednom kada je generirano zaglavlje.");
	}
	
	/**
	 * Metoda za postavljanje veličine poruke.
	 * 
	 * @param contentLength veličina poruke koja se postavlja
	 * @throws RuntimeException ukoliko je zaglavlje već generirano
	 */
	public void setContentLength(Long contentLength) {
		if(!headerGenerated) 
			this.contentLength = contentLength;
		else
			throw new RuntimeException("Nije moguće postaviti duljinu poruke jednom kada je generirano zaglavlje.");
	}
	
	/**
	 * Metoda za dodavanje kolačića.
	 * 
	 * @param cookie kolačić koji se dodaje
	 * @throws RuntimeException ukoliko je zaglavlje već genrirano
	 */
	public void addRCCookie(RCCookie cookie) {
		if(!headerGenerated) 
			outputCookies.add(cookie);
		else
			throw new RuntimeException("Nije moguće dodavati kolačiće jednom kada je generirano zaglavlje.");
	}
}
