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
			//TODO popraviti ovo
			assertEquals(0, c.size());
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
		
		
	}
}
