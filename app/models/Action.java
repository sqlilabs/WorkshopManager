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
@Table(name = "ACTION")
public class Action extends Model {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1227920506796496166L;

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
	 * The action title
	 */
	@Column(name = "title", length = 1000)
	public String title;
	
	/**
	 * The action description
	 */
	@Column(name = "description", length = 1000)
	public String description;
	
	/**
	 * Définition d'un finder qui va permettre de faire les accès à la base
	 */
	public static Finder<Long, Action> find = new Finder<Long, Action>(Long.class, Action.class);
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructeur
	 */
	public Action() {
		super();
	}
	
	
}
