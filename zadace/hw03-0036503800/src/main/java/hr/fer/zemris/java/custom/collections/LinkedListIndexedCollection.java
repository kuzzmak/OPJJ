package hr.fer.zemris.java.custom.collections;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

/**
 * Razred koji oponaša kolekciju povezane liste gdje je svaki element zaseban
 * spremnik tipa ListNode.
 * 
 * @author Antonio Kuzminski
 *
 */
public class LinkedListIndexedCollection implements List {

	private int size;
	private ListNode first;
	private ListNode last;
	
	private long modificationCount = 0;

	/**
	 * Privatni razred koji ima funkciju iteratora kolekcije.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	class LLICElementsGetter implements ElementsGetter{

		ListNode currentNode = first;
		// zastavica koja je istinita prilikom prvog dohvata elementa kolekcije
		private boolean firstElement = true;
		// broj modifikacije prilikom stvaranje ElementGettera
		private long savedModificationCount;

		/**
		 * Inicijalni konstruktor.
		 * 
		 * @param modificationCount broj modifikacije koje su se do sada dogodile u kolekciji
		 */
		public LLICElementsGetter(long modificationCount) {
			this.savedModificationCount = modificationCount;
		}
		
		@Override
		public boolean hasNextElement() {

			if(currentNode != null || currentNode == first) return true;
			return false;
		}

