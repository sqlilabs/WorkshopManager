/**
 * 
 */
package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import play.data.validation.Constraints.Required;
import play.db.jpa.JPA;

/**
 * Le Java Bean contenant les informations génériques du Workshop.
 * 
 * @author cachavezley
 * @see WorkshopSession
 */
@Entity
public class Workshop {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 4522289643447732357L;

	/**
	 * L'identifiant
	 */
	@Id
    @GeneratedValue
	public Long id;

	/**
	 * Le sujet du workshop.
	 */
	@Required
	public String subject;
	
	/**
	 * La description du contenu du workshop.
	 */
	public String description;
	
	/**
	 * L'url de l'image à utiliser.
	 */
	public String image;
	
	/**
	 * Le speaker proposé du workshop
	 */
	//public Queue<User> speakers = new LinkedList<>();
	
	/**
	 * Constructeur par défaut
	 */
	public Workshop() {
	}
	
}
