package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentDatabase {
	
	// mapa jmbag = indeks koja služi za brzi dohvat zapisa prema indeksu
	private Map<String, Integer> indexes;
	// zapisi studenata
	private List<StudentRecord> records;
	
	public StudentDatabase(List<String> entries) {
		
		indexes = new HashMap<>();
		
		makeRecords(entries);
	}
	
	/**
	 * Metoda za dohvaćanje zapisa iz baze na temelju JMBAG-a.
	 * 
	 * @param jmbag identifikacijski broj studenta 
	 * @return zapis studenta ako postoji, <code>null</code> inače
	 */
	public StudentRecord forJMBAG(String jmbag) {
		
		int index = indexes.getOrDefault(jmbag, -1);
		
		return index != -1 ? records.get(index): null;
	}
	
	/**
	 * Metoda za filtriranje zapisa u bazi prema predanom filteru {@code filter}.
	 * 
	 * @param filter filter prema kojem se filtriraju zapisi
	 * @return lista zapisa koji zadovoljavaju uvjet filtera
	 */
	public List<StudentRecord> filter(IFilter filter){
		
		List<StudentRecord> accepted = new ArrayList<>();
		
		for(StudentRecord sr: records) {
			if(filter.accepts(sr)) accepted.add(sr);
		}
		return accepted;
	}
	
	/**
	 * Metoda za stvaranje zapisa u bazi iz redaka {@code entries}.
	 * 
	 * @param entries retci iz kojih se stvaraju zapisi u bazi
	 */
	private void makeRecords(List<String> entries) {
		
		records = new ArrayList<>();
		
		// brojač za indekse
		int i = 0;
		
		for(String row: entries) {
			
			String[] splitted = row.split("\\s+");
			
			String jmbag;
			String firstName;
			String lastName;
			int finalGrade;
			
			// jedna riječ prezime, jedan riječ ime
			if(splitted.length == 4) {
				jmbag = splitted[0];
				firstName = splitted[2];
				lastName = splitted[1];
				finalGrade = Integer.parseInt(splitted[3]);
			}else { // dvije riječi prezime, jedna riječ ime
				jmbag = splitted[0];
				firstName = splitted[3];
				lastName = splitted[1] + " " + splitted[2];
				finalGrade = Integer.parseInt(splitted[4]);
			}
			
			indexes.put(jmbag, i);
			i++;
			records.add(new StudentRecord(jmbag, firstName, lastName, finalGrade));
		}
	}
}
