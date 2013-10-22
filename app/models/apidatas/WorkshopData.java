/**
 * 
 */
package models.apidatas;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.Comment;
import models.Ressources;
import models.User;
import models.Workshop;

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
	 * @return the event id
	 */
	public Long getId() {
		return workshop.id;
	}

	/**
	 * @return the event subject
	 */
	public String getSubject() {
		return workshop.subject;
	}

	/**
	 * @return the event summary
	 */
	public String getSummary() {
		return workshop.summary;
	}

	/**
	 * @return the event description
	 */
	public String getDescription() {
		return workshop.description;
	}

	/**
	 * @return the event url image
	 */
	public String getImage() {
		return workshop.image;
	}

	/**
	 * @return the foreseen speakers
	 */
	public Set<UserData> getSpeakers() {
		Set<UserData> speakers = new HashSet<UserData>();
		for (User speaker : workshop.speakers) {
			speakers.add(new UserData(speaker));
		}
		return speakers;
	}

	/**
	 * @return Who created the workshop
	 */
	public UserData getAuthor() {
		return new UserData(workshop.author);
	}

	/**
	 * @return When it as been created
	 */
	public Date getCreationDate() {
		return workshop.creationDate;
	}


	/**
	 * @return the user interested by this event
	 */
	public Set<User> getPotentialParticipants() {
		return workshop.potentialParticipants;
	}


	/**
	 * @return the event comments
	 */
	public Set<Comment> getComments() {
		return null;//workshop.comments;
	}


	/**
	 * @return Ressources of the workshop (file/link to the workshop support)
	 */
	public Ressources getWorkshopRessources() {
		return workshop.workshopRessources;
	}

}
