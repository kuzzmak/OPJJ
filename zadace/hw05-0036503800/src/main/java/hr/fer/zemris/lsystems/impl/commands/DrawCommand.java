package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;
import hr.fer.zemris.math.Vector2D;

/**
 * Naredba za jedan korak crtanja kornjače.
 * 
 * @author Antonio Kuzminski
 *
 */
public class DrawCommand implements Command {

	private double effectiveStep;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param step veličina koraka kornjače
	 */
	public DrawCommand(double step) {
		effectiveStep = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		
		TurtleState state = ctx.getCurrentState();
		
		Vector2D oldPosition = state.getPosition();
		Vector2D direction = state.getDirection();
		double step = state.getStep();
		
		double x = oldPosition.getX() + 
				+ direction.getX() * step * effectiveStep;
		double y = oldPosition.getY() + 
				 direction.getY() * step * effectiveStep;
		
		Vector2D newPosition = new Vector2D(x, y);
		
		state.setPosition(newPosition);
		
		painter.drawLine(oldPosition.getX(), 
				oldPosition.getY(), 
				newPosition.getX(), 
				newPosition.getY(), 
				state.getColor(), 
				1f);
	}

	@Override
	public String toString() {
		return "DrawCommand [effectiveStep=" + effectiveStep + "]";
	}
}
