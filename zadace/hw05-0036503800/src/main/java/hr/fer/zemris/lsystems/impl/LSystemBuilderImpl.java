package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

public class LSystemBuilderImpl implements LSystemBuilder {

	private Dictionary<Character, Command> actions = new Dictionary<>();
	private Dictionary<String, Command> productions = new Dictionary<>();
	
	Context context;
	private LSystem system;

	class LSystemImpl implements LSystem {

		@Override
		public void draw(int arg0, Painter arg1) {

			context = new Context();
			// početno stanje
			context.pushState(new TurtleState());

		}

		@Override
		public String generate(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

	}

	@Override
	public LSystem build() {
		
		system = new LSystemImpl();
		
		return system;
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {

		for (String s : arg0) {

			String[] splitted = s.strip().split("\\s+", 2);
			// prazni redak
			if (splitted.length == 1)
				continue;
			else {

				String parameter = splitted[0];

				if (parameter.equals("command")) {

					registerCommand(splitted[0].charAt(0), splitted[1]);
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


			}
			System.out.println(s);
		}

		return null;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {

		String[] args = arg1.split("\\s+");
		
		if(args[0].equals("draw")) {
			
			actions.put(arg0, new DrawCommand(Double.parseDouble(args[1])));
			return this;
		}
		
		if(args[0].equals("pop")) {
			actions.put(arg0, new PopCommand());
			return this;
		}
		
//		if(args[0].equals("push")) {
//			actions.put(arg0, new PushCommand(context, ));
//		}
		
		if(args[0].equals("rotate")) {
			actions.put(arg0, new RotateCommand(Double.parseDouble(args[1])));
			return this;
		}
		
		if(args[0].equals("skip")) {
			actions.put(arg0, new SkipCommand(Double.parseDouble(args[1])));
			return this;
		}
		
		if(args[0].equals("scale")) {
			actions.put(arg0, new ScaleCommand(Double.parseDouble(args[1])));
			return this;
		}
		
		if(args[0].equals("color")) {
			
			String c = args[1];
			Color color;
			
			if(c.equals("green")) {
				color = Color.GREEN;
			}else if(c.equals("blue")) {
				color = Color.BLUE;
			}else {
				color = Color.RED;
			}
			
			actions.put(arg0, new ColorCommand(color));
			return this;
		}
		return null;
	}

	@Override
	public LSystemBuilder registerProduction(char arg0, String arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder setAngle(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder setAxiom(String arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder setOrigin(double arg0, double arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder setUnitLength(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double arg0) {
		// TODO Auto-generated method stub
		return null;
	}
}
