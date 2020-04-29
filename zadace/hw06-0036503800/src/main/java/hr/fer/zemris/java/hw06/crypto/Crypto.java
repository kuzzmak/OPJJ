package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {

	/**
	 * Metoda za provjeru hasha koji se dobije primjenom sha256 na predanoj
	 * datoteci.
	 * 
	 * @param file datoteka čiji se hash provjerava
	 * @param hash heksadekatska reprezentacija hasha koji treba biti jednak
	 *             generiranom hashu
	 * @throws NoSuchAlgorithmException ako algoritam za stvaranje hasha ne postoji
	 */
	private static void checksha(Path file, String hash){

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e1) {
		}

		try (InputStream is = Files.newInputStream(file)) {

			byte[] buff = new byte[16];

			while (true) {
				int r = is.read(buff);

				if (r < 1) break;
				else messageDigest.update(buff);
			}

		} catch (IOException e) {
			System.err.println("Datoteka ne postoji.");
			System.exit(-1);
		}

		byte[] digest = messageDigest.digest();

		boolean ok = Util.bytetohex(digest).equals(hash);

		if (ok) {
			System.out.println("Digesting complete. " + "Digest of: " + file + " matches expected digest.");
		} else {
			System.out.println("Digest completed. Digest of " + file
					+ " does not match the expected digest. Digest was: " + Util.bytetohex(digest));
		}

	}

	/**
	 * Metoda za stvaranje objekta preko kojeg se krptira ili dekriptira.
	 * 
	 * @param keyText ključ kojim se kriptira ili dekriptira
	 * @param ivText početni vektor
	 * @param encrypt kriptira li se ili dekriptira
	 * @return objekt tipa {@code Cipher}
	 */
	private static Cipher getCipher(String keyText, String ivText, boolean encrypt) {

		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");

		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));

		Cipher cipher;

		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5PAdding");

			try {
				cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
				return cipher;

			} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			}

		} catch (NoSuchAlgorithmException | NoSuchPaddingException e1) {
		}

		return null;
	}

	/**
	 * Metoda za kriptiranje ili dekriptiranje predane datoteke.
	 * 
	 * @param inputFile datoteka koja se kriptira ili dekriptira
	 * @param outputFile datoteka koja nastaje kao rezultat kriptiranja ili dekriptiranja
	 * @param keyText ključ kojim se kriptira ili dekriptira
	 * @param ivText početni vektor 
	 * @param encrypt kriptira li se ili dekriptira
	 */
	private static void encryptDecrypt(String inputFile, String outputFile, String keyText, String ivText, boolean encrypt) {

		Cipher cipher = getCipher(keyText, ivText, encrypt);

		try (InputStream is = new BufferedInputStream(new FileInputStream(inputFile));
				OutputStream os = new BufferedOutputStream(new FileOutputStream(outputFile))) {

			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = is.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null)
					os.write(output);
			}

			byte[] output = cipher.doFinal();
			if (output != null)
				os.write(output);

		} catch (IOException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
		
		if(encrypt) {
			System.out.println(
					"Encryption completed. Generated file " + outputFile + " based on file " + inputFile);
			
		}else {
			System.out.println(
					"Decryption completed. Generated file " + outputFile + " based on file " + inputFile);
		}
	}

	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args predani argumenti programu
	 * @throws IOException ukoliko ne postoji datoteka koja se kriptira ili dekriptira
	 * @throws IllegalArgumentException ako je predan krivi broj argumenata
	 */
	public static void main(String[] args) throws IOException{

		String function = args[0].toLowerCase();

		if (function.equals("checksha")) {
			
			if(args.length != 2)
				throw new IllegalArgumentException("Neispravan broj argumenata.");

			String path = args[1];

			String hash = "";

			System.out.println("Please provide expected sha-256 digest for: " + path + ":");

			try (Scanner sc = new Scanner(System.in)) {

				hash = sc.nextLine();
			}

			checksha(Paths.get(path), hash);

		} else if (function.equals("encrypt") || function.equals("decrypt")) {
			
			if(args.length != 3)
				throw new IllegalArgumentException("Neispravan broj argumenata.");

			String fileName = args[1];
			String encryptedFileName = args[2];

			String keyText;
			String ivText;

			try (Scanner sc = new Scanner(System.in)) {

				System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
				keyText = sc.nextLine();
				System.out.println("Please provide initalization vector as hex-encoded text (32 hex-digits):");
				ivText = sc.nextLine();
			}
			
			encryptDecrypt(fileName, encryptedFileName, keyText, ivText, true);

		} else 
			throw new IllegalArgumentException("Nepostojeća naredba: " + args[0]);
	}
}
