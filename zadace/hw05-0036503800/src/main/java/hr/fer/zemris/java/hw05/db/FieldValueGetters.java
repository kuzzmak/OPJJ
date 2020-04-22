package hr.fer.zemris.java.hw05.db;

/**
 * Razred za dohvaćanje pojedinih podataka studenta iz zapisa.
 * 
 * @author Antonio Kuzminski
 *
 */
public class FieldValueGetters {

	public static final IFieldValueGetter FIRST_NAME = record -> record.getFirstName();
	
	public static final IFieldValueGetter LAST_NAME = record -> record.getLastName();
	
	public static final IFieldValueGetter JMBAG = record -> record.getJmbag();
}
