package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Naredba za stavljanje kopije trenutnog stanja na stog.
 * 
 * @author Antonio Kuzminski
 *
 */
public class PushCommand implements Command {

	@Override
	public void execute(Context ctx, Painter painter) {

		TurtleState currentState = ctx.getCurrentState();
		ctx.pushState(currentState.copy());
	}
}
