package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja naredbu {@link hr.fer.zemris.java.gui.calc.model.CalcModelIMpl#clearAll() clearAll()}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ClearAllCommand implements ICalcCommand {

	@Override
	public void execute(CalcModel model) {
		model.clearAll();
	}

}
