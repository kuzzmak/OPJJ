package hr.fer.zemris.java.hw15.forms;

import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import hr.fer.zemris.java.hw15.dao.DAO;
import hr.fer.zemris.java.hw15.dao.DAOProvider;
import hr.fer.zemris.java.hw15.model.BlogComment;
import hr.fer.zemris.java.hw15.model.BlogEntry;
import hr.fer.zemris.java.hw15.model.BlogUser;

/**
 * Forma za obavljanje operacija s {@code BlogComment} entitetom.
 * 
 * @author Antonio Kuzminski
 *
 */
public class BlogCommentForm {

	/** Objava čiji je ovo komentar. */
	private BlogEntry blogEntry;
	/** Mail korisnika koji je komentirao. */
	private String usersEMail;
	/** Poruka komentara. */
	private String message;
	/** Datum objave komentara. */
	private Date postedOn;
	
	public BlogCommentForm() {
	}
	
	/**
	 * Metoda za stvaranje objekta tipa {@code BlogCommentForm} iz parametara
	 * http zathjeva.
	 * 
	 * @param req
	 */
	public void fromHttpRequest(HttpServletRequest req) {
		
		DAO dao = DAOProvider.getDAO();
		
		String currentUser = (String) req.getSession().getAttribute("current.user.nick");
		
		if(currentUser == null) {
			usersEMail = "";
		}else {
			BlogUser bu = dao.findUserByUsername(currentUser);
			usersEMail = bu.geteMail();
		}
		
		blogEntry = dao.getBlogEntry(Long.valueOf((String) req.getSession().getAttribute("blogId")));
		message = extract(req.getParameter("message"));
		postedOn = Date.from(Instant.now());
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
	 * Metoda za dohvat objekta tipa {@code BlogComment} iz 
	 * {@code BlogCommentForm}.
	 * 
	 * @return objekta tipa {@code BlogComment}
	 */
	public BlogComment getBlogComment() {
		
		BlogComment bc = new BlogComment();
		
		bc.setUsersEMail(usersEMail);
		bc.setMessage(message);
		bc.setPostedOn(postedOn);
		bc.setBlogEntry(blogEntry);
		
		return bc;
	}
	
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}

	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	public String getUsersEMail() {
		return usersEMail;
	}

	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}
	
}
