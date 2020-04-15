package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;


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
	
	@Test
	public void dohvatVrijednostiPodNepostojecimKlKjucem() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		String value = sht.get("2");
		assertEquals(null, value);
	}
	
	@Test
	public void dohvatVrijednostiPostojecegKljuca() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertEquals("Jedan", sht.get("1"));
	}
	
	@Test
	public void dohvatPrekoNull() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertEquals(null, sht.get(null));
	}
	
	@Test
	public void sadrziKljuc() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertTrue(sht.containsKey("1"));
	}
	
	@Test
	public void sadrziNull() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertFalse(sht.containsKey(null));
	}
	
	@Test
	public void sadrziVrijednost() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertTrue(sht.containsValue("Jedan"));
	}
	
	@Test
	public void sadrziNullVrijednost() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", null);
		assertTrue(sht.containsValue(null));
	}
	
	@Test
	public void sadrziVrijednost2() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		assertFalse(sht.containsValue("1"));
	}
	
	@Test
	public void micanjePostojecegKljuca() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", "Dva");
		sht.put("3", "Tri");
		sht.remove("2");
		assertTrue(sht.size() == 2);
		assertFalse(sht.containsKey("2"));
	}
	
	@Test
	public void micanjeNepostojecegKljuca() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", "Dva");
		sht.remove("3");
		assertTrue(sht.size() == 2);
	}
	
	@Test
	public void testPrazneTablice() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		assertTrue(sht.isEmpty());
	}
	
	@Test
	public void testIteratora() {
		
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", "Dva");
		sht.put("3", "Tri");
		
		Iterator<SimpleHashTable.TableEntry<String, String>> iter = sht.iterator();
		assertEquals(sht.get("1"), iter.next().getValue());
		assertEquals(sht.get("2"), iter.next().getValue());
		assertEquals(sht.get("3"), iter.next().getValue());
	}
	
	@Test
	public void micanjeElemenataIteratorom() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", "Dva");
		sht.put("3", "Tri");
		Iterator<SimpleHashTable.TableEntry<String, String>> iter = sht.iterator();
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
		}
		assertTrue(sht.isEmpty());
	}
	
	@Test
	public void pokusajBrisanjaDvaputZaRedom() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", "Dva");
		sht.put("3", "Tri");
		Iterator<SimpleHashTable.TableEntry<String, String>> iter = sht.iterator();
		while(iter.hasNext()) {
			iter.next();
			iter.remove();
			assertThrows(IllegalStateException.class, () -> iter.remove());
		}
	}
	
	@Test
	public void nedopustenaModifikacijeIzvana() {
		SimpleHashTable<String, String> sht = new SimpleHashTable<>();
		sht.put("1", "Jedan");
		sht.put("2", "Dva");
		sht.put("3", "Tri");
		
		assertThrows(ConcurrentModificationException.class, new Executable() {
            
           @Override
           public void execute() throws Throwable {
        	   Iterator<SimpleHashTable.TableEntry<String, String>> iter = sht.iterator();
        	   while(iter.hasNext()) {
        		   iter.next();
        		   sht.remove("3");
        	   }
           }
       });
	}
}
