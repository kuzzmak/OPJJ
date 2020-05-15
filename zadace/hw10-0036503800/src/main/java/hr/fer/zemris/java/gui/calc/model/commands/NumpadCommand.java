package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja naredbu {@link hr.fer.zemris.java.gui.calc.model.CalcModelIMpl#insertDigit(int) insertDigit(int)}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class NumpadCommand implements ICalcCommand {

	private int value;
	
	/**
	 * Konstruktor.
	 * 
	 * @param value vrijednost koju naredba u broj kalkulatora
	 */
	public NumpadCommand(int value) {
		this.value = value;
	}
	
	@Override
	public void execute(CalcModel model) {
		model.insertDigit(value);
	}

}
