package hr.fer.zemris.java.hw06.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba kojom se izlazi is {@code MyShell}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ExitShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		return ShellStatus.TERMINATE;
	}

	@Override
	public String getCommandName() {
		return "exit";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<String>(Arrays.asList("Ovom naredbom se izlazi iz MyShell."));
	}
}
