package hr.fer.zemris.java.webserver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

/**
 * Implementacija jednostavnog http servera.
 * 
 * @author Antonio Kuzminski
 *
 */
public class SmartHttpServer {

	/** adresa računala */
	private String                       address;
	/** ime domene računala */
	private String                       domainName;
	/** broj porta na kojem se sluša */
	private int                          port;
	/** broj dretvi koje mogu istovremeno usluživati korisnike */
	private int                          workerThreads;
	/** vrijeme života jedne sjednice u sekundama */
	private int                          sessionTimeout;
	/** vrste sadržaja koje se mogu prikazivati */
	private Map<String, String>          mimeTypes = new HashMap<>();
	/** referenca glavne dertve servera */
	private ServerThread                 serverThread;
	/** dretva za gašenje isteklih sjednica */
	private ExpiredSessionsThread        expThread;
	/** bazen dretvi radnika */
	private ExecutorService              threadPool;
	/** staza glavnog direktorija s podatcima servera */
	private Path                         documentRoot;
	/** mapa radnika koji se mogu stvoriti po potrebi */
	private Map<String, IWebWorker>      workersMap = new HashMap<>();
	/** mapa aktivnih sjednica */
	private Map<String, SessionMapEntry> sessions = new HashMap<>();
	/** generator slučajnih brojeva */
	private Random                       sessionRandom = new Random();
	/** zastavica za gašenje glavne dretve servera */
	private boolean                      alive = true;

	/**
	 * Konstruktor.
	 * 
	 * @param configFileName konfiguracijska datoteka servera
	 */
	public SmartHttpServer(String configFileName) {

		try {
			readConfigFile(configFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		serverThread = new ServerThread();
		expThread = new ExpiredSessionsThread();
	}

	/**
	 * Pokretanje dretve servera.
	 * 
	 */
	protected synchronized void start() {
		serverThread.start();
		expThread.start();
		threadPool = Executors.newFixedThreadPool(workerThreads);
	}

	/**
	 * Gašenje dretve servera.
	 * 
	 */
	protected synchronized void stop() {
		alive = false;
		threadPool.shutdown();
		expThread.kill();
	}

	/**
	 * Glavna dretva servera, radi inicijalizaciju porta i prihvaća
	 * nadolazeć promet.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	protected class ServerThread extends Thread {

		@Override
		public void run() {

			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress(InetAddress.getByName(address), port));

				while (alive) {
					Socket csocket = serverSocket.accept();
					ClientWorker cw = new ClientWorker(csocket);
					threadPool.submit(cw);
				}

				serverSocket.close();

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	/**
	 * Dretva koja svakih 5 minuta prolazi mapon aktivnih sessiona i
	 * ukoliko naiđe na neki kojem je vrijeme isteklo, uklanja ga.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private class ExpiredSessionsThread extends Thread {

		private boolean alive = true;
		
		/**
		 * Konstruktor.
		 * 
		 */
		public ExpiredSessionsThread() {
			setDaemon(true);
		}
		
		/**
		 * Gasi dretvu za uklanjanje isteklih sessiona.
		 * 
		 */
		public void kill() {
			alive = false;
		}
		
		@Override
		public void run() {
			
			while(alive) {
				
				Iterator<Map.Entry<String, SessionMapEntry>> iter = sessions.entrySet().iterator();
				while(iter.hasNext()) {
					
					Map.Entry<String, SessionMapEntry> entry = iter.next();
					if(entry.getValue().validUntil < System.currentTimeMillis()) {
						iter.remove();
					}
				}
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
				}
			}
		}
	}

