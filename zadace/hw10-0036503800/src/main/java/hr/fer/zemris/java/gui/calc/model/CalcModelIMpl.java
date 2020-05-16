package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.function.DoubleBinaryOperator;

/**
 * Konkretna implementacija modela kalkulatora.
 * 
 * @author Antonio Kuzminski
 *
 */
public class CalcModelIMpl implements CalcModel {
	
	// trenutna vrijednost u double formatu
	private double currentValue = 0;
	// trenutna vrijednost u string formatu
	private String currentValueString = "";
	// čuvanje vrijednosti prikaza kalkulatora
	private String freezeValue = null;
	// je li moguće pritisnuti gumb za dodavanje znamenke
	private boolean isEditable = true;
	// trenutni operand nakon pritiska na neku binarnu operaciju
	private Double activeOperand = null;
	// je li trenutna zamrznuta vrijednost negativna
	private boolean negativeValue = false;
	// slušaći pojedinih gumba
	public static List<CalcValueListener> listeners;
	// operator koji čeka na drugi operand
	private DoubleBinaryOperator pendingOperator = null;
	// stog za stavljanje i vađenje brojeva/rezultata kalkulatora
	private static Stack<Double> stack;

	/**
	 * Konstruktor.
	 * 
	 */
	public CalcModelIMpl() {
		listeners = new ArrayList<>();
		stack = new Stack<>();
	}

	@Override
	public void addCalcValueListener(CalcValueListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeCalcValueListener(CalcValueListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}

	@Override
	public double getValue() {
		return currentValue;
	}

	@Override
	public void setValue(double value) {
		this.currentValue = value;
		this.isEditable = false;
		this.negativeValue = value > 0 ? false : true;
		
		if(value % 1 == 0) {
			currentValueString = String.valueOf((long) value);
		}else {
			currentValueString = String.valueOf(value);
		}
		
		freezeValue(currentValueString);
		notifyListeners();
	}

	@Override
	public boolean isEditable() {
		return isEditable;
	}

	@Override
	public void clear() {
		currentValue = 0;
		negativeValue = false;
		currentValueString = "";
		isEditable = true;
	}

	@Override
	public void clearAll() {
		clear();
		clearActiveOperand();
		freezeValue(null);
		notifyListeners();
	}

	@Override
	public void swapSign() throws CalculatorInputException {
		
		if(isEditable) {
			negativeValue = !negativeValue;
			currentValue = -currentValue;
			
			if(currentValue % 1 == 0) {
				currentValueString = String.valueOf((long) currentValue);
			}else {
				currentValueString = String.valueOf(currentValue);
			}
			freezeValue(currentValueString);
		}else 
			throw new CalculatorInputException("Kalkulator nije editabilan.");
		notifyListeners();
	}

	@Override
	public void insertDecimalPoint() throws CalculatorInputException {
		
		if(currentValueString.contains("."))
			throw new CalculatorInputException("Broj već sadrži decimalnu točku.");
		
		if(!isEditable())
			throw new CalculatorInputException("Kalkulator nije editabilan.");
		
		if(currentValueString.equals("") || currentValue == 0 && negativeValue)
			throw new CalculatorInputException("Nije moguće ubaciti decimalnu točku.");
		
		currentValueString = currentValueString + "."; 
		freezeValue(currentValueString);
		
		notifyListeners();
	}

	@Override
	public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {

		if(currentValueString.length() >= 308)
			throw new CalculatorInputException("Prevelik broj.");
		
		String oldNumber = currentValueString;
		
		if (isEditable()) {
			if (digit == 0 && oldNumber.equals("0")) {
				
			} else {
				
				if(oldNumber.length() == 1 && oldNumber.equals("0")) {
					oldNumber = "";
				}
				
				oldNumber += String.valueOf(digit);
				currentValueString = oldNumber;
				currentValue = Double.parseDouble(oldNumber);
				freezeValue(currentValueString);
			}
			
			notifyListeners();
		}
	}

	@Override
	public boolean isActiveOperandSet() {
		return activeOperand != null;
	}

	@Override
	public double getActiveOperand() throws IllegalStateException {
		if(isActiveOperandSet()) return activeOperand;
		else
			throw new IllegalStateException("Operand nije postavljen.");
	}

	@Override
	public void setActiveOperand(double activeOperand) {
		this.activeOperand = activeOperand;
	}

	@Override
	public void clearActiveOperand() {
		activeOperand = null;
	}

	@Override
	public DoubleBinaryOperator getPendingBinaryOperation() {
		return pendingOperator;
	}

	@Override
	public void setPendingBinaryOperation(DoubleBinaryOperator op) {
		pendingOperator = op;
	}

	@Override
	public void freezeValue(String value) {
		freezeValue = value;
	}

	@Override
	public boolean hasFrozenValue() {
		return this.freezeValue != null;
	}

	@Override
	public String toString() {

		if(hasFrozenValue()) {
			
			if(freezeValue.equals("0") && negativeValue) return "-0";
			
			return freezeValue;
		}
		return "0";
	}
	
	/**
	 * Funkcija za obavještavanje pretplaćenih slušaća modela.
	 * 
	 */
	private void notifyListeners() {
		
		for(CalcValueListener l: listeners) {
			l.valueChanged(this);		
		}
	}
	
	/**
	 * Metoda za stavljanje vrijednosti na stog.
	 * 
	 * @param item vrijednost koja se stavlja na stog
	 */
	public static void push(double item) {
		stack.push(item);
	}
	
	/**
	 * Metoda za dohvat vrijednosti s vrha stoga.
	 * 
	 * @return vrijednost na vrhu stoga ako postoji
	 * @throws CalculatorInputException ako je stog prazan
	 */
	public static Double pop() {
		
		if(!stack.isEmpty()) {
			return stack.pop();
		}
		throw new CalculatorInputException("Stog prazan.");
	}

}
