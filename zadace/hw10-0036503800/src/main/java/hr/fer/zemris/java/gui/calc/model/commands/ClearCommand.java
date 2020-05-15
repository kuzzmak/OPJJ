package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

public class ClearCommand implements ICalcCommand {

	@Override
	public void execute(CalcModel model) {
		model.clear();
	}

}
