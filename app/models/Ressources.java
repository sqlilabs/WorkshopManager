package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.data.validation.Constraints.Pattern;

/**
 * This Object represent a comments on a Workshop
 * 
 * @author ychartois
 *
 */
@Entity
@Table(name = "RESSOURCE")
public class Ressources implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -7924212583471038287L;

	/**
	 * L'identifiant
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
	 
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructeur
	 */
	public Ressources() {
		super();
	}
}
