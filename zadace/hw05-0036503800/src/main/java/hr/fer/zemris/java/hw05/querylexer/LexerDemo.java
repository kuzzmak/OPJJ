package hr.fer.zemris.java.hw05.querylexer;

import hr.fer.zemris.java.hw05.db.QueryParser;

public class LexerDemo {
	
	public static void main(String[] args) {
		
		
		String query1 = "query jmbag=\"0000000003\"";
		String query2 = "query  lastName =  \"Blažić\"";
		String query3 = "query firstName>\"A\" and lastName LIKE \"B*ć\"";
		String query4 = "query firstName>\"A\" and firstName<\"C\" and "
				+ "lastName LIKE \"B*ć\" and jmbag>\"0000000002\"";
		
//		QueryLexer lexer = new QueryLexer(query4);
		
		QueryParser qp = new QueryParser(query4);
		System.out.println(qp.getQueriedJMBAG());
		
	}
}
