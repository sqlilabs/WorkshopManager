/**
 * 
 */
package dao;

import java.util.Collections;
import java.util.List;

import javax.persistence.TypedQuery;

import models.Workshop;
import models.utils.comparator.PlanifiedDateComparatorAscendant;
import models.utils.comparator.PlanifiedDateComparatorDescendant;
import play.db.jpa.JPA;

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
	//-								Méthodes DAO
	//<--------------------------------------------------------------------------->	
	/**
	 * @return la liste des workshops non joué
	 */
	public static List<Workshop> getWorkshops() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws WHERE ws.workshopSession IS null", Workshop.class);
		List<Workshop> list = query.getResultList();
		return list;
	}
	
	/**
	 * @return la liste des workshops planifiés
	 */
	public static List<Workshop> getWorkshopsPlanifie() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws WHERE ws.workshopSession.nextPlay IS NOT null AND ws.workshopSession.nextPlay >= NOW()", Workshop.class);
		List<Workshop> list = query.getResultList();
		
		// On trie la liste dans le sens décroissant
		Collections.sort(list, new PlanifiedDateComparatorDescendant());
		
		return list;
	}
	
	/**
	 * @return la liste des workshops déjà présentés
	 */
	public static List<Workshop> getWorkshopsAlreadyPlayed() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws WHERE ws.workshopSession.nextPlay IS NOT null AND ws.workshopSession.nextPlay <= NOW()", Workshop.class);
		List<Workshop> list = query.getResultList();
		
		// On trie la liste dans le sens croissant
		Collections.sort(list, new PlanifiedDateComparatorAscendant());
				
		return list;
	}

}