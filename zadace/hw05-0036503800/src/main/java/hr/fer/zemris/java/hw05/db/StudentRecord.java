package hr.fer.zemris.java.hw05.db;

/**
 * Razred predstavlja jedan zapis o studentu u bazi podataka. Studenti
 * se razlikuju prema jmbag-u.
 * 
 * @author Antonio Kuzminski
 *
 */
public class StudentRecord {

	private String jmbag;
	private String firstName;
	private String lastName;
	private int finalGrade;
	/**
	 * Inicijalni konstruktor.
	 * 
	 * @param jmbag identifikacijski broj studenta
	 * @param firstName ime studenta
	 * @param lastName prezime studenta
	 * @param finalGrade zaklkjučena ocjena studenta
	 */
	public StudentRecord(String jmbag, String firstName, String lastName, int finalGrade) {
		super();
		this.jmbag = jmbag;
		this.firstName = firstName;
		this.lastName = lastName;
		this.finalGrade = finalGrade;
	}
	
	public String getJmbag() {
		return jmbag;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public int getFinalGrade() {
		return finalGrade;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jmbag == null) ? 0 : jmbag.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		if (jmbag == null) {
			if (other.jmbag != null)
				return false;
		} else if (!jmbag.equals(other.jmbag))
			return false;
		return true;
	}
}
