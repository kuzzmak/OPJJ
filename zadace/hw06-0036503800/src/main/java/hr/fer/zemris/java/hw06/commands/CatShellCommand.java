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
import hr.fer.zemris.java.hw06.shell.MyShell;
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
		
		List<String> splitted = MyShell.extractNormalLine(arguments);
		
		String fileName = splitted.get(0);
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
		
		if(splitted.size() == 2) {
			
			if(!Charset.isSupported(splitted.get(1))) {
				env.writeln("Predani skup znakova nije podržan.");
				return ShellStatus.CONTINUE;
			}
				
			
			charset = Charset.forName(splitted.get(1));
		}
		else charset = Charset.defaultCharset();
		
		try(Reader reader = new BufferedReader(
				new InputStreamReader(
						new BufferedInputStream(
								new FileInputStream(file)), 
						charset))){
			
			char[] buffer = new char[1024];
			int bytesRead;
			while((bytesRead = reader.read(buffer)) != -1) {
				env.write(new String(buffer, 0, bytesRead));
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
