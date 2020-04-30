package hr.fer.zemris.java.hw06.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba {@code MyShell} koja dohvaća moguće skupove znakova na računalu.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CharsetsShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		SortedMap<String, Charset> charsets = Charset.availableCharsets();
		charsets.forEach((k, v) -> env.writeln(k + v));
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "charsets";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<String>(
				Arrays.asList("Ova naredba se koristi za dohvat mogućih",
						"skupova znakova koji postoje na računalu."));
	}

}
