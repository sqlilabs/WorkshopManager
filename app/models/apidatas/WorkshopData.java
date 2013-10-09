/**
 * 
 */
package models.apidatas;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import models.Comment;
import models.Ressources;
import models.User;
import models.Workshop;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Christophe
 * 
 */
public class WorkshopData {
	
	@JsonIgnore
	private transient Workshop workshop;

	@JsonIgnore
	public WorkshopData(Workshop workshop) {
		super();
		this.workshop = workshop;
	}
	
	public WorkshopData() {
		super();
	}

	/**
	 * L'identifiant
	 */
	public Long getId() {
		return workshop.id;
	}

	/**
	 * Le sujet du workshop.
	 */
	public String getSubject() {
		return workshop.subject;
	}

	/**
	 * Résumé court du workshop
	 */
	public String getSummary() {
		return workshop.summary;
	}

	/**
	 * La description du contenu du workshop.
	 */
	public String getDescription() {
		return workshop.description;
	}

	/**
	 * L'url de l'image à utiliser.
	 */
	public String getImage() {
		return workshop.image;
	}

	/**
	 * Les speakers proposé du workshop
	 */
	public Set<UserData> getSpeakers() {
		Set<UserData> speakers = new HashSet<UserData>();
		for (User speaker : workshop.speakers) {
			speakers.add(new UserData(speaker));
		}
		return speakers;
	}

	/**
	 * Who created the workshop
	 */
	public UserData getAuthor() {
		return new UserData(workshop.author);
	}

	/**
	 * When it as been created
	 */
	public Date getCreationDate() {
		return workshop.creationDate;
	}


	/**
	 * Les personnes intéressées par le workshop
	 */
	public Set<User> getPotentialParticipants() {
		return workshop.potentialParticipants;
	}


	/**
	 * Les commentaires du workshop
	 */
	public Set<Comment> getComments() {
		return null;//workshop.comments;
	}


	/**
	 * Ressources of the workshop (file/link to the workshop support)
	 */
	public Ressources getWorkshopRessources() {
		return workshop.workshopRessources;
	}

}
