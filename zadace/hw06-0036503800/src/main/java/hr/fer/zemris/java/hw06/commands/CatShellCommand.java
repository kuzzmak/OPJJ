package hr.fer.zemris.java.hw06.commands;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za ispis datoteke u {@code MyShell} koja je predana 
 * kao argument.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CatShellCommand implements ShellCommand {

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		String[] splitted = arguments.split("\\s+");
		
		String fileName = splitted[0];
		File file = new File(fileName);
		
		if(!file.exists()) {
			env.writeln("Predana datoteka: " + fileName + " ne postoji.");
			return ShellStatus.CONTINUE;
		}
		
		if(!file.isFile()) {
			env.writeln(fileName + " nije datoteka.");
			return ShellStatus.CONTINUE;
		}
		
		Charset charset = null;
		
		if(splitted.length == 2) charset = Charset.forName(splitted[1]);
		else charset = Charset.defaultCharset();
		
		try(Reader reader = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream(
								new FileInputStream(file)), 
						charset))){
			
			char[] buffer = new char[1024];
			int bytesRead;
			while((bytesRead = reader.read(buffer)) != -1) {
				env.write(String.valueOf(buffer));
			}
			
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		// TODO Auto-generated method stub
		return null;
	}
}
