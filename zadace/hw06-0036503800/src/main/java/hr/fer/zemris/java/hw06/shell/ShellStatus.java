package hr.fer.zemris.java.hw06.shell;

/**
 * Enumeracija za praćenje stanja {@code MyShell}. 
 * 
 * @author Antonio Kuzminski
 *
 */
public enum ShellStatus {
	
	// normalno stanje, nastavlja se s izvođenje
	CONTINUE,
	// izlazi se iz MyShell
	TERMINATE
}
