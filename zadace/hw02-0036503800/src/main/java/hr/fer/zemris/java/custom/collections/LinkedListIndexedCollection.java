package hr.fer.zemris.java.custom.collections;

public class LinkedListIndexedCollection extends Collection {

	private int size;
	private ListNode first;
	private ListNode last;

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

	/**
	 * Metoda za dodavanje elementa <code>value</code> u listu. Moguće je imati više
	 * istih elemenata, ali nije moguće dodati <code>null</code>.
	 * 
	 * @param value element koji se dodaje u kolekciju
	 * @throws NullPointerException ako je predana <code>null</code> vrijednost
	 */
	@Override
	public void add(Object value) {

		if (value == null)
			throw new NullPointerException("Nije moguće pohraniti null vrijednost.");

		// dodavanje prvog elementa
		if (this.last == null) {
			this.first = this.last = new ListNode(value);
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

			this.size++;
		}
	}

	/**
	 * Metoda za dohvat elementa liste na indeksu <code>index</code>.
	 * 
	 * @param index indeks na kojem se pokušava dohvatiti element
	 * @throws IndexOutOfBoundsException ako je <code>index</code> izvan granica
	 *                                   kolekcije
	 * @return element na indeksu <code>index</code> ako postoji
	 */
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

	/**
	 * Metoda za dohvat indeksa elementa unutar kolekcije ako postoji.
	 * 
	 * @param value element čiji se indeks traži
	 * @return indeks traženog elementa ako postoji, -1 inače
	 */
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

	/**
	 * {@inheritDoc}
	 */
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

	/**
	 * Metoda za brisanje elemenata kolekcije. "Zaboravlja" reference na prvi i
	 * zadnji član.
	 * 
	 */
	@Override
	public void clear() {
		this.first = this.last = null;
		this.size = 0;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	Object[] toArray() {

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

	/**
	 * {@inheritDoc}
	 */
	@Override
	void forEach(Processor processor) {

		ListNode node = this.first;

		while (node != null) {
			processor.process(node.data);
			node = node.next;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	void addAll(Collection other) {

		if (other == null)
			throw new NullPointerException("Predana kolekcija je null.");

		other.forEach(new Processor() {
			@Override
			public void process(Object value) {
				add(value);
			}
		});
	}

	/**
	 * Metoda za brisanje elementata liste na određenom indeksu <code>index</code>.
	 * 
	 * @param index indeks na kojem se briše element
	 * @throws IndexOutOfBoundsException ako je <code>index</code> izvan granica liste
	 */
	void remove(int index) {

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
			this.size--;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean remove(Object value) {

		if (value == null)
			throw new NullPointerException("Null nije dopušten u kolekciji.");

		int index = this.indexOf(value);
		if(index == -1) return false;

		if (index == 0) {
			
			ListNode secondElement = this.first.next;
			if(this.size != 1) secondElement.previous = null; // ako je samo jedan element, on nema sljedećeg niti prethodnog
			
			this.first = secondElement;
			
			this.size--;
			return true;
			
		} else if (index == this.size - 1) {
			
			ListNode secondToLast = this.last.previous;
			secondToLast.next = null;
			
			this.last = secondToLast;
			
			this.size--;
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

					return true;
				}
				node = node.next;
			}
		}
		return false;
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

}
