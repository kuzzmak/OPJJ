package hr.fer.zemris.java.hw06.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za rekurzivan ispis sadržaja direktorija.
 * 
 * @author Antonio Kuzminski
 *
 */
public class TreeShellCommand implements ShellCommand {

	/**
	 * Metoda za rekurzivan ispis sadržaja direktorija.
	 * 
	 * @param indent razmak sljedeće razine
	 * @param file   trenutna datoteka ili direktorij
	 * @param env    referenca do {@code MyShell} za ispis
	 */
	private void tree(int indent, File file, Environment env) {

		for (int i = 0; i < indent; i++) {
			env.write(" ");
		}

		env.writeln(file.getName());

		if (file.isDirectory()) {

			File[] files = file.listFiles();

			for (File f : files) {
				tree(indent + 2, f, env);
			}
		}
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {

		List<String> splitted = MyShell.extractNormalLine(arguments);

		if (splitted.size() != 1) {
			env.writeln("Predan krivi broj argumenata u naredbu: " + getCommandName());
			return ShellStatus.CONTINUE;
		}

		File f = new File(splitted.get(0));

		if (!f.isDirectory()) {
			env.writeln("Predani argument nije direktorij.");
			return ShellStatus.CONTINUE;
		}

		tree(0, f, env);

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<String>(Arrays.asList("Ova naredba se koristi za ispis izgleda direktorija.",
				"Predani direktorij se obilazi rekurzivno, a svaka razina", "unutar je odvojena s dva razmaka.",
				"Primjer korištenja:", "\ttree /home/user/OPJJ/predavanja"));
	}
}
