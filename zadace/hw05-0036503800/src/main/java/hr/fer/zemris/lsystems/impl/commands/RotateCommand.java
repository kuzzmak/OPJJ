package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.math.Vector2D;

/**
 * Naredba za rotaciju kornjače.
 * 
 * @author Antonio Kuzminski
 *
 */
public class RotateCommand implements Command {
	
	private double angle;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param angle kut zakreta kornjače u stupnjevima
	 */
	public RotateCommand(double angle) {
		this.angle = angle;
	}

	@Override
	public void execute(Context ctx, Painter painter) {
		
		Vector2D currentDirection = ctx.getCurrentState().getDirection();
		
		ctx.getCurrentState().setDirection(currentDirection.rotated(angle));
	}
}
