package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelIMpl;

/**
 * Razred koji služi za stavljanje vrijednosti na stog kalkulatora.
 * 
 * @author Antonio Kuzminski
 *
 */
public class PushCommand implements ICalcCommand {

	@Override
	public void execute(CalcModel model) {
		CalcModelIMpl.push(model.getValue());
	}

}
