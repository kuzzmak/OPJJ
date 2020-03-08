package hr.fer.zemris.java.hw01;

public class UniqueNumbers {

	static class TreeNode {

		TreeNode left;
		TreeNode right;
		int value;

		public String toString() {
			StringBuilder buffer = new StringBuilder(50);
			print(buffer, "", "");
			return buffer.toString();
		}

		/**
		 * Funkcija za slikovit ispit stabla.
		 * 
		 * @param buffer spremnik u koji se dodaju vrijednosti cvora i grane stabla
		 * @param prefix predmetak za glavu
		 * @param childrenPrefix predmetak za djecu
		 *  
		 */
		private void print(StringBuilder buffer, String prefix, String childrenPrefix) {
			buffer.append(prefix);
			buffer.append(value);
			buffer.append('\n');
			
			if (right != null) {
				right.print(buffer, childrenPrefix + "├── ", childrenPrefix + "│   ");
			}
			if(left != null) {
				left.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
			}
		}
	}

	/**
	 * Funkcija za dodavanje novih čvorova u stablo. Čvorovi se dodaju rekurzivno
	 * na način da se opet poziva funkcija za određeno dijete čvora roditelja, ovisno
	 * o novoj vrijednosti koja se treba upisati.
	 * 
	 * @param node čvor kojem se dodaje novi čvor
	 * @param value vrijednost koju ima novi čvor
	 * @return stablo s dodanim novim čvorom
	 */
	private static TreeNode addNode(TreeNode node, int value) {

		// čvor nema u sebi vrijednost, odnosno još nije stvoren
		if (node == null) {
			node = new TreeNode();
			node.value = value;
		} else { // u čvoru postoji vrijednost pa se poziva addNode na određenom djetetu
			if (value < node.value) {
				node.left = addNode(node.left, value);
			} else if (value > node.value) {
				node.right = addNode(node.right, value);
			}
		}

		return node;
	}

	/**
	 * Funkcija za provjeru je li neki čvor list ili nije.
	 * 
	 * @param node čvor koji se provjerava
	 * @return true ako je čvor list, false inače
	 */
	private static boolean isLeaf(TreeNode node) {
		return node.left == null && node.right == null;
	}
	
	/**
	 * Funkcija koja vraća broj djece nekog čvora.
	 * 
	 * @param node čvor čiji broj djece se računa
	 * @return broj djece čvora <code>node</code>
	 */
	private static int numberOfChildren(TreeNode node) {
		// ako je čvor krajnji čvor, odnosno list, nema djece
		if(isLeaf(node)) return 0;
		// puni čvor
		if(node.right != null && node.left != null) return 2;
		// inače postoji barem jedno dijete
		return 1;
	}
	
	/**
	 * Funkcija za računavnje veličine stabla.
	 * 
	 * @param node stablo čija se veličina računa
	 * @return broj čvorova stabla
	 */
	private static int treeSize(TreeNode node) {
		return size(node) + 1;
	}
	
	/**
	 * Funkcija za računanje veličine stabla bez korijenskog čvora.
	 * 
	 * @param node čvor čija se veličina računa
	 * @return broj čvorova stabla manje korijenski čvor
	 */
	private static int size(TreeNode node) {
		
		int size = 0;
		
		size += numberOfChildren(node);
		
		if(node.left != null) size += size(node.left);
		if(node.right != null) size += size(node.right);
		
		return size;
	}

	public static void main(String[] args) {

		TreeNode glava = null;
		glava = addNode(glava, 42);
		glava = addNode(glava, 76);
		glava = addNode(glava, 21);
		glava = addNode(glava, 76);
		glava = addNode(glava, 35);
//		glava = addNode(glava, 20);
//		glava = addNode(glava, 23);
//		glava = addNode(glava, 22);

		System.out.println(glava);
		System.out.println(treeSize(glava));
	}
}
