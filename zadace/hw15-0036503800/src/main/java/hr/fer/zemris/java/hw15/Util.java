package hr.fer.zemris.java.hw15;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Util {

	/**
	 * Stvaranje SHA-256 sažetka predanog stringa.
	 * 
	 * @param s string čiji se sažetak stvara
	 * @return string sažetka
	 */
	public static String getMessageDigest(String s) {

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
		}

		Charset charset = StandardCharsets.UTF_8;
		byte[] byteArrray = charset.encode(s).array();
		byte[] digest = messageDigest.digest(byteArrray);

		return bytetohex(digest);
	}
	
	/**
	 * Funkcija za pretvorbu polja bajtova u heksadekatski niz.
	 * 
	 * @param bytearray polje bajtova koje se pretvara u niz
	 * @throws NullPointerException ukoliko je predano polje {@code null}
	 * @return heksadekatski niz
	 */
	private static String bytetohex(byte[] bytearray) {
		
		// hex znamenke
		final char[] hexArray = "0123456789abcdef".toCharArray();

		if (bytearray == null)
			throw new NullPointerException("Argument funkcije ne može biti null.");

		StringBuilder sb = new StringBuilder();

		for (byte b : bytearray) {

			int v = b & 0xFF;
			sb.append(hexArray[v >>> 4]);
			sb.append(hexArray[v & 0x0F]);
		}

		return sb.toString();
	}

}
