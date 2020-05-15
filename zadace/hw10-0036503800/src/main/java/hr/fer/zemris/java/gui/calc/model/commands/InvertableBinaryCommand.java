package hr.fer.zemris.java.gui.calc.model.commands;

import java.util.function.DoubleBinaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja binarne operacije kalkulatora koje
 * je moguće invertirati, odnosno napraviti f^-1.
 * 
 * @author Antonio Kuzminski
 *
 */
public class InvertableBinaryCommand implements ICalcCommand {

	private DoubleBinaryOperator op1; 
	private DoubleBinaryOperator op2;
	private JCheckBox invButton;

	/**
	 * Konstruktor.
	 * 
	 * @param op1 operator koji se izvodi ako nije uključen gumb za inverziju
	 * @param op2 operator koji se izvodi ako je uključen gumb za inverziju
	 * @param invButton gumb koji služi za inverziju operacija
	 */
	public InvertableBinaryCommand(DoubleBinaryOperator op1, DoubleBinaryOperator op2, JCheckBox invButton) {
		this.op1 = op1;
		this.op2 = op2;
		this.invButton = invButton;
	}
	
	@Override
	public void execute(CalcModel model) {
		
		model.setActiveOperand(model.getValue());
		if (invButton.isSelected())
			model.setPendingBinaryOperation(op1);
		else
			model.setPendingBinaryOperation(op2);
		model.clear();
	}

}
