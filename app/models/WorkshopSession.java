/**
 * 
 */
package models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import play.data.format.Formats;


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
	 * Date à laquelle le workshop a été donné pour la dernière fois. Si null alors c'est 
	 * que le workshop n'a jamais été donné
	 */
	private Date lastPlay;
	
	/**
	 * Date à laquelle le workshop est planifié. Si null alors c'est 
	 * que le workshop n'est pas planifié
	 */
	private Date nextPlay;	
	
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

	/**
	 * @return the lastPlay
	 */
	public Date getLastPlay() {
		return lastPlay;
	}

	/**
	 * @param lastPlay the lastPlay to set
	 */
	public void setLastPlay(Date lastPlay) {
		this.lastPlay = lastPlay;
	}

	/**
	 * @return the nextPlay
	 */
	public Date getNextPlay() {
		return nextPlay;
	}

	/**
	 * @param nextPlay the nextPlay to set
	 */
	public void setNextPlay(Date nextPlay) {
		this.nextPlay = nextPlay;
	}
	
}
