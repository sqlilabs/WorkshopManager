package repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import models.Type;
import models.Workshop;
import models.WorkshopSession;
import models.utils.compare.WorkshopsPlayedComparator;

/**
 * @author ychartois
 *
 */
public class WorkshopRepository {

	
	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructor
	 */
	public WorkshopRepository() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-									Queries
	//<--------------------------------------------------------------------------->	
	/**
	 * @return The list of events with no workshopSession
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
	 * @return The list of events with a workshopSession which is not yet gone
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
	 * @return The list of events with a workshopSession which is gone
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

    /**
     * @return all the event types
     */
    public static List<String> getAllTypes() {

        List<Type> types = Type.find.all();

        List<String> toReturn = new ArrayList<>(types.size());
        for (Type type : types) {
            toReturn.add( type.name );
        }

        return toReturn;
    }

}