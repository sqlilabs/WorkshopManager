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
public class PlanifiedDateComparatorAscendant implements Comparator<Workshop>{

	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * Constructor
	 */
	public PlanifiedDateComparatorAscendant() {
		super();
	}

	
	//<--------------------------------------------------------------------------->
	//-						Implements Comparable   
	//<--------------------------------------------------------------------------->		
	@Override
	public int compare( Workshop workshop1, Workshop workshop2 ) {
		WorkshopSession 	session1 	= 	workshop1.getWorkshopSession();
		WorkshopSession 	session2 	= 	workshop2.getWorkshopSession();
		
		if ( session1 == null || session2 == null ) {
			return -1;
		}
		
		Date 				date1 		=	session1.getNextPlay();
		Date 				date2 		=	session2.getNextPlay();	
		
		if ( date1 == null || date2 == null ) {
			return -1;
		}
		
		return -date1.compareTo(date2);
	}
	
	


}
