package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * Razred koji služi za izvođenje postupka prikazivanja fraktala
 * i stavljanje te dohvaćanje stanja kornjače.
 * 
 * @author Antonio Kuzminski
 *
 */
public class Context {
	
	private ObjectStack<TurtleState> contextStack;
	
	/**
	 * Inicijalni konstruktor.
	 * 
	 */
	public Context() {
		contextStack = new ObjectStack<>();
	}

	/**
	 * Dohvat trenutnog stanja kornjače.
	 * 
	 * @return trenutno stanje kornjače.
	 */
	TurtleState getCurrentState() {
		return contextStack.peek();
	}
	

	/**
	 * Stavljanje trenutnoh stanje kornjače na stog.
	 * 
	 * @param state stanje koje se stavlja na stog
	 */
	void pushState(TurtleState state) {
		contextStack.push(state);
	}
	
	/**
	 * Micanje zadnjeg stanja na stogu.
	 * 
	 */
	void popState() {
		contextStack.pop();
	}
}
