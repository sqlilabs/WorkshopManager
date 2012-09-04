/**
 * 
 */
package models;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

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
	private Long id;
	
	/**
	 * L'url du doodle qui contient les participants à cette séance.
	 */
	private String doodleUrl;
	
	/**
	 * L'endroit où le workshop va se dérouler.
	 */
	private String location;
	
	/**
	 * La date à laquelle le workshop se déroulera.
	 */
	private Calendar date = new GregorianCalendar();
	
	/**
	 * La personne qui donnera cette séance du workshop.
	 */
	private User speaker;
	
	/**
	 * Constructeur par défaut.
	 */
	public WorkshopSession() {
	}

	/**
	 * @return the id
	 */
	@Id
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
	 * @return the doodleUrl
	 */
	@Column(name = "doodle_url")
	public String getDoodleUrl() {
		return doodleUrl;
	}

	/**
	 * @param doodleUrl the doodleUrl to set
	 */
	public void setDoodleUrl(String doodleUrl) {
		this.doodleUrl = doodleUrl;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the date
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Calendar date) {
		this.date = date;
	}

	/**
	 * @return the speaker
	 */
	@ManyToOne
	public User getSpeaker() {
		return speaker;
	}

	/**
	 * @param speaker the speaker to set
	 */
	public void setSpeaker(User speaker) {
		this.speaker = speaker;
	}
	
}
