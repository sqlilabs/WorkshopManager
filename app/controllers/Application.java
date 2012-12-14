package controllers;

import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import views.html.welcome.welcome;
import views.html.workshops.alreadyPlayed;
import dao.WorkshopDAO;

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
		Context.current().args.put("wsPlanifie", WorkshopDAO.getWorkshopsPlanifie()); 
		Context.current().args.put("ongletActif", "home");
		// We render the welcome page
		return ok(welcome.render("Accueil", WorkshopDAO.getWorkshops()));
	}
	
	/**
	 * This action allow to display the list of the already played workshop
	 * 
	 * @return Result the http response
	 */
	@Transactional(readOnly = true)
	public static Result workshops() {
		Context.current().args.put("wsPlanifie", WorkshopDAO.getWorkshopsPlanifie());
		Context.current().args.put("ongletActif", "alreadyPlayed");
		// We render the welcome page
		return ok(alreadyPlayed.render("Les Workshops déjà présentés", WorkshopDAO.getWorkshopsAlreadyPlayed()));
	}
	
	
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->
	/**
	 * @param tabName le nom du tab à tester
	 * @return le code html du style css à appliquer si c'est l'onglet selectionné
	 */
	public static String isActive( String tabName ) {
		String currentTab = (String) Http.Context.current().args.get("ongletActif");
		return tabName.equals( currentTab ) ? "class=active" : "";
	}
	
	
}
