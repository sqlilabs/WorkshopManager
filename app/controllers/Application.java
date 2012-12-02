package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http.Context;
import play.mvc.Result;
import views.html.welcome.welcome;
import views.html.workshops.alreadyPlayed;

public class Application extends Controller {
    

	// <--------------------------------------------------------------------------->
	// - 							Actions Methods
	// <--------------------------------------------------------------------------->	
	/**
	 * This method is the action that render the welcome page
	 * 
	 * @return Result the http response
	 */
	@Transactional(readOnly = true)
	public static Result welcome() {
		Context.current().args.put("wsPlanifie", WorkshopController.getWorkshopsPlanifie());  
		// We render the welcome page
		return ok(welcome.render("Workshop Manager", WorkshopController.getWorkshops()));
	}
	
	/**
	 * This action allow to display the list of the already played workshop
	 * 
	 * @return Result the http response
	 */
	@Transactional(readOnly = true)
	public static Result workshops() {
		// We render the welcome page
		return ok(alreadyPlayed.render("Les Workshops déjà présentés", WorkshopController.getWorkshopsAlreadyPlayed()));
	}
	
}
