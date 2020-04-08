package hr.fer.zemris.java.hw03.prob1;

public class test {

	public static void main(String[] args) {
		
		String ulaz1 = "  \\1st  \r\n\t   ";
//		
		String ulaz2 = "\\1\\2 ab\\\\\\2c\\3\\4d";
//		
//		String ulaz3 = "   \\";
//		
//		String ulaz4 = "   \\a    ";
//		
//		String ulaz5 = "ab\\2\\1cd\\4\\\\ \r\n\t";   
//		
//		String ulaz6 = "  1234\r\n\t 5678   ";
		
		String ulaz8 = "  -.? \r\n\t ##   ";
		
		String ulaz7 = "  ab\\123cd ab\\2\\1cd\\4\\\\ \r\n\t   ";
		
		String ulaz9 = "Janko 3! Jasmina 5; -24";
		Lexer lex = new Lexer(ulaz9);
		
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		System.out.println(lex.nextToken());
		
		//9223372036854775807
		//12345678912123123432123
		
//		long big = Long.MAX_VALUE;
//		System.out.println(big);
		
		
	}
	
	
}
