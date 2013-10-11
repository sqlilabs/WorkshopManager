/**
 * 
 */
package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


/**
 * Une séance d'un Workshop.
 * 
 * @author cachavezley
 *
 */
@Entity
@Table(name = "WORKSHOP_SESSION")
public class WorkshopSession extends Model {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9112271812179769416L;

	/**
	 * L'identifiant
	 */
	@Id
	@GeneratedValue
	public Long id;
	
	/**
	 * L'endroit où le workshop va se dérouler.
	 */
	@Required
	@Column(length=50)
	public String location;
	
	/**
	 * Limite de place de la session
	 */ 
	@Column(name = "limite_place",length=2)
	@NotNull
	public int limitePlace;
	
	/**
	 * Date à laquelle le workshop est planifié ou a été joué. Si null alors c'est 
	 * que le workshop n'a jamais été planifié
	 */
	@Required
	public Date nextPlay;	
	
	/**
	 * La personne qui donnera cette séance du workshop.
	 */
	@ManyToOne(cascade=CascadeType.PERSIST)
	public User speaker;

	/**
	 * the workshop related to this comment
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "workshop_id")
	public Workshop workshop;
	
	/**
	 * Les personnes inscritent au workshop
	 */
	@ManyToMany()
	@JoinTable(
			name="PARTICIPANTS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> participants = new HashSet<User>();
	
	/**
	 * Définition d'un finder qui va permettre de faire les accès à la base
	 */
	public static Finder<Long, WorkshopSession> find = new Finder<Long, WorkshopSession>(Long.class, WorkshopSession.class);
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par défaut.
	 */
	public WorkshopSession() {
	}
	
}
