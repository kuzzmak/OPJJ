package hr.fer.zemris.java.gui.calc.model.commands;

import java.util.function.DoubleBinaryOperator;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja osnovne aritmetičke operacije: zbrajanje,
 * oduzimanje, dijeljenje i množenje. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class BasicOperationCommand implements ICalcCommand {

	private DoubleBinaryOperator operator;
	
	/**
	 * Konstruktor.
	 * 
	 * @param operator operacija koja se dodjeljuje nekom gumbu
	 */
	public BasicOperationCommand(DoubleBinaryOperator operator) {
		this.operator = operator;
	}
	
	@Override
	public void execute(CalcModel model) {
		
		// slučaj kada se operacije nižu jedna za drugom
		if(model.isActiveOperandSet()) {
			
			// prvo se izračuna prethodna operacija
			DoubleBinaryOperator op = model.getPendingBinaryOperation();
			double operand = model.getActiveOperand();
			double currentValue = model.getValue();

			double result = op.applyAsDouble(operand, currentValue);
			model.setValue(result);
			model.setActiveOperand(result);
		}
		
		// ako dosad nije postavljena nikakva operacija ili izvršenje druge
		// operacije ukoliko je postojala prethodna
		model.setActiveOperand(model.getValue());
		model.setPendingBinaryOperation(operator);
		model.clear();
	}

}