	/**
	 * Predstavlja spremnik podataka jedne sjednice.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private static class SessionMapEntry {

		String sid;
		String host;
		long validUntil;
		Map<String, String> map;

		/**
		 * Konstruktor.
		 * 
		 * @param sid identifikacijski string sjednice
		 * @param host ime računala
		 * @param validUntil vrijeme do kada je sjednica važeća
		 * @param map mapa s podatcima sjednice
		 */
		public SessionMapEntry(String sid, String host, long validUntil, Map<String, String> map) {
			this.sid = sid;
			this.host = host;
			this.validUntil = validUntil;
			this.map = map;
		}
	}

	/**
	 * Uslužna dretva koja obrađuje dolazni zahtjev.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private class ClientWorker implements Runnable, IDispatcher {

		/** priključna točka */
		private Socket              csocket;
		/** ulazni tok podataka */
		private InputStream         istream;
		/** ponor podataka */
		private OutputStream        ostream;
		/** verzija {@code HTTP} */
		private String              version;
		/** vrsta zahtjeva */
		private String              method;
		/** ime računala */
		private String              host;
		/** proslijeđeni parametri */
		private Map<String, String> params = new HashMap<>();
		/** mapa pomoćnih parametara */
		private Map<String, String> tempParams = new HashMap<>();
		/** parametri pojedine sjednice */
		private Map<String, String> persParams = new HashMap<>();
		/** kolačići */
		private List<RCCookie>      outputCookies = new ArrayList<>();
		/** identifikacijski broj sjednice */
		private String              SID;
		/** kontroler za obradu zahtjeva */
		private RequestContext      context = null;
		/** vrijeme u postojanja sjednice u milisekundama */
		private long TIMEOUT = sessionTimeout * 1000;

		/**
		 * Konstruktor.
		 * 
		 * @param csocket priključna točka
		 */
		public ClientWorker(Socket csocket) {
			super();
			this.csocket = csocket;
		}

		@Override
		public void run() {

			try {

				istream = new BufferedInputStream(csocket.getInputStream());
				ostream = new BufferedOutputStream(csocket.getOutputStream());

				byte[] request = readRequest(istream);
				if (request == null) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				String requestStr = new String(request, StandardCharsets.US_ASCII);

				List<String> headers = extractHeaders(requestStr);
				String[] firstLine = headers.isEmpty() ? null : headers.get(0).split(" ");
				if (firstLine == null || firstLine.length != 3) {
					sendError(ostream, 400, "Bad request");
					return;
				}

				method = firstLine[0].toUpperCase();
				if (!method.equals("GET")) {
					sendError(ostream, 405, "Method not allowed");
					return;
				}

				version = firstLine[2].toUpperCase();
				if (!version.equals("HTTP/1.1")) {
					sendError(ostream, 505, "HTTP version not supported");
					return;
				}

				// staza s parametrima
				String path = firstLine[1];

				for (int i = 1; i < headers.size(); i++) {
					if (headers.get(i).contains("Host")) {
						host = headers.get(i).split(":")[1].trim();
					}
				}

				checkSession(headers);

				String[] pathSplitted = path.split("\\?");
				if (pathSplitted.length == 2) {
					String parameters = pathSplitted[1];
					extractParameters(parameters);
				}

				// naziv datoteke
				path = pathSplitted[0];

				try {
					internalDispatchRequest(path, true);
					return;
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					csocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * Metoda za stvaranje nove sjednice.
		 * 
		 */
		private void makeNewSessionEntry() {

			// generiranje session id-ja
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 20; i++) {
				sb.append((char) (sessionRandom.nextInt(26) + 'A'));
			}

			SID = sb.toString();

			Map<String, String> map = new ConcurrentHashMap<>();
			persParams = map;

			SessionMapEntry sme = new SessionMapEntry(SID, host, System.currentTimeMillis() + TIMEOUT,
					map);

			sessions.put(SID, sme);

			outputCookies.add(new RCCookie("sid", SID, null, address, "/", true));
		}

		/**
		 * Provjera zaglavlja zahtjeva sadrži li poznate kolačiće i
		 * postoji li još uvijek aktivna sjednica.
		 * 
		 * @param headers
		 */
		private synchronized void checkSession(List<String> headers) {

			String tmp = null;

			for (String line : headers) {
				if (line.startsWith("Cookie")) {

					String[] cookies = line.replaceAll("Cookie: ", "").split(";");

					for (String c : cookies) {

						String[] splitted = c.split("=");

						String name = splitted[0].strip();
						String value = splitted[1].replaceAll("\"", "").strip();

						if (name.equals("sid")) {
							tmp = value;
						}
					}
				}
			}

			if (tmp != null) {
				// već postoji neki cookie za trenutnog korisnika
				SessionMapEntry entry = sessions.get(tmp);

				if (entry != null) {
					// ako se hostovi poklapaju
					if (host.equals(entry.host)) {

						// ako cookie još nije istekao, ažurira mu se vrijeme života
						if (entry.validUntil >= System.currentTimeMillis()) {
							entry.validUntil = System.currentTimeMillis() + TIMEOUT;
							// dohvat parametara zapamćenog cookie-ja
							persParams = entry.map;
							return;
						}
					}
				}
			}
			// u slučaju da ne postoji cookie, istekao je ili mu se host ne poklapa,
			// stvara se novi
			makeNewSessionEntry();
		}

		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		/**
		 * Obrada zahtjeva prolazi kroz ovu metodu koja može obraditi ili odbiti
		 * zahtjev, ovisno o tome na koji način je pristupljeno određenoj datoteci. Mogu
		 * se izvršavati normalni zahtjeva, npr. učitavanje index stranice, mogu se
		 * obrađivati zahtjevi već predodređenih {@code IWebWorker} radnika i datoteka
		 * koje imaju ekstenziju {@code .smscr}.
		 * 
		 * @param urlPath    staza datoteke koja se izvršava ili učitava
		 * @param directCall je li zahtjev došao izravno iz korisnikove trake
		 * @throws Exception ukoliko je došlo do greške prilikom izvođenja zahtjeva
		 */
		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {

			File f = new File(documentRoot.toString(), urlPath);

			if (context == null) {
				context = new RequestContext(ostream, params, persParams, tempParams, outputCookies, this, SID);
				context.setStatusCode(200);
			}

			// predefinirani workeri
			if (workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);
				return;
			}

			if (!f.exists()) {
				sendError(ostream, 403, "Forbidden");
				return;

			} else {

				if (f.isFile() && f.canRead()) {

					String extension = urlPath.split("\\.")[1];
					
					// nije dozvoljeno izravno pristupati private direktoriju
					if (urlPath.contains("/private"))
						if (directCall)
							sendError(ostream, 404, "Nije dozvoljeno pristupati: " + urlPath);

					// smart script datoteke
					if (extension.equals("smscr")) {

						String documentBody = new String(Files.readAllBytes(Paths.get(f.toString())),
								StandardCharsets.UTF_8);

						if (documentBody != null) {
							new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(), context)
									.execute();
						}
						return;
					}

					String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");
					context.setMimeType(mimeType);
					
					// ostale vrste datoteka
					try (InputStream is = new BufferedInputStream(new FileInputStream(f.toString()))) {

						byte[] buffer = new byte[1024];
						while (true) {
							int read = is.read(buffer);
							if (read < 1)
								break;
							context.write(buffer);
						}
					}

				} else {
					sendError(ostream, 404, "File not readable or doesn't exist");
					return;
				}
			}
		}

		/**
		 * Funkcija za izvlačenje parametara iz stringa.
		 * 
		 * @param parameters string spojenih parametara
		 */
		private void extractParameters(String parameters) {

			String[] splitted = parameters.split("&");

			for (int i = 0; i < splitted.length; i++) {
				String[] s = splitted[i].split("=");
				params.put(s[0], s[1]);
			}
		}

		/**
		 * Metoda za čitanje upita korisnika. Implementirana je tako da iz ulaznog toka
		 * čita oktete sve dok ne pročita ili 0D 0A 0D 0A, ili 0A 0A. Metoda u pomoćni
		 * tok upisuje sve pročitane oktete (osim 0D) i to radi sve dok ne pročita jedan
		 * od navedena dva slijeda .
		 * 
		 * @param is ulazni tok podataka
		 * @return poblje bajtova zahtjeva
		 * @throws IOException ukoliko je došlo do greške prilikom čitanja
		 */
		private byte[] readRequest(InputStream is) throws IOException {

			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int state = 0;
			l: while (true) {
				int b = is.read();
				if (b == -1)
					return null;
				if (b != 13) {
					bos.write(b);
				}
				switch (state) {
				case 0:
					if (b == 13) {
						state = 1;
					} else if (b == 10)
						state = 4;
					break;
				case 1:
					if (b == 10) {
						state = 2;
					} else
						state = 0;
					break;
				case 2:
					if (b == 13) {
						state = 3;
					} else
						state = 0;
					break;
				case 3:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				case 4:
					if (b == 10) {
						break l;
					} else
						state = 0;
					break;
				}
			}
			return bos.toByteArray();
		}
	}

	/**
	 * Čita konfiguracijsku datoteku webservera. Radi inicijalizaciju mime tipova i
	 * {@code IWebWorker} objekata.
	 * 
	 * @param configFileName konfiguracijska datoteka webservera
	 * @throws IOException           ukoliko je došlo do greške prilikom čitanja
	 * @throws FileNotFoundException ukoliko predana datoteka {@code configFileName}
	 *                               ne postoji
	 */
	private void readConfigFile(String configFileName) throws IOException {

		Properties prop = new Properties();

		InputStream is = Files.newInputStream(Paths.get(configFileName));

		if (is != null) {
			prop.load(is);
		} else
			throw new FileNotFoundException("Predana datoteka: " + configFileName + " ne postoji.");

		address = prop.getProperty("server.address");
		domainName = prop.getProperty("server.domainName");
		port = Integer.parseInt(prop.getProperty("server.port"));
		workerThreads = Integer.parseInt(prop.getProperty("server.workerThreads"));
		sessionTimeout = Integer.parseInt(prop.getProperty("session.timeout"));
		documentRoot = Paths.get(prop.getProperty("server.documentRoot"));

		// učitavanje mime tipova
		String mimeConfigurationPath = prop.getProperty("server.mimeConfig");
		readMimeConfigurationFile(mimeConfigurationPath);

		// učitavanje rapspoloživih radnika
		String workersConfigurationPath = prop.getProperty("server.workers");
		readWorkersConfigurationFile(workersConfigurationPath);
	}

	/**
	 * Služi za čitanje mime kongiguracijske datoteke.
	 * 
	 * @param path staza do mime konfiguracijske datoteke
	 */
	private void readMimeConfigurationFile(String path) {

		try {
			Scanner sc = new Scanner(new File(path));
			while (sc.hasNextLine()) {

				String line = sc.nextLine();
				String[] splitted = line.split("=");
				mimeTypes.put(splitted[0].strip(), splitted[1].strip());
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Stvara {@code IWebWorker} objekte navedene u konfiguracijskoj datoteci
	 * {@code workers.properties}.
	 * 
	 * @param filePath staza do konfiguracijske datoteke
	 */
	@SuppressWarnings("deprecation")
	private void readWorkersConfigurationFile(String filePath) {

		try {
			Scanner sc = new Scanner(new File(filePath));
			while (sc.hasNextLine()) {

				String line = sc.nextLine();
				String[] splitted = line.split("=");

				String path = splitted[0].strip();
				String fqcn = splitted[1].strip();

				Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
				Object newObject = referenceToClass.newInstance();
				IWebWorker iww = (IWebWorker) newObject;

				if (workersMap.containsKey(path))
					throw new IllegalArgumentException("Datoteka već sadrži stazu: " + path);

				workersMap.put(path, iww);
			}

		} catch (FileNotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Šalje statusni kod i tekst greške ponoru podataka.
	 * 
	 * @param cos        ponor podataka
	 * @param statusCode satusni kod
	 * @param statusText tekst greške
	 * @throws IOException ukoliko je došlo do greške prilikom zapisa
	 */
	private static void sendError(OutputStream cos, int statusCode, String statusText) throws IOException {

		cos.write(("HTTP/1.1 " + statusCode + " " + statusText + "\r\n" + "Server: simple java server\r\n"
				+ "Content-Type: text/plain;charset=UTF-8\r\n" + "Content-Length: 0\r\n" + "Connection: close\r\n"
				+ "\r\n").getBytes(StandardCharsets.US_ASCII));
		cos.flush();

	}

	/**
	 * Vraća zaglavlje html datoteke u obliku liste.
	 * 
	 * @param requestHeader zaglavlje datoteke u string obliku
	 * @return lista redaka zaglavlja
	 */
	private List<String> extractHeaders(String requestHeader) {
		
		List<String> headers = new ArrayList<String>();
		String currentLine = null;
		
		for (String s : requestHeader.split("\n")) {
			if (s.isEmpty())
				break;
			char c = s.charAt(0);
			if (c == 9 || c == 32) {
				currentLine += s;
			} else {
				if (currentLine != null) {
					headers.add(currentLine);
				}
				currentLine = s;
			}
		}
		if (!currentLine.isEmpty()) {
			headers.add(currentLine);
		}
		return headers;
	}

	/**
	 * Metoda iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args argumenti glavnog programa
	 */
	public static void main(String[] args) {
		
		if(args.length != 1) {
			System.out.println("Krivi broj argumenata. Potreban samo jedan: staza konfiguracijske datoteke servera.");
			System.exit(-1);
		}
		
		String confPath = args[0];
		new SmartHttpServer(confPath).start();
	}
}
