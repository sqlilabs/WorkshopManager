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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
	@Id
    @GeneratedValue
	private Long id;

	/**
	 * Le sujet du workshop.
	 */
	@Required
	private String subject;
	
	/**
	 * Résumé court du workshop
	 */
	@Column(length=300)
	@Required
	private String summary;
	
	/**
	 * La description du contenu du workshop.
	 */
	@Column(length = 1000)
	private String description;
	
	/**
	 * L'url de l'image à utiliser.
	 */
	private String image;
	
	/**
	 * Les speakers proposé du workshop
	 */
	@ManyToMany
	@JoinTable(
			name="WORKSHOP_SPEAKERS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> speakers = new HashSet<User>();
	
	/**
	 * La WorkshopSession contient les informations relatives à la planification du Workshop
	 */
	@OneToOne
	private WorkshopSession workshopSession;
	
	/**
	 * Who created the workshop
	 */
	@ManyToOne
	private User author;
	
	/**
	 * Les personnes intéressées par le workshop
	 */
	@ManyToMany
	@JoinTable(
			name="POTENTIAL_PARTICIPANTS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> potentialParticipants = new HashSet<User>();
	
	/**
	 * Les commentaires du workshop
	 */
	@OneToMany(mappedBy="workshop")
	private Set<Comment> comments = new HashSet<Comment>();
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par défaut
	 */
	public Workshop() {
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
	public Set<User> getSpeakers() {
		return speakers;
	}

	/**
	 * @param speakers the speakers to set
	 */
	public void setSpeakers(Set<User> speakers) {
		this.speakers = speakers;
	}


	/**
	 * @return the workshopSession
	 */
	public WorkshopSession getWorkshopSession() {
		return workshopSession;
	}


	/**
	 * @param workshopSession the workshopSession to set
	 */
	public void setWorkshopSession(WorkshopSession workshopSession) {
		this.workshopSession = workshopSession;
	}


	/**
	 * @return the author
	 */
	public User getAuthor() {
		return author;
	}


	/**
	 * @param author the author to set
	 */
	public void setAuthor(User author) {
		this.author = author;
	}


	/**
	 * @return the potentialParticipants
	 */
	public Set<User> getPotentialParticipants() {
		return potentialParticipants;
	}


	/**
	 * @param potentialParticipants the potentialParticipants to set
	 */
	public void setPotentialParticipants(Set<User> potentialParticipants) {
		this.potentialParticipants = potentialParticipants;
	}


	/**
	 * @return the comments
	 */
	public Set<Comment> getComments() {
		return comments;
	}


	/**
	 * @param comments the comments to set
	 */
	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}


	/**
	 * @return the summary
	 */
	public String getSummary() {
		return summary;
	}


	/**
	 * @param summary the summary to set
	 */
	public void setSummary(String summary) {
		this.summary = summary;
	}
	
	
}
