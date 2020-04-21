package hr.fer.zemris.lsystems.impl.commands;

import java.awt.Color;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Vrsta naredbe koja mijenja boju linije kojom kornjača crta.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ColorCommand implements Command{

	private Color color;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param color boja kojom se crta
	 */
	public ColorCommand(Color color) {
		this.color = color;
	}
	
	@Override
	public void execute(Context ctx, Painter painter) {
		ctx.getCurrentState().setColor(color);
	}

	@Override
	public String toString() {
		return "ColorCommand [color=" + color + "]";
	}
}
