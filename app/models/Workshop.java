/**
 * 
 */
package models;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Le Java Bean contenant les informations génériques du Workshop.
 * 
 * @author cachavezley
 * @see WorkshopSession
 */
public class Workshop {

	/**
	 * Le sujet du workshop.
	 */
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
	 * Le speaker proposé du workshop
	 */
	private Queue<User> speakers = new LinkedList<>();
	
	/**
	 * Constructeur par défaut
	 */
	public Workshop() {
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
	 * TODO on devrait retourner ça ou une copie pour garder la cohérence interne?
	 * @return the speakers
	 */
	public Queue<User> getSpeakers() {
		return speakers;
	}

	/**
	 * TODO ça sert à qqch cette méthode ou on devrait avoir de méthodes
	 * addSpeaker(...) et removeSpeaker(...) directement ici dans la classe
	 * Workshop?
	 * @param speakers the speakers to set
	 */
	public void setSpeakers(Queue<User> speakers) {
		this.speakers = speakers;
	}
	
	
}
