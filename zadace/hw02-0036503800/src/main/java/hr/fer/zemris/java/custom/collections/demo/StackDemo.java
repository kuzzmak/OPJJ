package hr.fer.zemris.java.custom.collections.demo;

import java.util.Arrays;

import hr.fer.zemris.java.custom.collections.ObjectStack;

public class StackDemo {

	public static void main(String[] args) {

//		if (args.length != 1)
//			throw new IllegalArgumentException("Pogrešan broj argumenata: " + args.length + ".");
		
		int result = evaluateExpression("8 -2 / -1 *");

		System.out.println(result);
	}
	
	private static String[] parse(String expression) {
		
		String noQuotation = expression.replaceAll("\"", "");
		return noQuotation.split("\\s");
	}
	
	public static int executeOperation(String op, int arg1, int arg2) {
		
		if(op.equals("+")) return arg1 + arg2;
		else if(op.equals("-")) return arg1 - arg2;
		else if(op.equals("/")) return arg1 / arg2;
		else if(op.equals("*")) return arg1 * arg2;
		else return arg1 % arg2;
	}
	
	public static int evaluateExpression(String expression) {
		
		String[] characters = parse(expression);
		ObjectStack stack = new ObjectStack();
		
		for(String s: characters) {
			
			try {
				int number = Integer.valueOf(s);
				stack.push(number);
				
			}catch(NumberFormatException e) {
				
				if(s.equals("/") || s.equals("+") || s.equals("-") || s.equals("*") || s.equals("%")) {
					
					int arg1 = (int)stack.pop();
					int arg2 = (int)stack.pop();
					
					int result = executeOperation(s, arg2, arg1);
					
					stack.push(result);
				}else throw new IllegalArgumentException("Predan neispravan operator.");
			}
		}
		
		return (int)stack.pop();
	}
}
