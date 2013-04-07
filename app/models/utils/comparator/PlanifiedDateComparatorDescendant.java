/**
 * 
 */
package models.utils.comparator;

import java.util.Comparator;
import java.util.Date;

import models.Workshop;
import models.WorkshopSession;

/**
 * @author ychartois
 *
 */
public class PlanifiedDateComparatorDescendant implements Comparator<Workshop>{

	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructor
	 */
	public PlanifiedDateComparatorDescendant() {
		super();
	}

	
	//<--------------------------------------------------------------------------->
	//-						Implements Comparable   
	//<--------------------------------------------------------------------------->		
	@Override
	public int compare( Workshop workshop1, Workshop workshop2 ) {
		WorkshopSession 	session1 	= 	workshop1.workshopSession;
		WorkshopSession 	session2 	= 	workshop2.workshopSession;
		
		if ( session1 == null || session2 == null ) {
			return -1;
		}
		
		Date 				date1 		=	session1.nextPlay;
		Date 				date2 		=	session2.nextPlay;	
		
		if ( date1 == null || date2 == null ) {
			return -1;
		}
		
		return date1.compareTo(date2);
	}
	
	


}
