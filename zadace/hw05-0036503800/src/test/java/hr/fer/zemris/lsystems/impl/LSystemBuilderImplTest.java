package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.java.custom.collections.Dictionary;

public class LSystemBuilderImplTest {
	
	private Dictionary<Character, String> productions = new Dictionary<>();
	private String axiom = "";
	
	public String generate(int arg0) {
		
		if(arg0 == 0) return axiom;
		if(arg0 == 1) return productions.get(axiom.charAt(0));
		
		StringBuilder sb = new StringBuilder();
		// dodavanje produkcije aksioma
		sb.append(productions.get(axiom.charAt(0)));
		
		StringBuilder nextProduction = new StringBuilder();
		
		for(int i = 1; i < arg0; i++) {
			
			for(int j = 0; j < sb.length(); j++) {
				
				// ako smo naišli na neki znak koji ide u produkciju
				if(productions.get(sb.charAt(j)) != null) {
					
					nextProduction.append(productions.get(sb.charAt(j)));
					
				}else nextProduction.append(sb.charAt(j)); // neki operator/akcija
			}
			
			sb = nextProduction;
			nextProduction = new StringBuilder();
		}
		
		return sb.toString();
	}
	
	public void fillData() {
		
		productions.put('F', "F+F--F+F");
		axiom = "F";
	}
	
	@Test
	public void generate0() {
		
		fillData();
		
		String generated = generate(0);
		
		assertEquals("F", generated);
	}
	
	@Test
	public void generate1() {
		
		fillData();
		
		String generated = generate(1);
		
		assertEquals("F+F--F+F", generated);
	}
	
	@Test
	public void generate2() {
		
		fillData();
		
		String generated = generate(2);
		
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", generated);
	}
}
