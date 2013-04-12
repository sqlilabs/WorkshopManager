package controllers;

import static models.utils.constants.ApplicationConstants.UUID;

import java.util.List;

import models.Workshop;
import play.Play;
import play.db.ebean.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import views.html.welcome.charter;
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
	@Transactional()
	public static Result welcome() {
		Context.current().args.put("ongletActif", "home");
		// We render the welcome page
		return ok(welcome.render("Accueil", WorkshopDAO.getWorkshopsPlanifie()));
	}
	
	/**
	 * This a REST webservice which return the planned workshops list
	 * 
	 * @return Result the http response
	 */
	@Transactional()
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result wsWorkshopsPlanifies() {
		List<Workshop> wsPlanifies = WorkshopDAO.getWorkshopsPlanifie();
		return ok(play.libs.Json.toJson(wsPlanifies));
	}
	
	/**
	 * This action allow to display the list of the already played workshop
	 * 
	 * @return Result the http response
	 */
	@Transactional()
	public static Result workshops() {
		Context.current().args.put("ongletActif", "alreadyPlayed");
		// We render the welcome page
		return ok(alreadyPlayed.render("Les Workshops déjà présentés", WorkshopDAO.getWorkshopsAlreadyPlayed()));
	}
	
	/**
	 * This action allow to display the charter agreement page
	 * 
	 * @return the charter agreement page
	 */
	public static Result charter() {
		// We render the charter page
		return ok( charter.render(false) );
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
	
	/**
	 * Get the uuid of the session or create one if never defined
	 * 
	 * @return the uuid of this session
	 */
	public static String getUuid() {
		// Generate a unique ID
		String 		uuid		=	session(UUID);
		if( uuid == null ) {
			uuid				=	java.util.UUID.randomUUID().toString();
			session(UUID, uuid);
		}
		
		return uuid; 
	}
	
	/**
	 * Retreive the bug manager url
	 * 
	 * @return url of the bug manager
	 */
	public static String getBugManagerUrl() {
		return Play.application().configuration().getString("bug.manager.link");
	}
	
	
	
}
