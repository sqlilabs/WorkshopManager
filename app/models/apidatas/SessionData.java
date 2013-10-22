/**
 * 
 */
package models.apidatas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import models.User;
import models.WorkshopSession;


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
     * @return The event location
	 */
	public String getLocation() {
		return workshopSession.location;
	}

	/**
     * @return the event nextPlay or lastPlay. If null the event has never been played
     */
	public Date getNextPlay() {
		return workshopSession.nextPlay;
	}

	/**
	 * @return the event speaker
	 */
	public UserData getSpeaker() {
		return new UserData(workshopSession.speaker);
	}

	/**
	 * the workshop related to this comment
	 * @return the workshop
	 */
	public WorkshopData getWorkshop() {
		return new WorkshopData(workshopSession.workshop);
	}
	
	/**
	 * The participants of this session.
	 * 
	 * @return the participants
	 */
	public List<UserData> getParticipants() {
		if (workshopSession.participants == null) {
			return null;
		}
		List<UserData> participants = new ArrayList<UserData>(workshopSession.participants.size());
		for (User participant : workshopSession.participants) {
			participants.add(new UserData(participant));
		}
		return participants;
	}

	@JsonIgnore
	public SessionData(WorkshopSession workshopSession) {
		super();
		this.workshopSession = workshopSession;
	}

}