		@Override
		public Object getNextElement() {

			if (!hasNextElement())
				throw new NoSuchElementException("Nema više elemenata u kolekciji.");
			
			if(this.savedModificationCount != modificationCount) 
				throw new ConcurrentModificationException("Nije moguće raditi izmjene na kolekciji prilikom iteracije.");
			
			if(currentNode == first) {
				currentNode = currentNode.next;
				return first.data;
			}else {
				currentNode = currentNode.next;
				return currentNode.data;
			}
		}
	}

	/**
	 * Razred koji predstavlja jednog člana liste, a ujedno i pamti sljedećeg i
	 * prethodnog člana.
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
	private static class ListNode {

		@Override
		public String toString() {
			return String.valueOf(data);
		}

		private Object data;
		private ListNode next;
		private ListNode previous;

		public ListNode(Object value) {

			this.data = value;
		}
	}

	/**
	 * Inicijalni konstruktor.
	 * 
	 */
	public LinkedListIndexedCollection() {

		this.first = this.last = null;
	}

	/**
	 * Konstruktor preko neke druge kolekcije.
	 * 
	 * @param col druga kolekcija kojom se kopira u novonastalu
	 */
	public LinkedListIndexedCollection(Collection col) {
		this.addAll(col);
	}

	@Override
	public void add(Object value) {

		if (value == null)
			throw new NullPointerException("Nije moguće pohraniti null vrijednost.");

		// dodavanje prvog elementa
		if (this.last == null) {
			this.first = this.last = new ListNode(value);
			modificationCount++;
			this.size++;
		} else {

			ListNode newNode = new ListNode(value);

			if (this.size == 1) {

				this.last = newNode;
				this.first.next = newNode;
				newNode.previous = this.first;
			} else {

				ListNode temp = this.last;
				this.last = newNode;
				newNode.previous = temp;
				temp.next = newNode;
			}
			
			modificationCount++;
			this.size++;
		}
	}

	@Override
	public Object get(int index) {

		if (index < 0 || index > this.size - 1)
			throw new IndexOutOfBoundsException("Indeks izvan granica kolekcije.");

		// indeks u prvom dijelu kolekcije
		// kreće se pretraga od prvog node-a pa nadalje
		if (index < this.size / 2) {

			ListNode next = this.first;
			int counter = 0;
			while (next != null) {
				if (counter == index) {
					break;
				} else {
					next = next.next;
					counter++;
				}
			}
			return next.data;

		} else {
			// suprotno nego ispred
			ListNode previous = this.last;
			int counter = this.size - 1;
			while (previous != null) {
				if (counter == index) {
					break;
				} else {
					previous = previous.previous;
					counter--;
				}
			}
			return previous.data;
		}
	}

	@Override
	public int indexOf(Object value) {

		if (value == null)
			return -1;

		ListNode node = this.first;

		int i = 0;
		while (node != null) {
			if (node.data.equals(value))
				return i;
			node = node.next;
			i++;
		}

		return -1;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean contains(Object value) {

		if (value == null)
			return false;

		if (this.first.data.equals(value))
			return true;
		if (this.last.data.equals(value))
			return true;

		ListNode next = this.first.next;
		while (next != null) {
			if (next.data.equals(value))
				return true;
			else {
				next = next.next;
			}
		}
		return false;
	}

	@Override
	public void clear() {
		this.first = this.last = null;
		modificationCount++;
		this.size = 0;
	}

	@Override
	public Object[] toArray() {

		if (this.size == 0)
			return new Object[0];

		Object[] array = new Object[this.size];

		ListNode node = this.first;

		int i = 0;
		while (node != null) {
			array[i] = node.data;
			node = node.next;
			i++;
		}
		return array;
	}

	@Override
	public void remove(int index) {

		if (index < 0 || index > this.size - 1)
			throw new IndexOutOfBoundsException("Neispravan indeks.");

		// prva polovica liste
		if (index < this.size / 2) {

			ListNode current = this.first;
			int counter = 0;

			while (current != null) {
				if (counter == index) {
					break;
				} else {
					current = current.next;
					counter++;
				}
			}

			// dohvat prethodnog i sljedećeg elementa od elementa na traženom indeksu
			ListNode previous = current.previous;
			ListNode next = current.next;

			// uklanja se prvi element, potrebno promijeniti this.first
			if (index == 0) {
				this.first = next;
			}

			// svi osim prvog elementa imaju prethodni emelent
			if (index != 0)
				previous.next = next;
			next.previous = previous;

			this.size--;
			modificationCount++;

		} else {

			ListNode current = this.last;
			int counter = this.size - 1;

			while (current != null) {
				if (counter == index) {
					break;
				} else {
					current = current.previous;
					counter--;
				}
			}

			ListNode previous = current.previous;
			ListNode next = current.next;

			// slučaj kada je samo jedan element, on nema sljedećeg niti prethodnog
			if (previous == null && next == null) {
				this.first = null;
				this.size--;
				modificationCount++;
				return;
			}

			// slučaj kada je zadnji element
			if (index == this.size - 1) {
				this.last = previous;
			}

			// zadnji nema sljedećeg
			if (index != this.size - 1)
				next.previous = previous;

			previous.next = next;
			modificationCount++;
			this.size--;
		}
	}

	@Override
	public boolean remove(Object value) {

		if (value == null)
			throw new NullPointerException("Null nije dopušten u kolekciji.");

		int index = this.indexOf(value);
		if (index == -1)
			return false;

		if (index == 0) {

			ListNode secondElement = this.first.next;
			if (this.size != 1)
				secondElement.previous = null; // ako je samo jedan element,
												// on nema sljedećeg niti prethodnog
			this.first = secondElement;

			this.size--;
			modificationCount++;
			
			return true;

		} else if (index == this.size - 1) {

			ListNode secondToLast = this.last.previous;
			secondToLast.next = null;

			this.last = secondToLast;

			this.size--;
			modificationCount++;
			
			return true;

		} else {

			ListNode node = this.first;

			while (node != null) {

				if (node.data.equals(value)) {

					ListNode next = node.next;
					ListNode previous = node.previous;

					previous.next = next;
					next.previous = previous;

					this.size--;
					node = null;
					modificationCount++;
					
					return true;
				}
				node = node.next;
			}
		}
		return false;
	}
	
	@Override
	public void insert(Object value, int position) {
		
		if (value == null)
			throw new NullPointerException("Nije moguće dodati null u kolekciju.");
		if (position < 0 || position > size)
			throw new IndexOutOfBoundsException("Neispravan indeks za umetanje elementa.");
		
		if(position == this.size) {
			
			ListNode lastNode = this.last;
			ListNode newNode = new ListNode(value);
			
			lastNode.next = newNode;
			newNode.previous = lastNode;
			this.last = newNode;
			
		}else if(position == 0) {
			
			ListNode firstNode = this.first;
			ListNode newNode = new ListNode(value);
			
			firstNode.previous = newNode;
			newNode.next = firstNode;
			this.first = newNode;
			
		}else {
			
			ListNode node = this.first;
			int counter = 0;
			
			while(node != null) {
				if(counter == position) {
					
					ListNode previous = node.previous;
					
					ListNode newNode = new ListNode(value);
					
					previous.next = newNode;
					node.previous = newNode;
					
					newNode.previous = previous;
					newNode.next = node;
					
					break;
				}else {
					node = node.next;
					counter++;
				}
			}
		}
		
		this.size++;
		this.modificationCount++;
	}

	public ListNode getFirst() {
		return first;
	}

	public ListNode getLast() {
		return last;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		sb.append("[");

		if (this.first != null) {

			sb.append(this.first.data + ", ");

			ListNode next = this.first.next;
			while (next != null) {
				sb.append(next.data + ", ");
				next = next.next;
			}
			sb.delete(sb.length() - 2, sb.length());
		}

		sb.append("]");

		return sb.toString();
	}

	@Override
	public ElementsGetter createElementsGetter() {
		
		return new LLICElementsGetter(this.modificationCount);
	}

	
}
