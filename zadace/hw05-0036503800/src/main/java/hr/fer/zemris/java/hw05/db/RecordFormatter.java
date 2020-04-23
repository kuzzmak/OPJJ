package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Razred za formatiran ispis zapisa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class RecordFormatter {

	/**
	 * Metoda za formatiranje ispisa predanih zapisa.
	 * 
	 * @param records zapisi koje je potrebno ispisati.
	 * @return lista formatiranih stringova koji sadrže podatke predanih zapisa
	 */
	public static List<String> format(List<StudentRecord> records) {
		
		if(records.size() == 0) return new ArrayList<>(Arrays.asList("Records selected: 0"));

		List<String> outputStrings = new ArrayList<>();

		int maxFirstNameLength = records
				.stream()
				.mapToInt(c -> c.getFirstName().length())
				.max()
				.getAsInt();

		int maxLastNameLength = records
				.stream()
				.mapToInt(c -> c.getLastName().length())
				.max()
				.getAsInt();

		StringBuilder row = new StringBuilder();
		row.append("+=");

		for (int i = 0; i < 10; i++)
			row.append("=");
		row.append("=+=");

		for (int i = 0; i < maxLastNameLength; i++)
			row.append("=");
		row.append("=+=");

		for (int i = 0; i < maxFirstNameLength; i++)
			row.append("=");
		row.append("=+===+");

		outputStrings.add(row.toString());

		row.delete(0, row.length());

		for (StudentRecord r : records) {

			row.append("| ");

			row.append(r.getJmbag());

			row.append(" | ");

			String lastName = r.getLastName();

			row.append(lastName);

			for (int i = 0; i < maxLastNameLength - lastName.length(); i++)
				row.append(" ");

			row.append(" | ");

			String firstName = r.getFirstName();
			
			row.append(firstName);

			for (int i = 0; i < maxFirstNameLength - firstName.length(); i++)
				row.append(" ");

			row.append(" | ");
			
			row.append(r.getFinalGrade());
			row.append(" |");
			
			outputStrings.add(row.toString());
			
			row.delete(0, row.length());
		}
		
		outputStrings.add(outputStrings.get(0));

		outputStrings.add(new String("Records selected: " + String.valueOf(outputStrings.size() - 2)));
		return outputStrings;
	}
}
