package hr.fer.zemris.java.hw06.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za ispis informacija o nekoj konkretnoj naredbi i 
 * za opis kako koristiti tu naredbu.
 * 
 * @author Antonio Kuzminski
 *
 */
public class HelpShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		ShellCommand command;
		
		if((command = env.commands().get(arguments)) != null) {
			
			command.getCommandDescription().forEach(env::writeln);

		}else {
			env.writeln("Nepoznata naredba: " + arguments);
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "help";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<String>(Arrays.asList("Naredba se koristi za opis korištenja pojedine naredbe."));
	}
}
