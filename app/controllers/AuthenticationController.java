package controllers;

import static models.utils.constants.AuthentificationConstants.*;
import static models.utils.constants.UserConstants.*;

import java.util.HashMap;
import java.util.Map;

import models.User;
import models.Workshop;
import models.WorkshopSession;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;

import com.avaje.ebean.Ebean;

import play.Play;
import play.cache.Cache;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.F;
import play.libs.Json;
import play.libs.OpenID;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import services.UserService;
import views.html.errors.error;
import views.html.welcome.charter;

/**
 * Ce controller regroupe toutes les actions qui sont liées à l'authentification via Google API
 * 
 * @author ychartois
 */
public class AuthenticationController extends Controller {
	
	//<--------------------------------------------------------------------------->
	//-							 Actions(s)	        
	//<--------------------------------------------------------------------------->	
    /**
     * this method prepare the OpenID call
     *
     * @return redirect on the verify service
     */
    public static Result auth() {

        // url are defined in Application.conf
        String providerUrl = Play.application().configuration().getString("openID.provider.url");
        String returnToUrl = Play.application().configuration().getString("openID.redirect.url");

        // We construct the OpenID map
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Email", "http://schema.openid.net/contact/email");
        attributes.put("FirstName", "http://schema.openid.net/namePerson/first");
        attributes.put("LastName", "http://schema.openid.net/namePerson/last");
        attributes.put("Image", "http://schema.openid.net/media/image/48x48");

        //We call the OpenID provider
        F.Promise<String> redirectUrl = OpenID.redirectURL(providerUrl, returnToUrl, attributes);

        return redirect( redirectUrl.get() );
    }

    /**
     * this method verify the userInfo, get the user from the database or cache or
     * create it if it does not exist
     *
     * @return @return to welcome page or the charter agreement page
     */
    public static Result verify() {

        // We get the OpenID info of the user
        F.Promise<OpenID.UserInfo> userInfoPromise = OpenID.verifiedId();
        OpenID.UserInfo userInfo = userInfoPromise.get();

        // If the user is not from the specified domain he can't connect
        if ( !StringUtils.endsWith( userInfo.attributes.get("Email"), Play.application().configuration().getString("mail.filter")) ) {
            return forbidden();
        }

        // We add the authenticated user to the session
        session().put("email", userInfo.attributes.get("Email"));

        // We check if it's an existing user in base
        User user = Secured.getUser();

        // if not we create it
        if ( user == null ) {
            user = new UserService().createNewUser(userInfo);
        }

        if ( user.charterAgree  ) {
            // We save the new instance and save it in cache and redirect to welcome page
            Ebean.save( user );
            Cache.set( Application.getUuid() + "connectedUser", user );

            return redirect(routes.Application.welcome());
        }
        else {
            // We save the new user in cache and redirect to charter page
            Cache.set( Application.getUuid() + "newUser", user );
            return ok( charter.render(true) );
        }
    }

	/**
	 * Create a new user after his acceptance to the charter
	 * 
	 * @return the welcome page
	 */
	@Transactional
	public static Result createNewUser() {
		
		// We retreive the new user from cache and persist it
		User 	user 		= 	(User) Cache.get( Application.getUuid() + "newUser" );
		user.charterAgree	=	true;
		user.role			=	Play.application().configuration().getString("default.role");
		Ebean.save( user );
		
		// The new user is now connected
		Cache.set( Application.getUuid() + "connectedUser", user );

		return redirect(routes.Application.welcome());
	}

    /**
     * Allow to quit the app
     *
     * @return the HHTP code OK
     */
    public static Result logout() {
        // We clear the session
        session().clear();

        return ok();
    }
	
	/**
	 * WS allows to modify the user picture
	 * 
	 * @return the html status ok
	 */
	public static Result modifyUserPicture() {
		User 					user 	= 	(User) Cache.get( Application.getUuid() + "connectedUser" );
		Map<String, String[]> 	data 	= 	request().body().asFormUrlEncoded();
		user.picture					= 	data.get("image")[0];	
		Ebean.save( user );
		return ok("{image: " + user.picture + "}");
	}
	
	
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->
	/**
	 * Allow to acess to the authentificated user
	 * 
	 * @return the authentificated user
	 */
	public static User getUser() {
		return (User) Cache.get( Application.getUuid() + "connectedUser");
	}
	
	/**
	 * Allow to identify if the user is an admin
	 * 
	 * @return true if the user is admin
	 */
	public static boolean isAuthenticatedUserAdmin() {
		User	user 	=	getUser();
		return ROLE_ADMIN.equals( user != null ? user.role : null);
	}
	
	/**
	 * Determine if the connected user is the speaker of the session
	 * 
	 * @param session the workshop session
	 * 
	 * @return true if the connected user is the speaker of the session
	 */
	public static boolean isSessionSpeaker( WorkshopSession session ) {
		User	user 	=	getUser();
		
		if ( user == null ) {
			return false;
		}
		
		return user.firstName.equals( session.speaker.firstName ) 
				&& user.lastName.equals( session.speaker.lastName ) ;
	}
	
	/**
	 * Determine if the connected user is the author of the workshop
	 * 
	 * @param worshop a workshop
	 * 
	 * @return true if the connected user is the author of the workshop
	 */
	public static boolean isAuthor( Workshop worshop ) {
		User	user 	=	getUser();
		
		if ( user == null || worshop == null || worshop.author == null) {
			return false;
		}
		
		return user.firstName.equals( worshop.author.firstName ) 
				&& user.lastName.equals( worshop.author.lastName ) ;
	}
	
}
