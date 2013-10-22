package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Pattern;
import play.db.ebean.Model;

/**
 * This Object represent an event ressource
 * 
 * @author ychartois
 *
 */
@Entity
@Table(name = "RESSOURCE")
public class Ressources extends Model {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7924212583471038287L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	public Long id;
	
	/**
	 * The workshop support file (as pdf, doc, ppt, ...)
	 */
	@Column(name = "ws_support_file")
	public String workshopSupportFile;
	
	/**
	 * Date of which the comment was created
	 */
	@Column(name = "ws_support_link")
	@Pattern(value="http://(.*)",message="constraint.workshopsession.url")
	public String workshopSupportLink;
	 
	/**
	 * the Finder definition which allows to request the object in database
	 */
	public static Finder<Long, Ressources> find = new Finder<Long, Ressources>(Long.class, Ressources.class);
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructor
	 */
	public Ressources() {
		super();
	}
}
