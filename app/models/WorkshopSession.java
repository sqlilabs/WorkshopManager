/**
 * 
 */
package models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Pattern;
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
	 * L'url du doodle qui contient les participants à cette séance.
	 */
	@Required
	@Pattern(value="http://(.*)",message="constraint.workshopsession.url")
	@Column(name = "doodle_url", length=255)
	public String doodleUrl;
	
	/**
	 * L'endroit où le workshop va se dérouler.
	 */
	@Required
	@Column(length=50)
	public String location;
	
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
