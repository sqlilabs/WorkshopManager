package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.welcome.welcome;

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
		// We render the welcome page
		return ok(welcome.render("Workshop Manager", WorkshopController.getWorkshops()));
	}
	
	/**
	 * TODO voir à quoi ça sert ce lien ou l'enlever
	 * @return
	 */
	public static Result workshops() {
		return TODO;
	}
	
}
