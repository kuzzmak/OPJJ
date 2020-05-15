package hr.fer.zemris.java.gui.calc.model.commands;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja naredbu koja vraća rezultat operacije
 * klikom na znak jednakosti.
 * 
 * @author Antonio Kuzminski
 *
 */
public class EqualsCommand implements ICalcCommand {

	@Override
	public void execute(CalcModel model) {
		
		DoubleBinaryOperator op = model.getPendingBinaryOperation();
		double operand = model.getActiveOperand();
		double currentValue = model.getValue();

		double result = op.applyAsDouble(operand, currentValue);
		model.setValue(result);
		model.setActiveOperand(result);
	}

}
