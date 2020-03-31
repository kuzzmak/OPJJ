package hr.fer.zemris.java.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class ArrayIndexedCollectionTest {

	@Nested
	class testKonstruktora {

		@Test
		public void kapacitetManjiOd1() {

			assertThrows(IllegalArgumentException.class, () -> new ArrayIndexedCollection(-1));
		}

		@Test
		public void zadaniKapacitet() {
			ArrayIndexedCollection c = new ArrayIndexedCollection(10);
			Object[] elements = c.toArray();
			assertEquals(10, elements.length);
		}

		@Test
		public void nullKolekcija() {
			assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection(null));
		}

		@Test
		public void bezArgumenata() {
			int size = new ArrayIndexedCollection().size();
			assertEquals(0, size);
		}

		@Test
		public void nepraznaKolekcija() {

			ArrayIndexedCollection c = new ArrayIndexedCollection();
			c.add(5);
			c.add(6);
			c.add(7);

			int size = new ArrayIndexedCollection(c).size();
			assertEquals(3, size);
		}
	}

	@Nested
	class testMetoda {

		// add metoda
		@Test
		public void predanNull() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			assertThrows(NullPointerException.class, () -> c.add(null));
		}

		@Test
		public void predanObjekt() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			c.add(1);
			assertEquals(1, c.size());
		}

		@Test
		public void punaKolekcija() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.add(16);
			assertEquals(17, c.size());
		}

		// addAll metoda
		@Test
		public void nepraznaKolekcija() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();
			c1.add(1);
			ArrayIndexedCollection c2 = new ArrayIndexedCollection();
			c2.add(2);
			c2.addAll(c1);
			assertEquals(2, c2.size());
		}

		@Test
		public void praznaKolekcija() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();
			ArrayIndexedCollection c2 = new ArrayIndexedCollection();
			c2.add(2);
			c2.addAll(c1);
			assertEquals(1, c2.size());
		}

		@Test
		public void predanaNullKolekcija() {
			ArrayIndexedCollection c1 = null;
			ArrayIndexedCollection c2 = new ArrayIndexedCollection();
			c2.add(2);
			assertThrows(NullPointerException.class, () -> c2.addAll(c1));
		}

		@Test
		public void dodanaKolekcijaVecaOdTrenutnogLimita() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();
			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			ArrayIndexedCollection c2 = new ArrayIndexedCollection();
			for (int i = 0; i < 10; i++) {
				c2.add(i);
			}
			c2.addAll(c1);
			assertEquals(20, c2.size());
		}

		// toArray metoda
		@Test
		public void toArrayTestZaPolje() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 10; i++) {
				c.add(i);
			}
			Object[] elements = c.toArray();
			assertTrue(elements.getClass().isArray());
		}

		@Test
		public void ispravnostElemenata() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 10; i++) {
				c.add(i);
			}
			Object[] elements = c.toArray();

			boolean correct = true;
			for (int i = 0; i < 10; i++) {
				if (i != (int) elements[i]) {
					correct = false;
					break;
				}
			}

			assertTrue(correct);
		}

		// size metoda
		@Test
		public void velicina0() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			assertEquals(0, c.size());
		}

		@Test
		public void velicinaMaxInicijalno() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			assertEquals(16, c.size());
		}

		@Test
		public void velicinaZa1ViseOdMaxInicijalno() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.add(1);
			assertEquals(17, c.size());
		}

		// isEmpty metoda
		@Test
		public void isEmptyTestPrazna() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			assertTrue(c.isEmpty());
		}

		@Test
		public void isEmptyTestNeprazna() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			c.add(1);
			assertFalse(c.isEmpty());
		}

		// contains metoda
		@Test
		public void sadrziNull() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			assertFalse(c.contains(null));
		}

		@Test
		public void sadrziBroj() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			c.add(1);
			assertTrue(c.contains(1));
		}

		@Test
		public void sadrziString() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			c.add("da");
			assertTrue(c.contains("da"));
		}

		// remove metoda
		@Test
		public void micanjeNepostojeceg() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.remove("a");
			assertTrue(c.size() == 16);
		}

		@Test
		public void micanjePostojeceg() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.remove(6);
			assertTrue(c.size() == 15);
		}

		@Test
		public void micanjePrvog() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.remove(0);
			assertFalse(c.contains(0));
		}

		@Test
		public void micanjeZadnjeg() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.remove(15);
			assertFalse(c.contains(15));
		}

		@Test
		public void micanjeElementaNakonProsirenjaPolja() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();
			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			ArrayIndexedCollection c2 = new ArrayIndexedCollection();
			for (int i = 10; i < 20; i++) {
				c2.add(i);
			}
			c2.addAll(c1);

			c2.remove(15);
			assertFalse(c2.contains(15));
		}

		@Test
		public void micanjeNull() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 10; i++) {
				c.add(i);
			}
			assertFalse(c.remove(null));
		}

		// clear metoda
		@Test
		public void brisanjePuneKolekcije() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 16; i++) {
				c.add(i);
			}
			c.clear();

			assertTrue(c.size() == 0);
		}

		@Test
		public void ocuvanjeVelicine() {
			ArrayIndexedCollection c = new ArrayIndexedCollection();
			for (int i = 0; i < 20; i++) {
				c.add(i);
			}
			c.clear();

			Object[] elements = c.toArray();
			assertTrue(elements.length == 32);
		}

		// foreach metoda
		@Test
		public void foreachTest() {

			ArrayIndexedCollection c1 = new ArrayIndexedCollection();

			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}

			ArrayIndexedCollection c2 = new ArrayIndexedCollection();

			class localProcessor extends Processor {

				@Override
				public void process(Object value) {
					c2.add(value);
				}
			}

			c1.forEach(new localProcessor());

			assertTrue(c2.size() == 10);
		}

		// insert metoda
		@Test
		public void ubacivanjeNull() {

			assertThrows(NullPointerException.class, () -> new ArrayIndexedCollection().insert(null, 0));
		}

		@Test
		public void ubacivanjeIzvanDopustenihGranica() {

			assertThrows(IndexOutOfBoundsException.class, () -> new ArrayIndexedCollection().insert(0, -1));
		}

		@Test
		public void ubacivanjeIzvanDopustenihGranica2() {

			assertThrows(IndexOutOfBoundsException.class, () -> new ArrayIndexedCollection().insert(0, 1));
		}

		@Test
		public void ispravnoUbacivanje() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();

			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			
			c1.insert(69, 0);
			
			assertTrue(c1.contains(69));
		}
		
		// get metoda
		@Test
		public void indeksVanGranica() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();

			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			
			assertThrows(IndexOutOfBoundsException.class, () -> c1.get(10));
		}
		
		@Test
		public void ispravanIndeks() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();

			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			
			Object n = c1.get(1);
			
			assertEquals(1, n);
		}
		
		// indexOf metoda
		@Test
		public void postojeciElement() {
			
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();

			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			
			int n = c1.indexOf(1);
			
			assertEquals(1, n);
		}
		
		@Test
		public void nepostojeciElement() {
			ArrayIndexedCollection c1 = new ArrayIndexedCollection();

			for (int i = 0; i < 10; i++) {
				c1.add(i);
			}
			
			int n = c1.indexOf(10);
			
			assertEquals(-1, n);
		}
	}
}
