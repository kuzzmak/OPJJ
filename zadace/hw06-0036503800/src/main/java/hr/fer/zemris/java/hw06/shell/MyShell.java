package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.commands.HexDumpShellCommand;
import hr.fer.zemris.java.hw06.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.commands.MkDirShellCommand;
import hr.fer.zemris.java.hw06.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.commands.TreeShellCommand;

/**
 * Jednostavna emulacija komandne linije. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class MyShell {

	private static Environment env;

	Scanner sc;

	private Character PROMPTSYMBOL = '>';
	private Character MULTILINESYMBOL = '|';
	private Character MORELINESYMBOL = '\\';

	private ShellStatus status = ShellStatus.CONTINUE;

	SortedMap<String, ShellCommand> commands;

	/**
	 * Inicijalni konstruktor.
	 * 
	 */
	public MyShell() {

		env = makeEnvironment();
		commands = env.commands();

		env.writeln("Welcome to MyShell v 1.0");

		start();
	}

	/**
	 * Funkcija za inicijalizaciju objekta za komunikciju s {@code MyShell}.
	 * 
	 * @return objekt tipa {@code Environment}
	 */
	private Environment makeEnvironment() {

		sc = new Scanner(System.in);

		return new Environment() {

			@Override
			public void writeln(String text) throws ShellIOException {
				System.out.println(text);
			}

			@Override
			public void write(String text) throws ShellIOException {
				System.out.print(text);
			}

			@Override
			public void setPromptSymbol(Character symbol) {
				PROMPTSYMBOL = symbol;
			}

			@Override
			public void setMultiLineSymbol(Character symbol) {
				MULTILINESYMBOL = symbol;
			}

			@Override
			public void setMoreLinesSymbol(Character symbol) {
				MORELINESYMBOL = symbol;
			}

			@Override
			public String readLine() throws ShellIOException {
				if (sc.hasNextLine())
					return sc.nextLine();
				else
					throw new ShellIOException("Nije bilo nikakvog unosa.");
			}

			@Override
			public Character getPromptSymbol() {
				return PROMPTSYMBOL;
			}

			@Override
			public Character getMultiLineSymbol() {
				return MULTILINESYMBOL;
			}

			@Override
			public Character getMorelinesSymbol() {
				return MORELINESYMBOL;
			}

			@Override
			public SortedMap<String, ShellCommand> commands() {
				SortedMap<String, ShellCommand> commands = new TreeMap<>();

				commands.put("charsets", new CharsetsShellCommand());
				commands.put("ls", new LsShellCommand());
				commands.put("help", new HelpShellCommand());
				commands.put("symbol", new SymbolShellCommand());
				commands.put("exit", new ExitShellCommand());
				commands.put("cat", new CatShellCommand());
				commands.put("tree", new TreeShellCommand());
				commands.put("mkdir", new MkDirShellCommand());
				commands.put("copy", new CopyShellCommand());
				commands.put("hexdump", new HexDumpShellCommand());

				return commands;
			}
		};
	}

	/**
	 * Funkcija za izvlačenje argumenata iz naredbenog retka {@code MyShell}.
	 * Naredba se može protezati kroz više redaka pa ako je to slučaj,
	 * svi se dijelovi spoje zajedno s razmakom između svakog.
	 * 
	 * @param env objekt za komunikaciju s {@code MyShell}
	 * @return string reprezentacija unesenog teksta u naredbenom retku
	 */
	private String extractMultiLine(Environment env) {

		List<String> lines = new ArrayList<>();

		boolean multilineMode = false;

		while (true) {

			String line = env.readLine();

			// ako je prethodno bio redak koj završava
			// sa simbolom za više redaka
			if (multilineMode) {

				// ako sljedeći redak nema simbol za multiline, greška
				if (line.startsWith(String.valueOf(env.getMultiLineSymbol()))) {

					// zadnji redak stringa koji se proteže kroz više
					// redaka nema znak za moreline na kraju jer je zadnji
					if (!line.endsWith(String.valueOf(env.getMorelinesSymbol()))) {

						lines.add(line.substring(1, line.length()).strip());
						break;
					}

					// inače je redak koji na početku ima simbol za početak
					// sljedeće linije i na kraju simbol da slijedi još redaka
					lines.add(line.substring(1, line.length() - 2).strip());
					continue;

				} else {
					env.writeln("Krivo zadan string.");
					break;
				}
			}

			// ako smo naišli na redak koji započinje unos kroz više redaka
			if (line.endsWith(String.valueOf(env.getMorelinesSymbol()))) {

				multilineMode = true;
				lines.add(line.substring(0, line.length() - 2).strip());
			}

			// pročitan je normalan redak
			if (!multilineMode) {
				return line;
			}
		}
		
		StringBuilder sb = new StringBuilder();
		
		for(String l: lines) {
			sb.append(l).append(" ");
		}

		return sb.toString();
	}
	
	/**
	 * Metoda za izvlačenje argumenata iz naredbenog retka nakon što je
	 * obavljeno spajanje ako se naredba protezala kroz više redaka.
	 * 
	 * @param text redak iz kojeg se izvlače argumenti
	 * @return lista argumenata
	 * @throws ShellIOException ako je unesen neispravan string
	 */
	public static List<String> extractNormalLine(String text) {

		List<Character> spaces = new ArrayList<>(Arrays.asList(' ', '\n', '\t', '\r'));

		List<String> splitted = new ArrayList<>();

		char[] data = text.toCharArray();

		StringBuilder sb = new StringBuilder();

		int currentIndex = 0;
		while (currentIndex < text.length()) {

			if (data[currentIndex] == '"') {

				// pomak na znak poslije prvog navodnika
				currentIndex++;
				int start = currentIndex;

				while (data[currentIndex] != '"')
					currentIndex++;
				splitted.add(new String(data, start, currentIndex - start));

				currentIndex++;

				if (currentIndex < data.length) {
					// ako odmah nakon navodnika nije neka praznina, greška
					if (!spaces.contains(data[currentIndex])) {
						throw new ShellIOException("Krivo zadan string: " + text);
					}
				}
				continue;

			} else if (spaces.contains(data[currentIndex])) {

				if (sb.length() > 0) {
					splitted.add(sb.toString());
					sb.delete(0, sb.length());
				}

				currentIndex++;
				continue;
			}

			sb.append(data[currentIndex]);
			currentIndex++;
		}
		
		if(sb.length() > 0) {
			splitted.add(sb.toString());
		}
		
		return splitted;
	}

	/**
	 * Metoda za pokretanje izvođenja {@code MyShell}.
	 * 
	 */
	private void start() {

		env.write(String.valueOf(env.getPromptSymbol()));

		while (true) {
			String line = extractMultiLine(env);
			String[] splitted = line.split("\\s+", 2);
			String commandName = splitted[0];
			String arguments = "";

			if (splitted.length > 1) {
				arguments = splitted[1];
			}

			ShellCommand command = commands.get(commandName);

			if (command != null) {

				status = command.executeCommand(env, arguments);

			} else {
				env.writeln("Nepoznata naredba: " + commandName);
			}

			if (status == ShellStatus.TERMINATE)
				break;

			env.write(String.valueOf(env.getPromptSymbol()));
		}

		sc.close();
	}

	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args argumenti glavnog programa
	 */
	public static void main(String[] args) {

		new MyShell();
	}
}
