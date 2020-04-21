package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Naredba za jedan korak crtanja kornjače.
 * 
 * @author Antonio Kuzminski
 *
 */
public class DrawCommand implements Command {

	private double step;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param step veličina koraka kornjače
	 */
	public DrawCommand(double step) {
		this.step = step;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		
		Vector2D oldPosition = ctx.getCurrentState().getPosition();
		Vector2D direction = ctx.getCurrentState().getPosition();

		Vector2D newPosition = new Vector2D(oldPosition.getX() + step * direction.getX(),
				oldPosition.getY() + step * direction.getY());
		
		painter.drawLine(oldPosition.getX(), 
				oldPosition.getY(), 
				newPosition.getX(), 
				newPosition.getY(), 
				ctx.getCurrentState().getColor(), 
				1f);
	}

}
