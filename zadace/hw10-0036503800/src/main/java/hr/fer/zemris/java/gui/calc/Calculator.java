package hr.fer.zemris.java.gui.calc;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleBinaryOperator;
import java.util.function.DoubleUnaryOperator;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.calc.model.CalcModelIMpl;
import hr.fer.zemris.java.gui.calc.model.CalcValueListener;
import hr.fer.zemris.java.gui.calc.model.Operators;
import hr.fer.zemris.java.gui.calc.model.ScreenListener;
import hr.fer.zemris.java.gui.calc.model.commands.BasicOperationCommand;
import hr.fer.zemris.java.gui.calc.model.commands.ClearAllCommand;
import hr.fer.zemris.java.gui.calc.model.commands.ClearCommand;
import hr.fer.zemris.java.gui.calc.model.commands.EqualsCommand;
import hr.fer.zemris.java.gui.calc.model.commands.ICalcCommand;
import hr.fer.zemris.java.gui.calc.model.commands.InsertDecimalPointCommand;
import hr.fer.zemris.java.gui.calc.model.commands.InvertableBinaryCommand;
import hr.fer.zemris.java.gui.calc.model.commands.InvertableUnaryCommand;
import hr.fer.zemris.java.gui.calc.model.commands.NumpadCommand;
import hr.fer.zemris.java.gui.calc.model.commands.PopCommand;
import hr.fer.zemris.java.gui.calc.model.commands.PushCommand;
import hr.fer.zemris.java.gui.calc.model.commands.SwapSignCommand;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

