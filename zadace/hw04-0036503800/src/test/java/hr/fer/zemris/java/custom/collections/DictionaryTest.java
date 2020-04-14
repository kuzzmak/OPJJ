package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class DictionaryTest {

	@Test
	public void inicijalizacijaRjecnika() {
		Dictionary<String, String> dict = new Dictionary<>();
		assertEquals(0, dict.size());
		assertTrue(dict.isEmpty());
	}
	
	@Test
	public void stavljanjeNepostojeceVrijednosti() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("1", "jedan");
		assertFalse(dict.isEmpty());
	}
	
	@Test
	public void stavljanjePostojeceVrijednosti() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("1", "jedan");
		dict.put("1", "dva");
		assertTrue(dict.size() == 1);
		assertTrue(dict.get("1").equals("dva"));
	}
	
	@Test
	public void provjeraVrijednostiURjecniku() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("1", "jedan");
		assertTrue(dict.get("1").equals("jedan"));
	}
	
	@Test
	public void predajaNullVrijednosti() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("1", "jedan");
		assertTrue(dict.get(1) == null);
	}
	
	@Test
	public void dohvatPrekoNull() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("1", "jedan");
		assertTrue(dict.get(null) == null);
	}
	
	@Test
	public void stavljanjeNullRjecnikKaoKlju() {
		Dictionary<String, String> dict = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dict.put(null, "jedan"));
	}
	
	@Test
	public void stavljanjeNullKaoVrijednosti() {
		Dictionary<String, String> dict = new Dictionary<>();
		dict.put("1", null);
		assertTrue(dict.get("1") == null);
	}
}
