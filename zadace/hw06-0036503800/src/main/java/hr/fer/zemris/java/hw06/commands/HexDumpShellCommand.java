package hr.fer.zemris.java.hw06.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za ispis hexdump-a neke datoteke.
 * 
 * @author Antonio Kuzminski
 *
 */
public class HexDumpShellCommand implements ShellCommand {

	/**
	 * Funkcija koja stvara jedan redak hexdump-a iz predanog polja bajtova.
	 * 
	 * @param bytes polje bajtova koje se oblikuje u string
	 * @param row   redak ispisa
	 * @return string reprezentacija hexdump retka
	 */
	private String hexFormat(byte[] bytes, int row) {

		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%8d", row * 10).replace(' ', '0'));
		sb.append(": ");

		String hex = Util.bytetohex(bytes);

		// prva polovica ispisa nije potpuna
		if (hex.length() <= 16) {

			// prvi dio koji postoji
			for (int i = 0; i < hex.length(); i += 2) {

				if (i == 16) {
					sb.replace(sb.length() - 1, sb.length(), "");
					sb.append("|");
				}

				sb.append(hex.charAt(i));
				sb.append(hex.charAt(i + 1));
				sb.append(" ");
			}

			// drugi dio koji ne postoji
			for (int i = 0; i < 16 - hex.length(); i += 2)
				sb.append("   ");

		} else {

			for (int i = 0; i < 16; i += 2) {

				sb.append(hex.charAt(i));
				sb.append(hex.charAt(i + 1));
				sb.append(" ");
			}
		}

		// srednja crta koja dijeli dva dijela tablice
		sb.replace(sb.length() - 1, sb.length(), "");
		sb.append("|");

		if (hex.length() >= 16 && hex.length() <= 32) {

			// ostatak hex stringa koji postoji
			for (int i = 16; i < hex.length(); i += 2) {

				sb.append(hex.charAt(i));
				sb.append(hex.charAt(i + 1));
				sb.append(" ");
			}

			// drugi dio koji ne postoji
			for (int i = hex.length(); i < 32; i += 2)
				sb.append("   ");
		} else {

			// drugi dio koji ne postoji
			for (int i = 0; i < 16; i += 2)
				sb.append("   ");
		}

		// znakovi
		sb.append("| ");

		for (byte b : bytes) {

			if (b >= 32 && b <= 127)
				sb.append((char) b);
			else
				sb.append(".");
		}

		return sb.toString();
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		List<String> splitted = null;

		try {
			splitted = MyShell.extractNormalLine(arguments);
		} catch (ShellIOException e) {
			env.writeln(e.getMessage());
			return ShellStatus.CONTINUE;
		}

		if (splitted.size() != 1) {
			env.writeln("Pogrešan broj argumenata za naredbu: " + getCommandName());
		}

		File f = new File(splitted.get(0));

		if (!f.exists()) {
			env.writeln("Datoteka: " + arguments + " ne postoji.");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new FileInputStream(f)) {

			byte[] buffer = new byte[16];
			int bytesRead;
			int row = 0;

			while ((bytesRead = is.read(buffer)) != -1) {

				env.writeln(hexFormat(Arrays.copyOf(buffer, bytesRead), row));
				row++;
			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("Naredba za stvaranje hexdump-a datoteke.", "Primjer korištenja:",
				"\thexdump /home/test.txt"));
	}
}
