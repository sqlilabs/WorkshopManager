/**
 * 
 */
package controllers.data;

import java.util.Date;

import models.WorkshopSession;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * @author Christophe
 * 
 */
public class SessionData {
	@JsonIgnore
	private transient WorkshopSession workshopSession;

	/**
	 * 
	 */
	public SessionData() {
		super();
	}

	/**
	 * Id de la session.
	 * 
	 * @return id
	 */
	public Long getId() {
		return workshopSession.id;
	}

	/**
	 * L'url du doodle qui contient les participants à cette séance.
	 * 
	 * @return doodlUrl
	 */
	public String getDoodleUrl() {
		return workshopSession.doodleUrl;
	}

	/**
	 * L'endroit où le workshop va se dérouler.
	 */
	public String getLocation() {
		return workshopSession.location;
	}

	/**
	 * Date à laquelle le workshop est planifié ou a été joué. Si null alors
	 * c'est que le workshop n'a jamais été planifié
	 */
	public Date getNextPlay() {
		return workshopSession.nextPlay;
	}

	/**
	 * La personne qui donnera cette séance du workshop.
	 */
	public UserData getSpeaker() {
		return new UserData(workshopSession.speaker);
	}

	/**
	 * the workshop related to this comment
	 */
	public WorkshopData getWorkshop() {
		return new WorkshopData(workshopSession.workshop);
	}

	@JsonIgnore
	public SessionData(WorkshopSession workshopSession) {
		super();
		this.workshopSession = workshopSession;
	}

}
