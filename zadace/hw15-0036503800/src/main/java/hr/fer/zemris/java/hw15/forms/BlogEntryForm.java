package hr.fer.zemris.java.hw15.forms;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

public class BlogEntryForm {

	/** Korisnik koji je stvorio objavu. */
	private BlogUser creator;
	/** Komentari objave. */
	private List<BlogComment> comments = new ArrayList<>();
	/** Datum stvaranje objave. */ 
	private Date createdAt;
	/** Datum zadnje izmjene objave. */
	private Date lastModifiedAt;
	/** Naslov objave. */
	private String title;
	/* Tekst objave. */
	private String text;
	
	/**
	 * Konstruktor.
	 * 
	 */
	public BlogEntryForm() {
	}
	
	/**
	 * Metoda za dohvat atributa forme za stvaranje nove objave nekog korisnika
	 * preko http zahtjeva.
	 * 
	 * @param req http zahtjev
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		
		DAO dao = DAOProvider.getDAO();
		
		// korisnik koji je autor objave
		String currentUser = (String) req.getSession().getAttribute("current.user.nick");
		if(currentUser == null) {
			this.creator = new BlogUser();
			this.creator.setNick("anonymous");
		}else {
			this.creator = dao.findUserByUsername(currentUser);
		}
		
		this.createdAt = Date.from(Instant.now());
		this.lastModifiedAt = Date.from(Instant.now());
		
		this.title = extract(req.getParameter("blogTitle"));
		this.text = extract(req.getParameter("text"));
	}
	
	/**
	 * Metoda za parsiranje teksta koji je dobiven iz http zahtjeva.
	 * 
	 * @param s tekst, odnosno pojedini parametar korisnika {@code BlogEntry}
	 * @return "" ako parametar nije unesen, {@code String} vrijednost bez praznina
	 *         na početku i kraju ako je parametar unesen
	 */
	private String extract(String s) {
		if (s == null)
			return "";
		else
			return s.strip();
	}

	/**
	 * Metoda za stvaranje objekta {@code BlogEntry} iz {@code BlogEntryForm}.
	 * 
	 * @return objekt tipa {@code BlogEntry}
	 */
	public BlogEntry getBlogEntry() {
		
		BlogEntry be = new BlogEntry();
		
		be.setCreator(creator);
		be.setComments(comments);
		be.setCreatedAt(createdAt);
		be.setLastModifiedAt(lastModifiedAt);
		be.setTitle(title);
		be.setText(text);
		
		return be;
	}

	public BlogUser getCreator() {
		return creator;
	}

	public void setCreator(BlogUser creator) {
		this.creator = creator;
	}

	public List<BlogComment> getComments() {
		return comments;
	}

	public void setComments(List<BlogComment> comments) {
		this.comments = comments;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getLastModifiedAt() {
		return lastModifiedAt;
	}

	public void setLastModifiedAt(Date lastModifiedAt) {
		this.lastModifiedAt = lastModifiedAt;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
