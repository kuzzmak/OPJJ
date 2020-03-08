package hr.fer.zemris.java.hw01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class UniqueNumbers {

	/**
	 * Pomoćni razred koji predstavlja binarno stablo
	 * 
	 * @author Antonio Kuzminski
	 *
	 */
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
		 * @param buffer         spremnik u koji se dodaju vrijednosti cvora i grane
		 *                       stabla
		 * @param prefix         predmetak za glavu
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
			if (left != null) {
				left.print(buffer, childrenPrefix + "└── ", childrenPrefix + "    ");
			}
		}
	}

	/**
	 * Funkcija za dodavanje novih čvorova u stablo. Čvorovi se dodaju rekurzivno na
	 * način da se opet poziva funkcija za određeno dijete čvora roditelja, ovisno o
	 * novoj vrijednosti koja se treba upisati.
	 * 
	 * @param node  čvor kojem se dodaje novi čvor
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
		if (isLeaf(node))
			return 0;
		// puni čvor
		if (node.right != null && node.left != null)
			return 2;
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

		if (node.left != null)
			size += size(node.left);
		if (node.right != null)
			size += size(node.right);

		return size;
	}

	/**
	 * Funkcija za provjeru sadrži li stablo određenu vrijednost.
	 * 
	 * @param node  stablo u kojem se traži vrijednost
	 * @param value vrijednost koja se traži
	 * @return true ako postoji vrijednost u stablu, false inače
	 */
	private static boolean containsValue(TreeNode node, int value) {

		if(node == null) return false;
		
		boolean flag = false;

		if (node.value == value)
			flag = true;
		else {
			if (node.left != null)
				flag = containsValue(node.left, value);
			if (flag)
				return true;
			if (node.right != null)
				flag = containsValue(node.right, value);
		}

		return flag;
	}
	
	private static List<Integer> inOrder(TreeNode node, List<Integer> list) {
		
		if(node.left != null) inOrder(node.left, list);
		list.add(node.value);
		if(node.right != null) inOrder(node.right, list);
		
		return list;
	}
	
	private static List<Integer> reverseInOrder(TreeNode node, List<Integer> list) {
		if(node.right != null) inOrder(node.right, list);
		list.add(node.value);
		if(node.left != null) inOrder(node.left, list);
		
		return list;
	}

	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		TreeNode node = null;

		System.out.print("Unesite broj > ");

		while (sc.hasNextLine()) {

			String line = sc.nextLine();

			// ako je unos korisnika kraj, izlazi se iz programa
			if (line.toLowerCase().equals("kraj")) {
				break;
			}

			try {
				// parsiranje unosa
				int value = Integer.parseInt(line.trim());
				
				// provjera postoji li već vrijednost u stablu
				if (!containsValue(node, value)) {
					node = addNode(node, value);
					System.out.println("Dodano");
				}else {
					System.out.println("Broj već postoji. Preskačem.");
				}

			} catch (IllegalArgumentException e) { 
				// predano je nešto različito od cijelog broja
				System.out.println(line.trim() + " nije cijeli broj");
			}

			System.out.print("Unesite broj > ");
		}
		
		// ispis stabla u rastućem poretku
		List<Integer> list = inOrder(node, new ArrayList<>());
		System.out.println("Ispis od najmanjeg: " + list.toString().replace("[", "").replace("]", ""));
		// ispis stabla u padajučem poretku
		list = reverseInOrder(node, new ArrayList<>());
		System.out.println("Ispis od najvećeg: " + list.toString().replace("[", "").replace("]", ""));
	}
}
