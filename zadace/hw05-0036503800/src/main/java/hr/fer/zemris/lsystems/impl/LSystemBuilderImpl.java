package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.Dictionary;
import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.Painter;

public class LSystemBuilderImpl implements LSystemBuilder {

	private Dictionary<String, Command> actions = new Dictionary<>();
	private Dictionary<String, Command> productions = new Dictionary<>();
	
	class LSystemImpl implements LSystem{

		@Override
		public void draw(int arg0, Painter arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public String generate(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
	@Override
	public LSystem build() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder configureFromText(String[] arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LSystemBuilder registerCommand(char arg0, String arg1) {
		// TODO Auto-generated method stub
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
