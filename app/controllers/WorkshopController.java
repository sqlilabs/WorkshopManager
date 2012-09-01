/**
 * 
 */
package controllers;

import java.util.ArrayList;
import java.util.List;

import models.Workshop;
import play.data.Form;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.workshops.addWorkshop;
import views.html.welcome.welcome;

/**
 * Ce controller regroupe toutes les actions qui sont liées à l'ajout d'un nouveau Workshop
 * 
 * @author ychartois
 * @version 0.1
 */
public class WorkshopController extends Controller {
	
	/**
     * Defines a form wrapping the User class.
     */ 
    final static Form<Workshop> workshopForm = form(Workshop.class);
  
    /**
     * Display a blank form.
     */ 
    public static Result blank() {
        return ok( addWorkshop.render(workshopForm) );
    }
  
    /**
     * Handle the 'new workshop form' submission 
     */
    @Transactional
    public static Result save() {
    	workshopForm.bindFromRequest();
    	
        if( workshopForm.hasErrors() ) {
            return badRequest(addWorkshop.render(workshopForm));
        }
        
        //Sauver l'objet en base
        //TODO
        
        return ok(welcome.render("Workshop Manager", getWorkshops()));
    }
    
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->	
	public static List<String> getWorkshops() {
		List<String> wks = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("WorksShop_");
			sb.append(i);
			wks.add(sb.toString());
		}
		return wks;
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par defaut
	 */
	public WorkshopController() {
		super();
	}

}
