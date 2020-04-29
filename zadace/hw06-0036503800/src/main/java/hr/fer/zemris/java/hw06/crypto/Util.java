package hr.fer.zemris.java.hw06.crypto;

/**
 * Razred s pomoćnim metodama za pretvorbu između heksadekatskih brojeva i
 * bajtova.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Util {

	// hex znamenke
	private final static char[] hexArray = "0123456789abcdef".toCharArray();

	/**
	 * Funkcija za pretvorbu heksadekatskog stringa u polje bajtova.
	 * 
	 * @param keyText string koji se pretvara u polje bajtova
	 * @throws NullPointerException     ako je predani string {@code null}
	 * @throws IllegalArgumentException ukoliko je neparan broj znakova stringa
	 * @return polje bajtova
	 */
	public static byte[] hextobyte(String keyText) {

		if (keyText == null)
			throw new NullPointerException("Argument funkcije ne može biti null.");

		String hex = keyText.toLowerCase();

		if (hex.length() % 2 != 0)
			throw new IllegalArgumentException("Krivo zapisan broj u heksadekatskom obliku.");

		byte[] data = new byte[hex.length() / 2];

		for (int i = 0; i < hex.length(); i += 2) {

			int num1 = Character.digit(hex.charAt(i), 16) << 4;
			int num2 = Character.digit(hex.charAt(i + 1), 16);

			data[i / 2] = (byte) (num1 + num2);
		}
		return data;
	}

	/**
	 * Funkcija za pretvorbu polja bajtova u heksadekatski niz.
	 * 
	 * @param bytearray polje bajtova koje se pretvara u niz
	 * @throws NullPointerException ukoliko je predano polje {@code null}
	 * @return heksadekatski niz
	 */
	public static String bytetohex(byte[] bytearray) {

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
