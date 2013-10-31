package models.utils.compare;

import models.Comment;
import models.Workshop;
import models.utils.helpers.EventsUtils;

import java.util.Comparator;
import java.util.Date;

public class CommentByDateComparator implements Comparator<Comment>{


	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur
	 */
	public CommentByDateComparator() {
		super();
	}

	
	//<--------------------------------------------------------------------------->
	//-							Implements Comparator        
	//<--------------------------------------------------------------------------->		
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(Comment c1, Comment c2) {
		Date date1 = c1.creationDate;
        Date date2 = c2.creationDate;

		return date2.compareTo(date1);
	}
}
