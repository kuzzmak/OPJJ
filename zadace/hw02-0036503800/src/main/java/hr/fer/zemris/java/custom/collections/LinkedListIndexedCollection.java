package hr.fer.zemris.java.custom.collections;

public class LinkedListIndexedCollection extends Collection {
	
	private int size = 0;
	private ListNode first;
	private ListNode last;
	
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
	
	public LinkedListIndexedCollection() {
		
		this.first = this.last = null;
	}
	
	
	public LinkedListIndexedCollection(Collection col) {
		
	}
	
	/**
	 * Metoda za dodavanje elementa <code>value</code> u listu. Moguće je imati
	 * više istih elemenata, ali nije moguće dodati <code>null</code>.
	 * 
	 * @param value element koji se dodaje u kolekciju
	 * @throws NullPointerException ako je predana <code>null</code> vrijednost
	 */
	@Override
	public void add(Object value) {
		
		if(value == null) throw new NullPointerException("Nije moguće pohraniti null vrijednost.");
		
		if(this.last == null) {
			this.first = this.last = new ListNode(value);
			this.size++;
		}else {
			
			ListNode nextNode = new ListNode(value);
			
			if(this.size == 1) {
				
				this.last = nextNode;
				this.first.next = nextNode;
				nextNode.previous = this.first;
			}else {
				
				ListNode temp = this.last;
				this.last = nextNode;
				nextNode.previous = temp;
				temp.next = nextNode;
			}
			
			this.size++;
		}
	}
	
	/**
	 * Metoda za dohvat elementa liste na indeksu <code>index</code>.
	 * 
	 * @param index indeks na kojem se pokušava dohvatiti element
	 * @throws IndexOutOfBoundsException ako je <code>index</code> izvan granica kolekcije
	 * @return element na indeksu <code>index</code> ako postoji
	 */
	public Object get(int index) {
		
		if(index < 0 || index > this.size - 1) throw new IndexOutOfBoundsException("Indeks izvan granica kolekcije.");
		
		// indeks u prvom dijelu kolekcije
		// kreće se pretraga od prvog do zadnjeg noda
		if(index < this.size / 2) {
			
			ListNode next = this.first;
			int counter = 0;
			while(next != null) {
				if(counter == index) {
					break;
				}else {
					next = next.next;
					counter++;
				}
			}
			return next.data;
			
		}else {
			// suprotno nego ispred
			ListNode previous = this.last;
			int counter = this.size - 1;
			while(previous != null) {
				if(counter == index) {
					break;
				}else {
					previous = previous.previous;
					counter--;
				}
			}
			return previous.data;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int size() {
		return this.size;
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
		
		if(this.first.data != null) {
			sb.append(this.first.data + " ");
		}
		
		ListNode next = this.first.next;
		while(next != null) {
			sb.append(next.data + " ");
			next = next.next;
		}
		
		return sb.toString();
	}
	
	
}
