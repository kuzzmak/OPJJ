package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

public class TurtleState {

	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double step;
	
	public TurtleState copy() {
		return new TurtleState();
	}
}
