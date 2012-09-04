/**
 * 
 */
package models;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import play.data.validation.Constraints.Required;

/**
 * Le Java Bean contenant les informations génériques du Workshop.
 * 
 * @author cachavezley
 * @see WorkshopSession
 */
@Entity
@Table(name = "WORKSHOP")
public class Workshop implements Serializable {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6669398454928349805L;

	/**
	 * L'identifiant
	 */
	private Long id;

	/**
	 * Le sujet du workshop.
	 */
	@Required
	private String subject;
	
	/**
	 * La description du contenu du workshop.
	 */
	private String description;
	
	/**
	 * L'url de l'image à utiliser.
	 */
	private String image;
	
	/**
	 * Les speakers proposé du workshop
	 */
	private Set<User> speakers = new HashSet<>();
	
	/**
	 * Constructeur par défaut
	 */
	public Workshop() {
	}

	/**
	 * @return the id
	 */
	@Id
    @GeneratedValue
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
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the description
	 */
	@Column(length = 1000)
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}

	/**
	 * @return the speakers
	 */
	@OneToMany
	@JoinTable(
			name="WORKSHOP_SPEAKERS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> getSpeakers() {
		return speakers;
	}

	/**
	 * @param speakers the speakers to set
	 */
	public void setSpeakers(Set<User> speakers) {
		this.speakers = speakers;
	}
	
}