/**
 * Implementacija jednostavnog džepnog računala.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Calculator extends JFrame {

	private static final long serialVersionUID = 1L;

	private CalcModel model;

	// imena lijevih unarnih funkcija
	List<String> unaryFunctionNames1 = new ArrayList<>(Arrays.asList("sin", "cos", "tan", "ctg", "1/x", "log", "ln"));

	List<String> unaryFunctionNames2 = new ArrayList<>(
			Arrays.asList("arcsin", "arccos", "arctan", "arcctg", "1/x", "10^x", "e^x"));

	// normalni operatori
	List<DoubleUnaryOperator> operators1 = new ArrayList<>(Arrays.asList(Operators.SIN, Operators.COS, Operators.TAN,
			Operators.CTG, Operators.ONEDIVX, Operators.LOG, Operators.LN));

	// invertirani operatori
	List<DoubleUnaryOperator> operators2 = new ArrayList<>(Arrays.asList(Operators.ARCSIN, Operators.ARCCOS,
			Operators.ARCTAN, Operators.ARCCTG, Operators.ONEDIVX, Operators.TENPOWX, Operators.EPOWX));

	// lista referenci do lijevih gumbi koji mogu promijeniti tekst klikom na gumb inv
	List<JButton> leftButtons = new ArrayList<>();

	/**
	 * Konstruktor.
	 * 
	 */
	public Calculator() {

		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		model = new CalcModelIMpl();
		initGUI();
		setPreferredSize(new Dimension(600, 300));
		setTitle("Java Calculator v1.0");
		pack();
	}

	/**
	 * Funkcija za incijalizaciju grafičkog sučelja kalkulatora.
	 * 
	 */
	private void initGUI() {

		Container cp = getContentPane();
		cp.setLayout(new CalcLayout(5));

		// labela ekrana kalkulatora
		JLabel screen = new JLabel("0", JLabel.RIGHT);
		screen.setBackground(Color.YELLOW);
		screen.setOpaque(true);
		cp.add(screen, new RCPosition(1, 1));

		CalcValueListener lScreen = new ScreenListener(screen);
		model.addCalcValueListener(lScreen);

		// gumb za invertiranje funkcija---------------------------------
		JCheckBox invButton = new JCheckBox("inv");
		invButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (invButton.isSelected()) {

					for (int i = 0; i < unaryFunctionNames2.size(); i++) {

						JButton button = leftButtons.get(i);
						button.setText(unaryFunctionNames2.get(i));
					}
					leftButtons.get(leftButtons.size() - 1).setText("x^(1/n)");

				} else {

					for (int i = 0; i < unaryFunctionNames1.size(); i++) {

						JButton button = leftButtons.get(i);
						button.setText(unaryFunctionNames1.get(i));
					}
					leftButtons.get(leftButtons.size() - 1).setText("x^n");
				}
			}
		});

		cp.add(invButton, new RCPosition(5, 7));

		// dodavanje funkcija lijeve strane koje su unarne------------
		for (int i = 0, row = 2, column = 2; i < unaryFunctionNames1.size(); i++) {

			if (i == 4)
				column = 1;
			if (row >= 6)
				row = 2;

			JButton button = new JButton(unaryFunctionNames1.get(i));
			addListener(button, 
					new InvertableUnaryCommand(operators1.get(i), operators2.get(i), invButton), 
					model);
			leftButtons.add(button);

			cp.add(button, new RCPosition(row++, column));
		}

		// gumb za potenciranje na n-tu-------------------------------
		JButton buttonXpowN = new JButton("x^n");
		addListener(buttonXpowN, 
				new InvertableBinaryCommand(Operators.XPOW1N, Operators.XPOWN, invButton), 
				model);

		leftButtons.add(buttonXpowN);
		cp.add(buttonXpowN, new RCPosition(5, 1));

		// brojevi od 1-9----------------------------------------------
		for (int row = 0, number = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {

				number++;
				JButton button = new JButton(String.valueOf(number));
				changeFontSize(button, 35f);
				addListener(button, new NumpadCommand(number), model);
				cp.add(button, new RCPosition(4 - row, column + 3));
			}
		}

		// broj 0------------------------------------------------------
		JButton b0 = new JButton("0");
		changeFontSize(b0, 35f);
		addListener(b0, new NumpadCommand(0), model);
		cp.add(b0, new RCPosition(5, 3));

		// osnovne operacije-------------------------------------------
		List<String> basicOperators = new ArrayList<>(Arrays.asList("/", "*", "-", "+"));
		List<DoubleBinaryOperator> basicOperations = new ArrayList<>(
				Arrays.asList(Operators.DIV, Operators.MUL, Operators.SUB, Operators.ADD));

		for (int row = 0; row < 4; row++) {
			JButton button = new JButton(basicOperators.get(row));
			changeFontSize(button, 20f);
			addListener(button, new BasicOperationCommand(basicOperations.get(row)), model);
			cp.add(button, new RCPosition(row + 2, 6));
		}

		// posebne operacije s desne strane-----------------------------
		JButton buttonClear = new JButton("clr");
		addListener(buttonClear, new ClearCommand(), model);
		cp.add(buttonClear, new RCPosition(1, 7));

		JButton buttonReset = new JButton("reset");
		addListener(buttonReset, new ClearAllCommand(), model);
		cp.add(buttonReset, new RCPosition(2, 7));

		JButton buttonPush = new JButton("push");
		addListener(buttonPush, new PushCommand(), model);
		cp.add(buttonPush, new RCPosition(3, 7));

		JButton buttonPop = new JButton("pop");
		addListener(buttonPop, new PopCommand(), model);
		cp.add(buttonPop, new RCPosition(4, 7));

		// gumb za promjenu predznaka-----------------------------------
		JButton plusMinus = new JButton("+/-");
		changeFontSize(plusMinus, 20f);
		addListener(plusMinus, new SwapSignCommand(), model);
		cp.add(plusMinus, new RCPosition(5, 4));

		// gumb za dodavanje decimalne točke----------------------------
		JButton dotButton = new JButton(".");
		changeFontSize(dotButton, 20f);
		addListener(dotButton, new InsertDecimalPointCommand(), model);
		cp.add(dotButton, new RCPosition(5, 5));

		// gumb jednako-------------------------------------------------
		JButton equalsButton = new JButton("=");
		changeFontSize(equalsButton, 20f);
		addListener(equalsButton, new EqualsCommand(), model);
		cp.add(equalsButton, new RCPosition(1, 6));

	}

	/**
	 * Funkcija za dodavanje slušača gumbu {@code button} i pridjeljivanje
	 * naredbe {@code command} koju izvodi ako ga se pritisne.
	 * 
	 * @param button gumb kojem se dodjeljuje slušać
	 * @param command naredba koja se dodjeljuje gumbu
	 * @param model referenca do modela kalkulatora
	 */
	private void addListener(JButton button, ICalcCommand command, CalcModel model) {
		
		button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				command.execute(model);
			}
		});
	}
	
	/**
	 * Funkcija za promjenu veličina fonta komponente.
	 * 
	 * @param component komponenta čiji font se mijenja
	 * @param fontSize nova veličina fonta
	 */
	private void changeFontSize(JComponent component, float fontSize) {
		component.setFont(component.getFont().deriveFont(fontSize));
	}

	/**
	 * Metoda iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args argumenti glavnog programa
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
	}

}
