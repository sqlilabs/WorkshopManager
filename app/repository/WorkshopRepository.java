package repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import models.Workshop;
import models.WorkshopSession;
import models.utils.compare.WorkshopsPlayedComparator;

/**
 * @author ychartois
 *
 */
public class WorkshopRepository {

	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructeur
	 */
	public WorkshopRepository() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-									Queries
	//<--------------------------------------------------------------------------->	
	/**
	 * @return la liste des workshops non joué
	 */
	public static List<Workshop> getWorkshops() {
		List<Workshop> 		unfilter 	= 	Workshop.find
													.fetch("workshopSession")
													.orderBy("creationDate desc")
													.findList();
		
		List<Workshop> 		filter		=	new ArrayList<Workshop>();
		for ( Workshop currentWorkshop : unfilter ) {
			if ( currentWorkshop.workshopSession.size() == 0 ) {
				filter.add( currentWorkshop );
			}
		}
		
		return filter;
	}
	
	/**
	 * @return la liste des workshops planifiés
	 */
	public static List<WorkshopSession> getWorkshopsPlanifie() {	

		return WorkshopSession.find
				.fetch("workshop")
				.fetch("speaker")
				.fetch("workshop.potentialParticipants")
				.fetch("workshop.speakers")
				.where()
					.gt("nextPlay", new Date() )
				.orderBy("nextPlay asc")
				.findList();
	}
	
	/**
	 * @return la liste des workshops déjà présentés
	 */
	public static List<Workshop> getWorkshopsAlreadyPlayed() {
		
		List<Workshop> list		=	Workshop.find
										.fetch("workshopSession")
										.fetch("workshopSession.speaker")
										.fetch("comments")
										.fetch("workshopRessources")
										.where()
											.lt("workshopSession.nextPlay", new Date() )
										.findList();
		
		Collections.sort(list, new WorkshopsPlayedComparator() );
		
		return list;
	}

}