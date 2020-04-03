package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {

		if (args.length != 1)
			throw new IllegalArgumentException("Pogrešan broj argumenata: " + args.length + ".");
		
		int result = evaluateExpression(args[0]); 

		System.out.println("Expression evaluates to " + result + ".");
	}
	
	/**
	 * Metoda za podjelu ulaznog string u pojedine znakove.
	 * 
	 * @param expression string koji se parsira
	 * @return polje znakova
	 */
	private static String[] parse(String expression) {
		
		String noQuotation = expression.replaceAll("\"", "");
		return noQuotation.split("\\s");
	}
	
	/**
	 * Metoda koja izvađa operaciju <code>op</code> nad argumentima 
	 * <code>arg1</code> i <code>arg2</code>.
	 * 
	 * @param op operacija koja se izvodi
	 * @param arg1 prvi argument
	 * @param arg2 drugi argument
	 * @return rezultat operacije
	 */
	public static int executeOperation(String op, int arg1, int arg2) {
		
		if(op.equals("+")) return arg1 + arg2;
		else if(op.equals("-")) return arg1 - arg2;
		else if(op.equals("/")) return arg1 / arg2;
		else if(op.equals("*")) return arg1 * arg2;
		else return arg1 % arg2;
	}
	
	/**
	 * Metoda za evaluiranje izraza <code>expression</code> u postfix notaciji.
	 * 
	 * @param expression izraz koji se evaluira
	 * @throws ArithmeticException u slučaju dijeljenja s nulom
	 * @throws IllegalArgumentException u slučaju neispravnog izraza
	 * @return rezultat izvođenja izraza
	 */
	public static int evaluateExpression(String expression) {
		
		String[] characters = parse(expression);
		ObjectStack stack = new ObjectStack();
		
		for(String s: characters) {
			
			try {
				int number = Integer.valueOf(s);
				stack.push(number);
				
			}catch(NumberFormatException e) {
				
				if(s.equals("/") || s.equals("+") || s.equals("-") || s.equals("*") || s.equals("%")) {
					
					try {
						int arg1 = (int)stack.pop();
						int arg2 = (int)stack.pop();
						
						if(arg1 == 0) throw new ArithmeticException("Dijeljenje s nulom.");
						
						int result = executeOperation(s, arg2, arg1);
						
						stack.push(result);
						
					}catch (EmptyStackException ex) {
						ex.printStackTrace();
					}
					
				}else throw new IllegalArgumentException("Predan neispravan operator.");
			}
		}
		
		if(stack.size() != 1) throw new IllegalArgumentException("Predan neispravan string.");
		
		return (int)stack.pop();
	}
}
