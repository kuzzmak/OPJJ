package hr.fer.zemris.java.hw06.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Naredba za ispis sadržaja predanog direktorija.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LsShellCommand implements ShellCommand {

	SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
	
	/**
	 * Mapper koji iz staze direktorija ili datoteke dobiva string 
	 * koji opisuje svojstva te datoteke/direktorija.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private class pathToStringMapper implements Function<Path, String> {

		@Override
		public String apply(Path path) {
			
			StringBuilder sb = new StringBuilder();

			File file = new File(path.toString());

			if (file.isDirectory()) sb.append("d"); else sb.append("-");
			if (file.canRead()) sb.append("r"); else sb.append("-");
			if (file.canWrite()) sb.append("w"); else sb.append("-");
			if (file.canExecute()) sb.append("x"); else sb.append("-");

			sb.append(" ");
			sb.append(String.format("%10d", file.length()));
			sb.append(" ");

			try {
				BasicFileAttributeView faView = Files.getFileAttributeView(path, 
						BasicFileAttributeView.class,
						LinkOption.NOFOLLOW_LINKS);

				BasicFileAttributes attributes = faView.readAttributes();
				FileTime fileTime = attributes.creationTime();

				String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
				sb.append(formattedDateTime);
				sb.append(" ");

			} catch (IOException e) {}

			sb.append(path.getFileName());

			return sb.toString();
		}
	}

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		
		if(!Files.isDirectory(Paths.get(arguments))) {
				env.writeln("Predana staza nije direktorij.");
				return ShellStatus.CONTINUE;
		}else {
			
			try {
				Files.list(Paths.get(arguments))
				.map(new pathToStringMapper())
				.forEach(env::writeln);
				
				return ShellStatus.CONTINUE;
			} catch (IOException e) {
				env.writeln("Nije moguće ispisati pristupiti nekim datotekama.");
				return ShellStatus.CONTINUE;
			}
		}
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		return new ArrayList<String>(Arrays.asList("Ova naredba se koristi za ispis datoteka",
				"i direktorija predanog direktorija preko", "argumenta funkcija. Ispis se sastoji", 
				"od 4 stupca: ", "\t 1. stupac: ", "\t\t je li direktorij(d)", "\t\t je li moguće čitati(r)",
				"\t\t je li moguće pisati(w)", "\t\t je li moguće izvršiti(x)", "\t 2. stupac: veličina objekta",
				"\t 3: stupac: vrijeme stvaranja", "\t 4. stupac: ime datoteke/direktorija"));
	}
}
