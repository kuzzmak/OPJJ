package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Naredba za pomak kornjače na neku drugu poziciju prilikom čega se ne crta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class SkipCommand implements Command {

	private double effectiveStep;
	
	/**
	 * Inicijalni koknstruktor.
	 * 
	 * @param step vličina koraka
	 */
	public SkipCommand(double step) {
		effectiveStep = step;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState();

		Vector2D oldPosition = state.getPosition();
		Vector2D direction = state.getDirection();
		double step = state.getStep();

		double x = oldPosition.getX() + direction.getX() * step * effectiveStep;
		double y = oldPosition.getY() + direction.getY() * step * effectiveStep;

		Vector2D newPosition = new Vector2D(x, y);

		state.setPosition(newPosition);
	}
}
