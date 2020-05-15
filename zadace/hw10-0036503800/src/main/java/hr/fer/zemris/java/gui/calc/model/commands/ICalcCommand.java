package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Sučelje koje predstavlja pojedinu naredbu kalkulatora koja 
 * se izvodi pritiskom na neki od gumba.  
 * 
 * @author Antonio Kuzminski
 *
 */
public interface ICalcCommand {
	
	/**
	 * Metoda za izvođenje naredbe.
	 * 
	 * @param model referenca do modela kalkulatora
	 */
	void execute(CalcModel model);
}
