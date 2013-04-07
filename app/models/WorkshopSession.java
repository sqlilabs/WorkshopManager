/**
 * 
 */
package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.validation.Constraints.Pattern;
import play.data.validation.Constraints.Required;


/**
 * Une séance d'un Workshop.
 * 
 * @author cachavezley
 *
 */
@Entity
@Table(name = "WORKSHOP_SESSION")
public class WorkshopSession implements Serializable {

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
	@Column(name = "doodle_url")
	public String doodleUrl;
	
	/**
	 * L'endroit où le workshop va se dérouler.
	 */
	@Required
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
	@ManyToOne
	public User speaker;
	
	/**
	 * Constructeur par défaut.
	 */
	public WorkshopSession() {
	}
	
}
