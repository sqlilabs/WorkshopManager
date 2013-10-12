package controllers;

import com.avaje.ebean.Ebean;
import models.Roles;
import models.User;
import models.Workshop;
import models.WorkshopSession;
import play.cache.Cache;
import play.db.ebean.Transactional;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import java.util.Map;

/**
 * This controller got the action that allow the user to authenticate
 * 
 * @author ychartois
 */
public class UsersController extends Controller {
	
	//<--------------------------------------------------------------------------->
	//-							 Actions(s)	        
	//<--------------------------------------------------------------------------->	
    /**
	 * Create a new user after his acceptance to the charter
	 * 
	 * @return the welcome page
	 */
	@Transactional
	public static Result createNewUser() {
		
		// We retrieve the new user from cache and persist it
		User 	user 		= 	(User) Cache.get( Application.getUuid() + "newUser" );
		user.charterAgree	=	true;
		user.role			=	Roles.valueOf( Application.conf("default.role") );
		user.save();
		
		// The new user is now connected
		Cache.set( Application.getUuid() + "connectedUser", user );

		return redirect(routes.Application.welcome());
	}

	/**
	 * WS allows to modify the user picture
	 * 
	 * @return the html status ok
	 */
    @Security.Authenticated(Secured.class)
	public static Result modifyUserPicture() {
		User 					user 	= 	Secured.getUser();
		Map<String, String[]> 	data 	= 	request().body().asFormUrlEncoded();
		user.picture					= 	data.get("image")[0];	
		Ebean.save( user );
		return ok("{image: " + user.picture + "}").as("application/json");
	}
	
	
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <---------------------------------------------------------------------------
	/**
	 * Determine if the connected user is the speaker of the session
	 * 
	 * @param session the workshop session
	 * 
	 * @return true if the connected user is the speaker of the session
	 */
	public static boolean isSessionSpeaker( WorkshopSession session ) {
		User	user 	=	Secured.getUser();
		
		if ( user == null ) {
			return false;
		}
		
		return user.firstName.equals( session.speaker.firstName ) 
				&& user.lastName.equals( session.speaker.lastName ) ;
	}
	
	/**
	 * Determine if the connected user is the author of the workshop
	 * 
	 * @param workshop a workshop
	 * 
	 * @return true if the connected user is the author of the workshop
	 */
	public static boolean isAuthor( Workshop workshop ) {
		User	user 	=	Secured.getUser();
		
		if ( user == null || workshop == null || workshop.author == null) {
			return false;
		}
		
		return user.firstName.equals( workshop.author.firstName )
				&& user.lastName.equals( workshop.author.lastName ) ;
	}
	
}
