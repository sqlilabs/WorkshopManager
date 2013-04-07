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
	public Long id;
	
	/**
	 * Comment author's name
	 */
	@ManyToOne
	public User author;
	
	/**
	 * Date of which the comment was created
	 */
	@Column(name = "creation_date")
	public Date creationDate;
	
	/**
	 * The comment
	 */
	@Column(name = "comment")
	public String comment;
	
	/**
	 * the workshop related to this comment
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "workshop_id")
	public Workshop workshop; 
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructeur
	 */
	public Comment() {
		super();
	}
	
	
	
}
