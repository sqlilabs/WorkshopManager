package models.utils.compare;

import java.util.Comparator;
import java.util.Date;

import models.Workshop;

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
		Date workshop1 = getMostRecentSession( o1 );
		Date workshop2 = getMostRecentSession( o2 );
		
		return -(workshop1.compareTo(workshop2));
	}
	
	//<--------------------------------------------------------------------------->
	//-								private methods      
	//<--------------------------------------------------------------------------->		
	/**
	 * Return the most recent session from the session list
	 * 
	 * @param workshop the workshop to test
	 * @return the most recent session
	 */
	private Date getMostRecentSession( Workshop workshop ) {
				
		Date sessionDate	=	workshop.workshopSession.get(0).nextPlay;
		
		if ( workshop.workshopSession.size() == 1 )
			return sessionDate;
		
		for ( int i = 1; i < workshop.workshopSession.size(); i++ ) {
			if ( sessionDate.before( workshop.workshopSession.get(i).nextPlay ) ) {
				sessionDate	=	workshop.workshopSession.get(i).nextPlay;
			}
		}
		
		return sessionDate;
	}
}
