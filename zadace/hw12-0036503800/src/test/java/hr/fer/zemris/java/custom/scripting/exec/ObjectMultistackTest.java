package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ObjectMultistackTest {

	@Test
	public void test1() {
		
		ObjectMultistack multistack = new ObjectMultistack(); 
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000)); 
		multistack.push("year", year); 
		ValueWrapper price = new ValueWrapper(200.51); 
		multistack.push("price", price);
		
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		assertEquals(Double.valueOf(200.51), multistack.peek("price").getValue());
		
		multistack.push("year", new ValueWrapper(Integer.valueOf(1900)));
		assertEquals(Integer.valueOf(1900), multistack.peek("year").getValue());
		
		multistack.peek("year").setValue(((Integer)multistack.peek("year").getValue()).intValue() + 50);
		assertEquals(Integer.valueOf(1950), multistack.peek("year").getValue());
		
		multistack.pop("year");
		assertEquals(Integer.valueOf(2000), multistack.peek("year").getValue());
		
		multistack.peek("year").add("5");
		assertEquals(Integer.valueOf(2005), multistack.peek("year").getValue());
		
		multistack.peek("year").add(5);
		assertEquals(Integer.valueOf(2010), multistack.peek("year").getValue());
		
		multistack.peek("year").add(5.0);
		assertEquals(Double.valueOf(2015), multistack.peek("year").getValue());
	}
	
	@Test
	public void peekNaNull() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertEquals(null, multistack.peek(""));
	}
	
	@Test
	public void prazniStog() {
		ObjectMultistack multistack = new ObjectMultistack();
		assertTrue(multistack.isEmpty(""));
	}
	
	@Test
	public void neprazniStog() {
		ObjectMultistack multistack = new ObjectMultistack(); 
		ValueWrapper year = new ValueWrapper(Integer.valueOf(2000)); 
		multistack.push("year", year); 
		assertFalse(multistack.isEmpty("year"));
	}
}
