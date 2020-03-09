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

		if (node == null)
			return false;

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

	/**
	 * Funkcija za prolazak kroz stablo na način da se prvo obiđe lijevo
	 * dijete, zatim se posjeti roditelj i onda desno dijete.
	 * 
	 * @param node stablo koje se posjećuje
	 * @param list lista u koju se dodaju vrijednosti zapisane u pojedinom čvoru
	 * @return lista vrijednosti zapisanih u čvorovima od manje prema većoj
	 */
	private static StringBuilder inOrder(TreeNode node, StringBuilder list) {

		if (node.left != null)
			inOrder(node.left, list);
		
		list.append(node.value).append(", ");
		
		if (node.right != null)
			inOrder(node.right, list);

		return list;
	}

	/**
	 * Funkcija za prolazak kroz stablo na način da se prvo obiđe desno
	 * dijete, zatim se posjeti roditelj i onda lijevo dijete.
	 * 
	 * @param node stablo koje se posjećuje
	 * @param list lista u koju se dodaju vrijednosti zapisane u pojedinom čvoru
	 * @return lista vrijednosti zapisanih u čvorovima od veće prema manjoj
	 */
	private static StringBuilder reverseInOrder(TreeNode node, StringBuilder list) {
		
		if (node.right != null)
			reverseInOrder(node.right, list);
		
		list.append(node.value).append(", ");
		
		if (node.left != null)
			reverseInOrder(node.left, list);

		return list;
	}

	/**
	 * Funkcija iz koje kreće izvođenje glavnog programa.
	 * 
	 * @param args predani argumenti
	 */
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
				} else {
					System.out.println("Broj već postoji. Preskačem.");
				}

			} catch (IllegalArgumentException e) {
				// predano je nešto različito od cijelog broja
				System.out.println(line.trim() + " nije cijeli broj");
			}

			System.out.print("Unesite broj > ");
		}

		if (node != null) {
			// dohvat stringa vrijednosti svih čvorova
			String list = inOrder(node, new StringBuilder()).toString();
			
			// ispis stabla u rastućem poretku i micanje zareza poslije zadnjeg broja
			System.out.println("Ispis od najmanjeg: " + list.substring(0, list.length() - 2));
			
			list = reverseInOrder(node, new StringBuilder()).toString();
			
			// ispis stabla u padajučem poretku
			System.out.println("Ispis od najvećeg: " + list.substring(0, list.length() - 2));
		}
	}
}
