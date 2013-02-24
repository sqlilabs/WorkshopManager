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
	private Long id;
	
	/**
	 * The workshop support file (as pdf, doc, ppt, ...)
	 */
	@Column(name = "ws_support_file")
	private String workshopSupportFile;
	
	/**
	 * Date of which the comment was created
	 */
	@Column(name = "ws_support_link")
	@Pattern(value="http://(.*)",message="constraint.workshopsession.url")
	private String workshopSupportLink;
	 
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructeur
	 */
	public Ressources() {
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
	 * @return the workshopSupportFile
	 */
	public String getWorkshopSupportFile() {
		return workshopSupportFile;
	}

	/**
	 * @param workshopSupportFile the workshopSupportFile to set
	 */
	public void setWorkshopSupportFile(String workshopSupportFile) {
		this.workshopSupportFile = workshopSupportFile;
	}

	/**
	 * @return the workshopSupportLink
	 */
	public String getWorkshopSupportLink() {
		return workshopSupportLink;
	}

	/**
	 * @param workshopSupportLink the workshopSupportLink to set
	 */
	public void setWorkshopSupportLink(String workshopSupportLink) {
		this.workshopSupportLink = workshopSupportLink;
	}
}
