/**
 * 
 */
package repository;

import java.util.List;

import com.avaje.ebean.PagingList;

import models.Action;

/**
 * @author ychartois
 *
 */
public class ActionsRepository {

	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructor
	 */
	public ActionsRepository() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-								Méthodes DAO
	//<--------------------------------------------------------------------------->	
	/**
	 * @return the action list
	 */
	public static List<Action> getActions() {
		PagingList<Action> 		list 	= 	Action.find.where()
											.orderBy("creationDate desc")
											.findPagingList(15); // Only the 15 last actions
		return list.getPage(0).getList();
	}

}
