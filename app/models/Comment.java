package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.db.ebean.Model;

/**
 * This Object represent a comments on a Workshop
 * 
 * @author ychartois
 *
 */
@Entity
@Table(name = "COMMENT")
public class Comment extends Model {

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
	@Column(name = "comment", length = 1000)
	public String comment;
	
	/**
	 * the workshop related to this comment
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "workshop_id")
	public Workshop workshop; 
	
	/**
	 * Définition d'un finder qui va permettre de faire les accès à la base
	 */
	public static Finder<Long, Comment> find = new Finder<Long, Comment>(Long.class, Comment.class);
	
	
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
