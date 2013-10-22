package models.utils.compare;

import java.util.Comparator;
import java.util.Date;

import models.Workshop;
import models.WorkshopSession;
import models.utils.helpers.EventsUtils;

public class WorkshopsPlayedComparator implements Comparator<Workshop>{

	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructeur
	 */
	public WorkshopsPlayedComparator() {
		super();
	}

	
	//<--------------------------------------------------------------------------->
	//-							Implements Comparator        
	//<--------------------------------------------------------------------------->		
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Workshop o1, Workshop o2) {
		Date workshop1 = EventsUtils.getMostRecentSession(o1).nextPlay;
		Date workshop2 = EventsUtils.getMostRecentSession(o2).nextPlay;
		
		return -(workshop1.compareTo(workshop2));
	}
}
