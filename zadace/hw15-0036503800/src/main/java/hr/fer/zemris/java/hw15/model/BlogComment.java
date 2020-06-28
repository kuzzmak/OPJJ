package hr.fer.zemris.java.hw15.model;

import java.util.Date;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Razred koji predstavlja komentar ispod neke objave.
 * 
 * @author Antonio Kuzminski
 *
 */
@Entity
@Table(name="blog_comments")
@Cacheable(true)
public class BlogComment {

	/** Jedinstveni identifikator svakog komentara. */
	private Long id;
	/** Objava čiji je ovo komentar. */
	private BlogEntry blogEntry;
	/** Mail korisnika koji je komentirao. */
	private String usersEMail;
	/** Poruka komentara. */
	private String message;
	/** Datum objave komentara. */
	private Date postedOn;
	
	@Id @GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne
	@JoinColumn(nullable=false)
	public BlogEntry getBlogEntry() {
		return blogEntry;
	}
	
	public void setBlogEntry(BlogEntry blogEntry) {
		this.blogEntry = blogEntry;
	}

	@Column(length=100, nullable=false)
	public String getUsersEMail() {
		return usersEMail;
	}

	public void setUsersEMail(String usersEMail) {
		this.usersEMail = usersEMail;
	}

	@Column(length=4096, nullable=false)
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable=false)
	public Date getPostedOn() {
		return postedOn;
	}

	public void setPostedOn(Date postedOn) {
		this.postedOn = postedOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		BlogComment other = (BlogComment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
