package hr.fer.zemris.java.gui.prim;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Model liste koja ima mogućnost generiranja prim brojeva.
 * 
 * @author Antonio Kuzminski
 *
 */
public class PrimListModel implements ListModel<Integer> {

	private int currentPrime = 1;
	private int size = 1;
	private ArrayList<Integer> primes;
	private List<ListDataListener> listeners = new ArrayList<>();
	
	/**
	 * Konstruktor.
	 * 
	 */
	public PrimListModel() {
		primes = new ArrayList<>();
		primes.add(currentPrime);
	}
	
	@Override
	public int getSize() {
		return size;
	}

	@Override
	public Integer getElementAt(int index) {
		return primes.get(index);
	}

	@Override
	public void addListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.add(l);
	}

	@Override
	public void removeListDataListener(ListDataListener l) {
		listeners = new ArrayList<>(listeners);
		listeners.remove(l);
	}
	
	/**
	 * Metoda za dodavanje prim broja modelu liste.
	 * 
	 * @param prime broj koji se dodaje
	 */
	private void add(Integer prime) {
		
		int position = primes.size();
		primes.add(prime);
		size++;
		
		ListDataEvent event = new ListDataEvent(this, ListDataEvent.INTERVAL_ADDED, position, position);
		listeners.forEach(l -> l.intervalAdded(event));
	}
	
	/**
	 * Funkcija za provjer je li broj prim.
	 * 
	 * @param n broj za koji se provjerava je li prim 
	 * @return istina ako je broj prim, laž inače
	 */
	private boolean isPrime(int n) { 
	   
	    if (n <= 1) 
	        return false; 
	  
	    for (int i = 2; i < n; i++) 
	        if (n % i == 0) 
	            return false; 
	  
	    return true; 
	} 
	
	/**
	 * Funkcija za dohvat sljedećeg prim broja.
	 * 
	 */
	public void next() {
		
		int i = currentPrime;
		
		while(true) {
			
			i++;
			if(isPrime(i)) break;
		}
		
		currentPrime = i;
		add(currentPrime);
	}

}
