package hr.fer.zemris.java.hw06.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za kopiranje datoteke u direktorij ili gaženje postojeće datoteke.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CopyShellCommand implements ShellCommand {

	/**
	 * Funkcija za kopiranje jedne datoteku u drugi direktorij ili gaženje druge
	 * datoteke.
	 * 
	 * @param f1 datoteka koja se kopira
	 * @param f2 direktorij u koji se kopira {@code f1} ili datoteka koju gati
	 *           {@code f1}
	 */
	private void copy(File f1, File f2) {

		try (InputStream is = new FileInputStream(f1); OutputStream os = new FileOutputStream(f2)) {

			byte[] buffer = new byte[1024];
			int byteRead;

			while ((byteRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, byteRead);
			}

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
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

		// datoteka koja se kopira
		File f1 = new File(splitted.get(0));
		// direkrorij ili datoteka koja se gazi
		File f2 = new File(splitted.get(1));

		// ako ne postoji, vraća se u MyShell
		if (!f1.exists()) {
			env.writeln("Datoteka: " + splitted.get(0) + " ne postoji.");
			return ShellStatus.CONTINUE;
		}

		// ako odredišni folder ne postoji, stvori se
		if (!f2.exists()) {
			env.commands().get("mkdir").executeCommand(env, f2.toString());
		}

		// direktorij nije moguće kopirati
		if (f1.isDirectory()) {
			env.writeln("Nije moguće kopirati direktorij.");
			return ShellStatus.CONTINUE;
		}

		// ako je drugi argument direktorij, doda mu se ime prve datoteke
		// čime se stvara nova u tome direktoriju
		if (f2.isDirectory()) {

			String f2Name = "";

			try {
				f2Name = f2.getCanonicalPath() + File.separator + f1.getName();
			} catch (IOException e) {
			}

			f2 = new File(f2Name);

			boolean ok = true;

			// ako je ok pregaziti postojeću datoteku
			if (f2.isFile()) {
				env.writeln(splitted.get(1) + " već postoji.");
				env.writeln("Prekopirati preko postojeće? [y/n]");
				env.write(String.valueOf(env.getPromptSymbol()));
				ok = env.readLine().strip().toLowerCase().equals("y") ? true : false;
			}

			if (ok)
				copy(f1, f2);
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("Ova naredba služi za kopiranje datoteke u odredišni",
				"direktorij. Ukoliko predani direktorij ne postoji, stvara se.",
				"Ako je datoteka istog imena u odredišnom direktoriju,",
				"korisnika se pita je li uredu prepisati. Ako je,", "prepisuje se datotekom istog imena. Nije moguće",
				"kopirati direktorij, samo datoteke.", "Primjer korištenja:",
				"\tcopy /home/user/OPJJ/zadace/zad/hw06.pdf /home/user/OPJJ/testing"));
	}
}
