package hr.fer.zemris.java.hw03.prob1;

public class test {

	public static void main(String[] args) {
		
		String ulaz = "Ovo je 123ica, ab57.\nKraj";
		String ulaz2 = "\\1\\2 ab\\\\\\2c\\3\\4d";
		
		Lexer lex = new Lexer(ulaz2);
		
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
//		System.out.println(lex.nextToken());
		
	}
	
	
}
