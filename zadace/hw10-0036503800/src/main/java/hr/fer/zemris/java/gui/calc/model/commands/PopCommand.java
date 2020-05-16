package hr.fer.zemris.java.gui.calc.model.commands;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelIMpl;

/**
 * Razred koji služi za dohvat vrijednosti s vrha stoga ako nije prazan.
 * 
 * @author Antonio Kuzminski
 *
 */
public class PopCommand implements ICalcCommand {

	@Override
	public void execute(CalcModel model) {
		
		double value = CalcModelIMpl.pop();
		model.setValue(value);
	}

}
