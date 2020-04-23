package hr.fer.zemris.java.hw05.querylexer;

import hr.fer.zemris.java.hw05.db.QueryFilter;
import hr.fer.zemris.java.hw05.db.QueryParser;
import hr.fer.zemris.java.hw05.db.StudentDB;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class LexerDemo {
	
	public static void main(String[] args) {
		
		
		String query1 = "query jmbag=\"0000000003\"";
		String query2 = "query  lastName =  \"Blažić\"";
		String query3 = "query firstName>\"A\" and lastName LIKE \"B*\"";
		String query4 = "query firstName>\"A\" and firstName<\"C\" and "
				+ "lastName LIKE \"B*ć\" and jmbag>\"0000000002\"";
		String query5 = "query firstName=\"Ivan\" and lastName LIKE \"R*\"";
		
//		QueryLexer lexer = new QueryLexer(query4);
		
//		QueryParser qp = new QueryParser(query3);
//		QueryFilter qf = new QueryFilter(qp.getQuerry());
//		System.out.println(qf.accepts(new StudentRecord("0000000003", "Ana", "Blažić", 5)));
		
//		StudentDB db = new StudentDB();
//		db.query(query5);
		
//		List<Integer> num = new ArrayList<>();
//		List<Integer> num2 = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
//		
//		num2.forEach(new Consumer<Integer>() {
//
//			@Override
//			public void accept(Integer t) {
//				if(t > 3) num.add(t);
//				
//			}
//		});
//		
//		System.out.println(num);
	}
}
