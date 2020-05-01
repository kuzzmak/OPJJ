package hr.fer.zemris.java.hw06.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.MyShell;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za stvaranje novog direktorija.
 * 
 * @author Antonio Kuzminski
 *
 */
public class MkDirShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		List<String> splitted = MyShell.extractNormalLine(arguments);
		
		if(splitted.size() != 1) {
			env.writeln("Predan krivi broj argumenata u naredbu: " + getCommandName());
			return ShellStatus.CONTINUE;
		}
		
		File f = new File(splitted.get(0));
		
		if(!f.exists()) {
			
			try {
				Files.createDirectories(f.toPath());
			} catch (IOException e) {
				env.writeln("Nije moguće stvoriti: " + arguments);
			}
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "mkdir";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<>(Arrays.asList("Ova naredba se koristi za stvaranje direktorija.",
				"Primjer korištenja:",
				"\tmkdir src/test/java"));
	}
}
