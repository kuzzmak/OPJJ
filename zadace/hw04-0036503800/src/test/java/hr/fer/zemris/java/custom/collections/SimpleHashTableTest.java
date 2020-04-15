package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class SimpleHashTableTest {

	@Test
	public void inicijalniKonstruktor() {
		
		SimpleHashTable<String, Integer> sht = new SimpleHashTable<>();
	}
	
	@Test
	public void stavljanjeNullKljučaUTablicu() {
		
		SimpleHashTable<Object, Object> sht = new SimpleHashTable<>();
		assertThrows(NullPointerException.class, () -> sht.put(null, 1));
	}
	
	@Test
	public void stavljanjeNormalneVrijednosti() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertTrue(sht.containsKey("1"));
		assertTrue(sht.containsValue("Jedan"));
		assertTrue(sht.size() == 1);
	}
	
	@Test
	public void gazenjeStareVrijednosti() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("1", "Dva");
		assertTrue(sht.containsKey("1"));
		assertTrue(sht.containsValue("Dva"));
		assertTrue(sht.size() == 1);
	}
}
