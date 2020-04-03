package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class LinkedListIndexedCollectionTest {

	@Nested
	class testKonstruktora {

		@Test
		public void praznaKolekcija() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			assertEquals(0, c.size());
		}
		
		@Test
		public void prekoDrugeKolekcije() {
			LinkedListIndexedCollection c1 = new LinkedListIndexedCollection();
			c1.add(1);
			c1.add(2);
			
			LinkedListIndexedCollection c2 = new LinkedListIndexedCollection(c1);
			Object[] elements = c2.toArray();
			
			assertEquals(2, c2.size());
			assertEquals(1, elements[0]);
			assertEquals(2, elements[1]);
		}
		
		@Test
		public void prekoKolekcijeDrugogTipa() {
			ArrayIndexedCollection a = new ArrayIndexedCollection();
			a.add(1);
			a.add(2);
			
			LinkedListIndexedCollection c = new LinkedListIndexedCollection(a);
			Object[] elements = c.toArray();
			assertEquals(2, c.size());
			assertEquals(1, elements[0]);
			assertEquals(2, elements[1]);
		}
	}

	@Nested
	class testMetoda {

		// size metoda
		@Test
		public void praznaKolekcija() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();

			assertEquals(0, c.size());
		}

		// add metoda
		@Test
		public void predanNull() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			assertThrows(NullPointerException.class, () -> c.add(null));
		}

		@Test
		public void dodavanjePrvogElementa() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertEquals(1, c.size());
		}

		// get metoda
		@Test
		public void dohvatNepostojecegIndeksa() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertThrows(IndexOutOfBoundsException.class, () -> c.get(2));
		}
		
		@Test
		public void dohvatPostojecegIndeksa() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertEquals(1, c.get(0));
		}
		
		// isEmpty metoda
		@Test
		public void inicijalnaPraznaKolekcija() {
			assertTrue(new LinkedListIndexedCollection().isEmpty());
		}
		
		@Test
		public void jedanElementUKolekciji() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertFalse(c.isEmpty());
		}
		
		// contains metoda
		@Test
		public void nepostojecaVrijednost() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertFalse(c.contains(0));
		}
		
		@Test
		public void postojecaVrijednost() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertTrue(c.contains(1));
		}
		
		// clear metoda
		@Test
		public void testClear() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			c.add(1);
			c.add(1);
			c.clear();
			assertTrue(c.getFirst() == null);
			assertTrue(c.getLast() == null);
		}
		
		// remove metoda (indeks)
		@Test
		public void brisanjeNepostojecegIndeksa() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			assertThrows(IndexOutOfBoundsException.class, () -> c.remove(2));
		}
		
		@Test
		public void ispravanIndeks() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			c.remove(0);
			assertEquals(0, c.size());
		}
		
		// remove metoda (objekt)
		@Test
		public void brisanjePrvog() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			c.add(2);
			c.remove(Integer.valueOf(1));
			assertEquals(1, c.size());
		}
		
		@Test
		public void brisanjeZadnjeg() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			c.add(2);
			c.remove(Integer.valueOf(2));
			assertEquals(1, c.size());
		}
		
		@Test
		public void brisanjeSvih() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			c.add(2);
			c.remove(Integer.valueOf(2));
			c.remove(Integer.valueOf(1));
			assertEquals(0, c.size());
		}
		
		// toArray metoda
		@Test
		public void toarrayTest() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			c.add(1);
			c.add(2);
			Object[] array = c.toArray();
			assertEquals(2, array.length);
			assertEquals(1, (int)array[0]);
			assertEquals(2, (int)array[1]);
		}
		
		@Test
		public void toArrayPraznaKolekcija() {
			LinkedListIndexedCollection c = new LinkedListIndexedCollection();
			Object[] array = c.toArray();
			assertEquals(0, array.length);
		}
		
		// forEach metoda
		@Test
		public void processTest() {
			LinkedListIndexedCollection c1 = new LinkedListIndexedCollection();
			c1.add(1);
			c1.add(2);
			c1.add(3);
			
			LinkedListIndexedCollection c2 = new LinkedListIndexedCollection();
			
			c1.forEach(new Processor() {
				@Override
				public void process(Object value) {
					c2.add(String.valueOf(value));
				} 
			});
			
			assertEquals(3, c2.size());
			
			c2.forEach(new Processor() {
				@Override
				public void process(Object value) {
					assertTrue(value instanceof String);
				} 
			});
		}
		
		// addAll metoda
		@Test
		public void addAllTest() {
			LinkedListIndexedCollection c1 = new LinkedListIndexedCollection();
			c1.add(1);
			c1.add(2);
			c1.add(3);
			LinkedListIndexedCollection c2 = new LinkedListIndexedCollection();
			c2.addAll(c1);
			
			for(int i = 0; i < 3; i++) {
				assertEquals(c1.get(i), c2.get(i));
			}
		}
		
		@Test
		public void praznaKolekcijaUAddAll() {
			LinkedListIndexedCollection c1 = new LinkedListIndexedCollection();
			assertThrows(NullPointerException.class, () -> c1.addAll(null));
		}
		
		// indexOf metoda
		@Test
		public void nullPredan() {
			assertEquals(-1, new LinkedListIndexedCollection().indexOf(null));
		}
		
		@Test
		public void postojecaVrijednostUListi() {
			LinkedListIndexedCollection c1 = new LinkedListIndexedCollection();
			c1.add(1);
			assertEquals(0, c1.indexOf(1));
		}
		
		@Test
		public void nepostojecaVrijednostUListi() {
			LinkedListIndexedCollection c1 = new LinkedListIndexedCollection();
			c1.add(1);
			assertEquals(-1, c1.indexOf(2));
		}
	}
}
