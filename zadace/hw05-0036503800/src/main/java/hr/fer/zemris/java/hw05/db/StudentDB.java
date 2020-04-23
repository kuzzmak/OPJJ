package hr.fer.zemris.java.hw05.db;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * Razred koji oponaša jednostvnu bazu podataka.
 * 
 * @author Antonio Kuzminski
 *
 */
public class StudentDB {

	Path databaseFile = Paths.get("src/test/resources/database.txt");

	StudentDatabase sdb;

	/**
	 * Inicijalni konstruktor.
	 * 
	 */
	public StudentDB() {

		List<String> lines = null;

		try {
			lines = Files.readAllLines(databaseFile, Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
		}

		sdb = new StudentDatabase(lines);
	}

	/**
	 * Metoda za izvršavanje pojedinog upita.
	 * 
	 * @param query upit koji je potrebno izvršiti
	 */
	private void query(String query) {

		QueryParser qp = new QueryParser(query);

		if (qp.isDirectQuery()) {
			
			RecordFormatter
			.format(
					new ArrayList<StudentRecord>(
							Arrays.asList(sdb.forJMBAG(qp.getQueriedJMBAG()))))
			.forEach(System.out::println);

		} else {

			List<StudentRecord> records = new ArrayList<>();

			for (StudentRecord sr : sdb.filter(new QueryFilter(qp.getQuerry()))) {

				records.add(sr);
			}

			RecordFormatter.format(records).forEach(System.out::println);
		}
	}

	public static void main(String[] args) {

		StudentDB db = new StudentDB();

		try (Scanner sc = new Scanner(System.in)) {

			while (sc.hasNextLine()) {

				String line = sc.nextLine().strip();

				if (line.equals("exit")) {
					System.out.println("Goodbye!");
					System.exit(0);
				}

				db.query(line);
			}
		}
	}
}
