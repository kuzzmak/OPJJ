package hr.fer.zemris.java.hw15.forms;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.model.BlogUser;

import hr.fer.zemris.java.hw15.Util;

/**
 * Razred za manipulaciju s entitetom {@code BlogUser}.
 * 
 * @author Antonio Kuzminski
 *
 */
public class BlogUserForm {

	/** Ime korisnika. */
	private String name;
	/** Prezime korisnika. */
	private String surname;
	/** E-mail korisnika. */
	private String email;
	/** Korisničko ime korisnika. */
	private String username;
	/** Lozinka korisnika. */
	private String password;
	/** Ponovljena lozinka korisnika. */
	private String passwordRepeat;
	/** Mapa grešaka ako ima neispravnih podataka. */
	private Map<String, String> errors = new HashMap<>();

	/**
	 * Konstruktor.
	 * 
	 */
	public BlogUserForm() {
	}

	/**
	 * Metoda za dohvat podataka korisnika prilikom registracije iz parametra html
	 * forme.
	 * 
	 * @param req http zahtjev
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		this.name = extract(req.getParameter("name"));
		this.surname = extract(req.getParameter("surname"));
		this.email = extract(req.getParameter("email"));
		this.username = extract(req.getParameter("username"));
		this.password = extract(req.getParameter("password"));
		this.passwordRepeat = extract(req.getParameter("password_repeat"));
	}

	/**
	 * Metoda za parsiranje teksta koji je dobiven iz http zahtjeva.
	 * 
	 * @param s tekst, odnosno pojedini parametar korisnika {@code BlogUser}
	 * @return "" ako parametar nije unesen, {@code String} vrijednost bez praznina
	 *         na početku i kraju ako je parametar unesen
	 */
	private String extract(String s) {
		if (s == null)
			return "";
		else
			return s.strip();
	}

	public void validate() {

		errors.clear();

		if (!password.equals(passwordRepeat)) {
			errors.put("password", "Upisane lozinke nisu jednake.");
		}

	}

	/**
	 * Metoda za stvaranje objekta {@code BlogUser} iz forme.
	 * 
	 * @return objekt tipa {@code BlogUser}
	 */
	public BlogUser getBlogUser() {

		BlogUser bu = new BlogUser();

		bu.setFirstName(name);
		bu.setLastName(surname);
		bu.seteMail(email);
		bu.setNick(username);
		bu.setPasswordHash(Util.getMessageDigest(password));
		
		return bu;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordRepeat() {
		return passwordRepeat;
	}

	public void setPasswordRepeat(String passwordRepeat) {
		this.passwordRepeat = passwordRepeat;
	}

	public Map<String, String> getErrors() {
		return errors;
	}

	@Override
	public String toString() {
		return "BlogUserForm [name=" + name + ", surname=" + surname + ", email=" + email + ", username=" + username
				+ ", password=" + password + ", passwordRepeat=" + passwordRepeat + ", errors=" + errors + "]";
	}

}
