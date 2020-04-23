package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class StudentDB {
	
	Path databaseFile = Paths.get("src/test/resources/database.txt");
	
	StudentDatabase sdb;

	public StudentDB() {
		
		List<String> lines = null;
		
		try {
			lines = Files.readAllLines(databaseFile, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		sdb = new StudentDatabase(lines);
	}
	
	public void query(String query) {
		
		QueryParser qp = new QueryParser(query);
		
		if(qp.isDirectQuery()) {
			
			System.out.println(sdb.forJMBAG(qp.getQueriedJMBAG()));
			
		}else {
			
			for(StudentRecord sr: sdb.filter(new QueryFilter(qp.getQuerry()))) {
				
				
				System.out.println(sr);
				
			}
			
		}
		
		
	}
}
