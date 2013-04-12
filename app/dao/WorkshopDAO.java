/**
 * 
 */
package dao;

import java.util.Date;
import java.util.List;

import models.Workshop;

/**
 * @author ychartois
 *
 */
public class WorkshopDAO {

	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructeur
	 */
	public WorkshopDAO() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-									Queries
	//<--------------------------------------------------------------------------->	
	/**
	 * @return la liste des workshops non joué
	 */
	public static List<Workshop> getWorkshops() {
		return Workshop.find.where()
					.isNull("workshopSession")
					.orderBy("creationDate desc")
					.findList();
	}
	
	/**
	 * @return la liste des workshops planifiés
	 */
	public static List<Workshop> getWorkshopsPlanifie() {
		return Workshop.find
				.fetch("workshopSession")
				.fetch("workshopSession.speaker")
				.fetch("potentialParticipants")
				.fetch("speakers")
				.where()
					.isNotNull("workshopSession.nextPlay")
					.gt("workshopSession.nextPlay", new Date())
					.orderBy("workshopSession.nextPlay asc")
					.findList();
	}
	
	/**
	 * @return la liste des workshops déjà présentés
	 */
	public static List<Workshop> getWorkshopsAlreadyPlayed() {
		return Workshop.find
				.fetch("workshopSession")
				.fetch("workshopSession.speaker")
				.fetch("comments")
				.fetch("workshopRessources")
				.where()
					.isNotNull("workshopSession.nextPlay")
					.lt("workshopSession.nextPlay", new Date() )
					.orderBy("workshopSession.nextPlay desc")
					.findList();
	}

}