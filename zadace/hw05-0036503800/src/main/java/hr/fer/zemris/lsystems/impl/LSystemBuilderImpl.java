package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;
import hr.fer.zemris.math.Vector2D;

/**
 * Razred koji predstavlja sustav za prikazivanje Lindenmayerovih fraktala.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {

	// rječnik mogućih akcija
	private Dictionary<Character, Command> actions = new Dictionary<>();
	// rječnik produkcija
	private Dictionary<Character, String> productions = new Dictionary<>();
	// pohranjuje trenutno stanje kornjače
	private Context context;
	// jedinica pomaka kornjače
	private double unitLength = 0.1;
	// faktor kojim se korak kornjače skalira
	private double unitLengthDegreeScaler = 1;
	// početni položaj kornjače
	private Vector2D origin = new Vector2D(0, 0);
	// poečtni kut usmjerenja kornjače
	private double angle = 0;
	// početni simbol
	private String axiom = "";

	/**
	 * Konkretna implementacija jednog generatora Lindenmayerovih fraktala.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	class LSystemImpl implements LSystem {

		@Override
		public void draw(int arg0, Painter arg1) {

			context = new Context();
			// početno stanje
			context.pushState(new TurtleState(origin,
					new Vector2D(Math.cos(Math.toRadians(angle)), Math.sin(Math.toRadians(angle))), Color.BLACK,
					unitLength * Math.pow(unitLengthDegreeScaler, arg0)));

			String generated = generate(arg0);
			
			char[] characters = generated.toCharArray();

			for (char c : characters) {

				// ako postoji akcije, izvodi se, ako ne, preskače se
				if (actions.get(c) != null) {
					Command command = actions.get(c);
					command.execute(context, arg1);
				}
			}
		}

		@Override
		public String generate(int arg0) {

			if(arg0 == 0) return axiom;
			
			StringBuilder sb = new StringBuilder();
			
			char[] aksiomCharacters = axiom.toCharArray();
			
			// ako se aksiom sastoji od više slova, svakom slovu se priduži 
			// produkcija ako postoji
			for(int i = 0; i < aksiomCharacters.length; i++) {
				
				if(productions.get(aksiomCharacters[i]) != null) {
					sb.append(productions.get(aksiomCharacters[i]));
				}else {
					sb.append(aksiomCharacters[i]);
				}
			}

			StringBuilder nextProduction = new StringBuilder();

			for (int i = 1; i < arg0; i++) {

				for (int j = 0; j < sb.length(); j++) {

					// ako smo naišli na neki znak koji ide u produkciju
					if (productions.get(sb.charAt(j)) != null) {

						nextProduction.append(productions.get(sb.charAt(j)));

					} else
						nextProduction.append(sb.charAt(j)); // neki operator/akcija
				}

				sb = nextProduction;
				nextProduction = new StringBuilder();
			}

			return sb.toString();
		}
	}

	@Override
	public LSystem build() {

		return new LSystemImpl();
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {

		for (String s : arg0) {

			// odvaja se na temelju prve praznine
			String[] splitted = s.strip().split("\\s+", 2);
			// prazni redak
			if (splitted.length == 1)
				continue;
			else {

				String parameter = splitted[0];

				// dodavanje neke naredbe
				if (parameter.equals("command")) {

					// odvaja se slovo, koje predstavlja pojedinu naredbu, od naredbe
					String[] args = splitted[1].split("\\s+", 2);

					registerCommand(args[0].charAt(0), args[1]);
					continue;
				}

				// postavljanje origina
				if (parameter.equals("origin")) {

					String[] args = splitted[1].split("\\s+");

					double x = Double.parseDouble(args[0]);
					double y = Double.parseDouble(args[1]);

					setOrigin(x, y);
					continue;
				}

				// postavljanje kuta
				if (parameter.equals("angle")) {

					double angle = Double.parseDouble(splitted[1]);

					setAngle(angle);
					continue;
				}

				// posatavljanje jediničnog vektora
				if (parameter.equals("unitLength")) {

					double length = Double.parseDouble(splitted[1]);

					setUnitLength(length);
					continue;
				}

				// postavljanje faktora skaliranja
				if (parameter.equals("unitLengthDegreeScaler")) {

					if (splitted[1].contains("/")) {

						String[] args = splitted[1].split("/");
						setUnitLengthDegreeScaler(Double.parseDouble(args[0]) / Double.parseDouble(args[1]));
						continue;
					} else {

						setUnitLengthDegreeScaler(Double.parseDouble(splitted[1]));
						continue;
					}
				}

				// postavljanje početnog znaka
				if (parameter.equals("axiom")) {

					setAxiom(splitted[1]);
					continue;
				}

				// dodavanje produkcijskih pravila
				if (parameter.equals("production")) {

					String[] args = splitted[1].split("\\s+");

					registerProduction(args[0].charAt(0), args[1]);
				}
			}
		}
		return this;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {

		String[] args = arg1.split("\\s+");

		if (args[0].equals("draw")) {

			actions.put(arg0, new DrawCommand(Double.parseDouble(args[1])));
			return this;
		}

		if (args[0].equals("pop")) {
			actions.put(arg0, new PopCommand());
			return this;
		}

		if(args[0].equals("push")) {
			actions.put(arg0, new PushCommand());
		}

		if (args[0].equals("rotate")) {
			actions.put(arg0, new RotateCommand(Double.parseDouble(args[1])));
			return this;
		}

		if (args[0].equals("skip")) {
			actions.put(arg0, new SkipCommand(Double.parseDouble(args[1])));
			return this;
		}

		if (args[0].equals("scale")) {
			actions.put(arg0, new ScaleCommand(Double.parseDouble(args[1])));
			return this;
		}

		if (args[0].equals("color")) {

			String c = args[1];
			Color color;

			if (c.equals("green")) {
				color = Color.GREEN;
			} else if (c.equals("blue")) {
				color = Color.BLUE;
			} else if (c.equals("red")){
				color = Color.RED;
			}else {
				color = Color.BLACK;
			}

			actions.put(arg0, new ColorCommand(color));
			return this;
		}
		return this;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {

		productions.put(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		angle = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		axiom = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		origin = new Vector2D(arg0, arg1);
		return this;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		unitLength = arg0;
		return this;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		unitLengthDegreeScaler = arg0;
		return this;
	}
}
