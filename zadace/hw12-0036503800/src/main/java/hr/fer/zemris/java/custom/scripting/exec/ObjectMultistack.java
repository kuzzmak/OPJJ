package hr.fer.zemris.java.custom.scripting.exec;

import java.util.HashMap;
import java.util.Map;

/**
 * Razred koji predstavlja strukturu podatka sličnu stogu. Interno se
 * koristi mapa koja za svaku vrstu ključa pamti dodane objekte. Stog
 * može bit prazan za jednu vrstu ključa dok može biti neprazan za
 * drugu vrstu ključa. Moguće je koristiti standardne metode za 
 * manipulaciju stogom.
 * 
 * @author Antonio Kuzminski
 *
 */
public class ObjectMultistack {

	/**
	 * Privatni razred koji ima ulogu jednog unosa pod određenim ključem.
	 * Sastoji se od vrijednosti koja je tipa {@code ValueWrapper} i sljedeće
	 * vrijednosti koja je tipa {@code MultistackEntry}.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private static class MultistackEntry {

		private MultistackEntry next;
		private ValueWrapper value;

		/**
		 * Konstruktor.
		 * 
		 * @param valueWrapper vrijednost koja se pohranjuje
		 */
		public MultistackEntry(ValueWrapper valueWrapper) {
			this.value = valueWrapper;
		}

	}

	private Map<String, MultistackEntry> entries;

	/**
	 * Konstruktor.
	 * 
	 */
	public ObjectMultistack() {
		entries = new HashMap<>();
	}

	/**
	 * Metoda za stavljanje vrijednosti na stog.
	 * 
	 * @param keyName vrijenost ključa pod kojim se vrijednost stavlja na stog
	 * @param valueWrapper vrijednost koja se stavlja na stog
	 */
	public void push(String keyName, ValueWrapper valueWrapper) {

		if (entries.get(keyName) == null) {
			entries.put(keyName, new MultistackEntry(valueWrapper));

		} else {

			MultistackEntry entry = entries.get(keyName);
			MultistackEntry newEntry = new MultistackEntry(valueWrapper);
			newEntry.next = entry;
			entries.put(keyName, newEntry);
		}
	}

	/**
	 * Metoda za micanje najgornje vrijednosti sa stoga pod određenim ključem.
	 * 
	 * @param keyName ključ pod kojim se skida vrijednost sa stoga
	 * @return vrijednost pod ključem {@code keyName}
	 */
	public ValueWrapper pop(String keyName) {

		if (entries.containsKey(keyName)) {

			MultistackEntry entry = entries.get(keyName);

			MultistackEntry nextEntry = entry.next;
			entries.put(keyName, nextEntry);

			return entry.value;
		}

		return null;
	}

	/**
	 * Metoda za dohvat vrijednosti na vrhu stoga pod određenim ključem. 
	 * 
	 * @param keyName ključ pod kojim se dohvaća vrijednost
	 * @return vrijednost pod ključem {@code keyName}
	 */
	public ValueWrapper peek(String keyName) {

		if (entries.containsKey(keyName)) {

			return entries.get(keyName).value;
		}

		return null;
	}

	/**
	 * Metoda za provjeru je li stoga prazan pod ključem {@code keyName}. 
	 * 
	 * @param keyName ključe pod kojim se provjerava je li stog prazan
	 * @return istina ako je stog prazan, laž inače
	 */
	public boolean isEmpty(String keyName) {
		
		return entries.get(keyName) == null;
	}
}
