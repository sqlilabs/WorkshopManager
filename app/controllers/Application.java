package controllers;

import static models.utils.constants.ApplicationConstants.UUID;
import play.Play;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import repository.WorkshopRepository;
import views.html.welcome.charter;
import views.html.welcome.welcome;
import views.html.workshops.alreadyPlayed;
import views.html.workshops.newWorkshops;


/**
 * The main controller of the app which redirect to the main views
 *
 * @author ychartois
 */
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
		Context.current().args.put("activeTab", "home");
		// We render the welcome page
		return ok(welcome.render(Messages.get("application.home"), WorkshopRepository.getWorkshopsPlanifie()));
	}
	
	/**
	 * This action allow to display the list of the already played workshop
	 * 
	 * @return Result the http response
	 */
	@Transactional()
	public static Result workshops() {
		Context.current().args.put("activeTab", "alreadyPlayed");
		// We render the welcome page
		return ok(alreadyPlayed.render(Messages.get("application.alreadyPlayed"), WorkshopRepository.getWorkshopsAlreadyPlayed()));
	}
	
	/**
	 * This action allow to display the list of the already played workshop
	 * 
	 * @return Result the http response
	 */
	@Transactional()
	public static Result newWorkshops() {
		Context.current().args.put("activeTab", "newWorkshops");
		// We render the welcome page
		return ok( newWorkshops.render(Messages.get("application.newWorkshops"), WorkshopRepository.getWorkshops()) );
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
	 * Test if the current tab is active
     *
     * @param tabName the tab name
     *
	 * @return the HTML code which apply the css active property on the selected tab
	 */
	public static String isActive( String tabName ) {
		String currentTab = (String) Http.Context.current().args.get("activeTab");
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

    /**
     * Simply the call to properties in application.conf
     *
     * @param properties the property to retrieve
     *
     * @return the value of the property in the application.conf file
     */
    public static String conf( String properties ) {
        return Play.application().configuration().getString(properties);
    }

}
