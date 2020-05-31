package hr.fer.zemris.java.custom.scripting.exec;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ValueWrapperTest {
	
	@Test
	public void nullJEdanIDrugi() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(null);
		v1.add(v2.getValue()); 
		assertEquals(Integer.valueOf(0), v1.getValue());
		assertEquals(null, v2.getValue());
	}
	
	@Test
	public void doubleIInteger() {
		
		ValueWrapper v3 = new ValueWrapper("1.2E1");
		ValueWrapper v4 = new ValueWrapper(Integer.valueOf(1));
		v3.add(v4.getValue());
		assertEquals(Double.valueOf(13), v3.getValue());
		assertEquals(Integer.valueOf(1), v4.getValue());
	}
	
	@Test
	public void obaIteger() {
		ValueWrapper v5 = new ValueWrapper("12");
		ValueWrapper v6 = new ValueWrapper(Integer.valueOf(1));
		v5.add(v6.getValue()); 
		assertEquals(Integer.valueOf(13), v5.getValue());
		assertEquals(Integer.valueOf(1), v6.getValue());
	}
	
	@Test
	public void exception() {
		ValueWrapper v7 = new ValueWrapper("Ankica");
		ValueWrapper v8 = new ValueWrapper(Integer.valueOf(1));
		assertThrows(RuntimeException.class, () -> v7.add(v8.getValue())); 
	}
	
	@Test
	public void nullPaInteger() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2);
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(2), v1.getValue());
	}
	
	@Test
	public void integerPaNull() {
		ValueWrapper v1 = new ValueWrapper(null);
		ValueWrapper v2 = new ValueWrapper(2);
		v2.add(v1.getValue());
		assertEquals(Integer.valueOf(2), v2.getValue());
	}
	
	@Test
	public void brojcaniStringovi() {
		ValueWrapper v1 = new ValueWrapper("2");
		ValueWrapper v2 = new ValueWrapper("3");
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(5), v1.getValue());
	}
	
	@Test
	public void zbrajanjeDvajuIntegera() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(3);
		v1.add(v2.getValue());
		assertEquals(Integer.valueOf(5), v1.getValue());
	}
	
	@Test
	public void zbrajanjeDvajuDoubla() {
		ValueWrapper v1 = new ValueWrapper(2.0);
		ValueWrapper v2 = new ValueWrapper(3.0);
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(5), v1.getValue());
	}
	
	@Test
	public void zbrajanjeDoublaIIntegera() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(3.0);
		v1.add(v2.getValue());
		assertEquals(Double.valueOf(5), v1.getValue());
	}
	
	@Test
	public void mnozenjeIntegeraIDoubla() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(3.0);
		v1.multiply(v2.getValue());
		assertEquals(Double.valueOf(6), v1.getValue());
	}
	
	@Test
	public void mnozenjeS0() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(0);
		v1.multiply(v2.getValue());
		assertEquals(Integer.valueOf(0), v1.getValue());
	}
	
	@Test
	public void usporedbaIntegera() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(0);
		assertTrue(v1.numCompare(v2.getValue()) > 0);
	}
	
	@Test
	public void usporedbaIntegeraIDoubla() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(1.0);
		assertTrue(v1.numCompare(v2.getValue()) > 0);
	}
	
	@Test
	public void usporedbaJednakih() {
		ValueWrapper v1 = new ValueWrapper(2);
		ValueWrapper v2 = new ValueWrapper(2.0);
		assertTrue(v1.numCompare(v2.getValue()) == 0);
	}
}
