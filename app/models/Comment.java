package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * This Object represent a comments on a Workshop
 * 
 * @author ychartois
 *
 */
@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8881854974752539059L;

	/**
	 * L'identifiant
	 */
	@Id
	@GeneratedValue
	private Long id;
	
	/**
	 * Comment author's name
	 */
	@ManyToOne
	private User author;
	
	/**
	 * Date of which the comment was created
	 */
	@Column(name = "creation_date")
	private Date creationDate;
	
	/**
	 * The comment
	 */
	@Column(name = "comment")
	private String comment;
	
	/**
	 * the workshop related to this comment
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "workshop_id")
	private Workshop workshop; 
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructeur
	 */
	public Comment() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							Setter/Getter	        
	//<--------------------------------------------------------------------------->	
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}


	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}


	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}


	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}


	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}


	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}


	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}


	/**
	 * @return the workshop
	 */
	public Workshop getWorkshop() {
		return workshop;
	}


	/**
	 * @param workshop the workshop to set
	 */
	public void setWorkshop(Workshop workshop) {
		this.workshop = workshop;
	}
}
