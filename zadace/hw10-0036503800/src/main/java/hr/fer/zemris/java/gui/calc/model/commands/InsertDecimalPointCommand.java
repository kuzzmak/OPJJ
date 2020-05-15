package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja naredbu {@link hr.fer.zemris.java.gui.calc.model.CalcModelIMpl#insertDecimalPoint() insertDecimalPoint()}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class InsertDecimalPointCommand implements ICalcCommand {

	@Override
	public void execute(CalcModel model) {
		model.insertDecimalPoint();
	}

}
