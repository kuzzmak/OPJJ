package hr.fer.zemris.java.gui.calc.model.commands;

import java.util.function.DoubleUnaryOperator;

import javax.swing.JCheckBox;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

/**
 * Razred koji predstavlja unarne operacije koje su invertibilne.
 * 
 * @author Antonio Kuzminski
 *
 */
public class InvertableUnaryCommand implements ICalcCommand {
	
	private DoubleUnaryOperator op1; 
	private DoubleUnaryOperator op2;
	private JCheckBox invButton;

	/**
	 * Konstruktor.
	 * 
	 * @param op1 operator koji se primjenjuje ako nije uključen gumb za inverziju
	 * @param op2 operator koji se primjenjuje ako je uključen gumb za inverziju
	 * @param invButton gumb za inverziju
	 */
	public InvertableUnaryCommand(DoubleUnaryOperator op1, DoubleUnaryOperator op2, JCheckBox invButton) {
		this.op1 = op1;
		this.op2 = op2;
		this.invButton = invButton;
	}
	
	@Override
	public void execute(CalcModel model) {
		
		double value = model.getValue();
		if (invButton.isSelected())
			model.setValue(op2.applyAsDouble(value));
		else
			model.setValue(op1.applyAsDouble(value));
	}

}
