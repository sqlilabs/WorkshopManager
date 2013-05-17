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
	 * @return la liste des actions
	 */
	public static List<Action> getActions() {
		PagingList<Action> 		list 	= 	Action.find.where()
											.orderBy("creationDate desc")
											.findPagingList(20); // On limite au 20 dernières actions	
		return list.getPage(0).getList();
	}

}
