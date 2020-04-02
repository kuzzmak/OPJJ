package hr.fer.zemris.java.custom.collections;

import java.util.Arrays;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class test {

	public static void main(String[] args) {

//		ComplexNumber c = ComplexNumber.fromMagnitudeAndAngle(1, Math.toRadians(30));
//		System.out.println(c);

		String[] numbers = new String[] {"+351", "-351", "3.15", "-3.15", "1+i", "+1 +i", "1 + i", "-1 - i",
				"-1-i", "+1.1i", "-1.1i", "2-1.1i", "+2.1+i", "-12.1-1.9i"};
		
//		for(String s: numbers) {
//			parse(s);
//		}

		ComplexNumber c1 = ComplexNumber.parse("6-i");
//		ComplexNumber c2 = ComplexNumber.parse("6-i");
//		System.out.println(c1.power(3));
		
		int n = 1;
		ComplexNumber[] roots = c1.root(n);
		
		for(int i = 0; i < n; i++) {
			System.out.println(roots[i]);
		}
		
//		System.out.println(Math.toDegrees(parse("0").getAngle()));
//		System.out.println(Math.atan(0/0));
	}

	
}
