/**
 * 
 */
package controllers;

import java.util.List;

import javax.persistence.TypedQuery;

import models.Workshop;
import play.api.templates.Html;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.welcome.welcome;
import views.html.workshops.addWorkshop;

/**
 * Ce controller regroupe toutes les actions qui sont liées à l'ajout d'un nouveau Workshop
 * 
 * @author ychartois
 * @version 0.1
 */
public class WorkshopController extends Controller {
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par defaut
	 */
	public WorkshopController() {
		super();
	}
	
	/**
     * Defines a form wrapping the User class.
     */ 
    final static Form<Workshop> workshopForm = form(Workshop.class);
  
    /**
     * Display a blank form.
     */ 
    public static Result blank() {
        return ok(addWorkshop.render(workshopForm));
    }
    
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->	
    /*
	 * TODO Remplacer les helpers par un DAO
     */
	/**
	 * @return
	 */
	public static List<Workshop> getWorkshops() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws", Workshop.class);
		List<Workshop> list = query.getResultList();
		return list;
	}
  
    /**
     * Handle the 'new workshop form' submission 
     */
    @Transactional
    public static Result save() {
    	Form<Workshop> filledForm = workshopForm.bindFromRequest();
    	
        if (filledForm.hasErrors()) {
            return badRequest(addWorkshop.render(workshopForm));
        }
        
        //Sauver l'objet en base
        Workshop workshop = filledForm.get();
		JPA.em().persist(workshop);
		
		Html html = welcome.render("Workshop Manager", getWorkshops());
        
        return ok(html);
    }
	
	/**
	 * @param id l'identifiant du workshop
	 * @return
	 */
	@Transactional
	public static Result deleteWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		JPA.em().remove(ws);
		
		Html html = welcome.render("Workshop Manager", getWorkshops());
		
		return ok(html);
	}

}