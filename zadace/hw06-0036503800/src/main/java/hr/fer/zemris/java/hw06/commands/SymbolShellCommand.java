package hr.fer.zemris.java.hw06.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za postavljanje simbola koji se interno koriste u {@code MyShell}
 * 
 * @author Antonio Kuzminski
 *
 */
public class SymbolShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] splitted = arguments.split("\\s+");
		
		String command = splitted[0];
		
		if(splitted.length == 1) {
			
			if(command.equals("PROMPT")) env.writeln("Symbol for PROMPT is '" + String.valueOf(env.getPromptSymbol()) + "'");
			else if(command.equals("MORELINES")) env.writeln("Symbol for MORELINES is '" + String.valueOf(env.getMorelinesSymbol()) + "'");
			else if(command.equals("MULTILINE")) env.writeln("Symbol for MULTILINE is '" + String.valueOf(env.getMultiLineSymbol()) + "'");
			else env.writeln(command + " nije valjani simbol.");
			
		}else if(splitted.length == 2) {
			
			if(command.equals("PROMPT")) env.setPromptSymbol(splitted[1].charAt(0));
			else if(command.equals("MORELINES")) env.setMoreLinesSymbol(splitted[1].charAt(0));
			else if(command.equals("MULTILINE")) env.setMultiLineSymbol(splitted[1].charAt(0));
			else env.writeln(command + " nije valjani simbol.");
			
		}else
			env.writeln("Kriva sintaksa naredbe.");
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "symbol";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("Naredba služi za promjenu interno korištenih simbola",
				"za prompt, za string kojim se označava kraj linije koja",
				"nije zadnja, a postoji ih više ili za string koji označava",
				"sljedeću liniju.", 
				"Primjer korištenja:",
				"\t>symbol PROMPT",
				"\tSymbol for PROMPT is '>'",
				"",
				">symbol PROMPT #",
				"Symbol for PROMPT changed from '>' to '#'"));
	}
}
