package demo;

import hr.fer.zemris.java.hw05.db.ComparisonOperators;
import hr.fer.zemris.java.hw05.db.FieldValueGetters;
import hr.fer.zemris.java.hw05.db.IComparisonOperator;
import hr.fer.zemris.java.hw05.db.StudentDB;
import hr.fer.zemris.java.hw05.db.StudentRecord;

public class DatabaseDemo {

	public static void main(String[] args) {

//		StudentDB db = new StudentDB();
		
//		String s1 = "Ana";
//		String s2 = "Ana";
//		System.out.println(s1.compareTo(s2));
		
		IComparisonOperator oper = ComparisonOperators.LIKE;
		System.out.println(oper.satisfied("Blažić", "B*ć"));
		System.out.println(oper.satisfied("AAAA", "A*A"));
		
		
//		StudentRecord r = new StudentRecord("001", "Ana", "Koko", 5);
//		System.out.println(FieldValueGetters.FIRST_NAME.get(r));
		
	}
}
