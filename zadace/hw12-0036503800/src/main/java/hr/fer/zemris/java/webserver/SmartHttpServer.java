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
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext.RCCookie;

public class SmartHttpServer {

	private String address;
	private String domainName;
	private int port;
	private int workerThreads;
	private int sessionTimeout;
	private Map<String, String> mimeTypes = new HashMap<>();
	private ServerThread serverThread;
	private ExecutorService threadPool;
	private Path documentRoot;
	private Map<String, IWebWorker> workersMap = new HashMap<>();

	public SmartHttpServer(String configFileName) {

		try {
			readConfigFile(configFileName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		serverThread = new ServerThread();
	}

	protected synchronized void start() {
		serverThread.start();
	}

	protected synchronized void stop() {

	}

	protected class ServerThread extends Thread {

		@Override
		public void run() {

			try {
				ServerSocket serverSocket = new ServerSocket();
				serverSocket.bind(new InetSocketAddress((InetAddress) null, port));

				while (true) {
					Socket csocket = serverSocket.accept();
					new ClientWorker(csocket).run();
				}
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Socket csocket = new Socket();
			try {
				csocket.bind(new InetSocketAddress(port));
			} catch (IOException e) {
				e.printStackTrace();
			}
			new ClientWorker(csocket).run();
		}

	}

	private class ClientWorker implements Runnable, IDispatcher {

		private Socket csocket;
		private InputStream istream;
		private OutputStream ostream;
		private String version;
		private String method;
		private String host;
		private Map<String, String> params = new HashMap<>();
		private Map<String, String> tempParams = new HashMap<>();
		private Map<String, String> persParams = new HashMap<>();
		private List<RCCookie> outputCookies = new ArrayList<>();
		private String SID;
		private RequestContext context = null;

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
				
				File f = new File(documentRoot.toString(), path);
				String extension = path.split("\\.")[1];
				String mimeType = mimeTypes.getOrDefault(extension, "application/octet-stream");
				
				if (!f.exists()) {
					sendError(ostream, 403, "Forbidden");
					return;
					
				}else {
					
					if (f.isFile() && f.canRead()) {
						
						// smart script datoteka
						if (extension.equals("smscr")) {

							context = new RequestContext(ostream, params, persParams, tempParams, outputCookies, this);
							context.setMimeType(mimeType);
							context.setStatusCode(200);

							String documentBody = new String(Files.readAllBytes(Paths.get(f.toString())),
									StandardCharsets.UTF_8);

							if (documentBody != null) {
								new SmartScriptEngine(new SmartScriptParser(documentBody).getDocmentNode(), context)
										.execute();
							}
							return;

						}else { // neka druga datoteka
						
							try (InputStream is = new BufferedInputStream(new FileInputStream(f.toString()))) {

								byte[] buffer = new byte[1024];
								while (true) {
									int read = is.read(buffer);
									if (read < 1)
										break;
									context.write(buffer);
								}
							}
						}

					} else {
						sendError(ostream, 404, "File not readable or doesn't exist");
						return;
					}
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
		
		@Override
		public void dispatchRequest(String urlPath) throws Exception {
			internalDispatchRequest(urlPath, false);
		}

		public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
			
			String mimeType = mimeTypes.getOrDefault(urlPath, "application/octet-stream");
			
			if(context == null) {
				context =  new RequestContext(ostream, params, persParams, outputCookies);
				context.setMimeType(mimeType);
				context.setStatusCode(200);
			}
			
			// predefinirani workeri
			if(workersMap.containsKey(urlPath)) {
				workersMap.get(urlPath).processRequest(context);
				return;
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
	 * Čita konfiguracijsku datoteku webservera. Radi inicijalizaciju
	 * mime tipova i {@code IWebWorker} objekata.
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

		String mimeConfigurationPath = prop.getProperty("server.mimeConfig");
		readMimeConfigurationFile(mimeConfigurationPath);
		
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
	 * Stvara {@code IWebWorker} objekte navedene u konfiguracijskoj 
	 * datoteci {@code workers.properties}.
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

		} catch (FileNotFoundException | ClassNotFoundException | InstantiationException | IllegalAccessException  e) {
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

	@SuppressWarnings("unused")
	private static void composeResponse(OutputStream cos, String path, String version, List<String> headers)
			throws IOException {

		StringBuilder sb = new StringBuilder(
				"<html>\r\n" + "  <head>\r\n" + "    <title>Ispis zaglavlja</title>\r\n" + "  </head>\r\n"
						+ "  <body>\r\n" + "    <h1>Informacije o poslanom upitu</h1>\r\n" + "    <p>Zatražen resurs: "
						+ path + "</p>\r\n" + "    <p>Definirane varijable:</p>\r\n" + "    <table border='1'>\r\n");

		for (String redak : headers) {
			int pos = redak.indexOf(':');
			sb.append("      <tr><td>").append(redak.substring(0, pos).trim()).append("</td><td>")
					.append(redak.substring(pos + 1).trim()).append("</td></tr>\r\n");
		}
		sb.append("    </table>\r\n" + "  </body>\r\n" + "</html>\r\n");

		byte[] tijeloOdgovora = sb.toString().getBytes(StandardCharsets.UTF_8);

		byte[] zaglavljeOdgovora = (version + " 200 OK\r\n" + "Server: simple java server\r\n"
				+ "Content-Type: text/html;charset=UTF-8\r\n" + "Content-Length: " + tijeloOdgovora.length + "\r\n"
				+ "Connection: close\r\n" + "\r\n").getBytes(StandardCharsets.US_ASCII);

		cos.write(zaglavljeOdgovora);
		cos.write(tijeloOdgovora);
		cos.flush();
	}

	public static void main(String[] args) {

		String fileName = "config/server.properties";
		new SmartHttpServer(fileName).start();
	}
}
