package hr.fer.zemris.lsystems.impl;

import java.awt.Color;

import hr.fer.zemris.math.Vector2D;

/**
 * Razred za prikaz nekog stanja kornjače. Svako stanje se sastoji
 * od pozicije, smjera, boje i koraka. 
 * 
 * @author Antonio Kuzminski
 *
 */
public class TurtleState {

	private Vector2D position;
	private Vector2D direction;
	private Color color;
	private double step;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param position pozicija novog stanja
	 * @param direction smjer novog stanja
	 * @param color boja novog stanja
	 * @param step korak novog stanja
	 */
	public TurtleState(Vector2D position, Vector2D direction, Color color, double step) {
		this.position = position;
		this.direction = direction;
		this.color = color;
		this.step = step;
	}
	
	/**
	 * Inicijalni konstruktor sa zadanim paramatrima.
	 * 
	 */
	public TurtleState() {
		this.position = new Vector2D(0, 0);
		this.direction = new Vector2D(1, 0);
		this.color = Color.BLACK;
		this.step = 0.5;
	}
	
	/**
	 * Metoda za stvaranje kopije trenutnog stanja.
	 * 
	 * @return novo stanje kornjače koje je kopija trenutnog
	 */
	public TurtleState copy() {
		return new TurtleState(position, direction, color, step);
	}

	public Vector2D getPosition() {
		return position;
	}

	public Vector2D getDirection() {
		return direction;
	}

	public Color getColor() {
		return color;
	}

	public double getStep() {
		return step;
	}

	public void setPosition(Vector2D position) {
		this.position = position;
	}

	public void setDirection(Vector2D direction) {
		this.direction = direction;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setStep(double step) {
		this.step = step;
	}
}
