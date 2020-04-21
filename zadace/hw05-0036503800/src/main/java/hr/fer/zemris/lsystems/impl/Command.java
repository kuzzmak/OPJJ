package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Sučelje koje modelira neku vrstu naredbe. Svaka naredba se može izvršiti.
 * 
 * @author Antonio Kuzminski
 *
 */
public interface Command {

	/**
	 * Metoda koja izvršava dotičnu neredbu.
	 * 
	 * @param ctx kontekst nad kojim se naredba izvršava
	 * @param painter objekt nad kojim se crta
	 */
	void execute(Context ctx, Painter painter);
}
