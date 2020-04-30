package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Model pojedine naredbe {@code MyShell}.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface ShellCommand {

	/**
	 * Metoda za izvršavanje svake funkcije.
	 * 
	 * @param env referenca do objekta za komunikaciju s {@code MyShell}
	 * @param arguments argumenti naredbe
	 * @return status tipa {@code ShellStatus}
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Metoda za dohvat imena naredbe.
	 * 
	 * @return ime naredbe
	 */
	String getCommandName();
	
	/**
	 * Metoda za dohvat opisa/pomoći naredbe.
	 * 
	 * @return opis/pomoć naredbe u obliku liste stringova
	 */
	List<String> getCommandDescription();
}
