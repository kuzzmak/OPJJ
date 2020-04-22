package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Naredba za promjenu veličine trenutnog koraka na način 
 * da se pomnoži nekim faktorom.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ScaleCommand implements Command {

	private double scale;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param scale faktor kojim se skalira trenutni korak
	 */
	public ScaleCommand(double scale) {
		this.scale = scale;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		
		double currentStep = ctx.getCurrentState().getStep();
		
		ctx.getCurrentState().setStep(scale * currentStep);
	}
}
