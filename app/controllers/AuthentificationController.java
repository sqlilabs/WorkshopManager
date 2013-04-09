package controllers;

import static models.utils.constants.AuthentificationConstants.*;
import static models.utils.constants.UserConstants.*;

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
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import views.html.errors.error;
import views.html.welcome.charter;

/**
 * Ce controller regroupe toutes les actions qui sont liées à l'authentification via Google API
 * 
 * @author ychartois
 */
public class AuthentificationController extends Controller {
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par defaut
	 */
	public AuthentificationController() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							 Actions(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Action which handle the authentification by Google and put the authentificated user
	 * in the session
	 * 
	 * @return to welcome page
	 */
	@Transactional
	public static Result callback() {
		
		// On récupère le code fourni par Google
		String[]	codeRetour 				= 	request().queryString().get(CALLBACK_GOOGLE_CODE);
		
		// Si le code n'existe pas c'est qu'il y a une erreur
		if ( codeRetour == null ) {
			String 		errorCode 			= 	request().queryString().get(CALLBACK_GOOGLE_ERROR)[CALLBACK_GOOGLE_INFO_INDEX];

			return ok ( error.render( Messages.get("error.google.authentification.refused", errorCode)) );
		}
		
		// Request an Access tokenarg0 
		Response 	responseAccessToken 	= 	WS.url(GOOGLE_ACCESS_TOKEN_URL)
													.setHeader("Content-Type", "application/x-www-form-urlencoded")
													.post( getAccessTokenParams( codeRetour[CALLBACK_GOOGLE_INFO_INDEX] ) )
													.get();
		
		JsonNode 	resultAccessToken		= 	responseAccessToken.asJson();
		
		// Get the user information
		Response 	response 				= 	WS.url(GOOGLE_USER_INFO_URL)
													.setQueryParameter(GOOGLE_ACCESS_TOKEN, resultAccessToken.get(GOOGLE_ACCESS_TOKEN).asText())
													.get().get();
		
		JsonNode 	result 					= 	response.asJson();
		
		// Call the service that handle the user
		User 		user 					= 	new UserService().handleUserFromGoogleResponse( result );
		
		// If the user is not from sqli he can't connect
		if ( !StringUtils.endsWith( user.email, "@sqli.com" ) ) {
			//TODO un pop-up pour expliquer pourquoi ça serai sympa
			return forbidden();
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
		user.role			=	"standard";
		Ebean.save( user );
		
		// The new user is now connected
		Cache.set( Application.getUuid() + "connectedUser", user );
		
		return redirect(routes.Application.welcome());
	}
	
	/**
	 * Action which handles the deconnection
	 * 
	 * @return the welcome page
	 */
	@Transactional
	public static Result deconnection() {
		// On enlève le user de la session
		Cache.set( Application.getUuid() + "connectedUser", null );	
		
		return redirect(routes.Application.welcome());
	}
	
	
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->

	/**
	 * Build the Google OAuth link. Use by the authentication view
	 * 
	 * @return the Google OAuth link
	 */
	public static String buildGoogleOAuthLink() {
		
		StringBuilder	googleOAuthLink		=	new StringBuilder(GOOGLE_ENDPOINT_URL);
		googleOAuthLink.append("?"); //The QueryString
		
		// Google Client ID
		googleOAuthLink.append(GOOGLE_CLIENT_ID);
		googleOAuthLink.append("=");
		googleOAuthLink.append( Play.application().configuration().getString(GOOGLE_CLIENT_ID_PROP) );
		googleOAuthLink.append("&");
		
		// Google redirect URL
		googleOAuthLink.append(GOOGLE_REDIRECT_URL);
		googleOAuthLink.append("=");
		googleOAuthLink.append(Play.application().configuration().getString(GOOGLE_REDIRECT_URL_PROP));
		googleOAuthLink.append("&");
		
		// Google Scope
		googleOAuthLink.append(GOOGLE_SCOPE);
		googleOAuthLink.append("=");
		googleOAuthLink.append(Play.application().configuration().getString(GOOGLE_SCOPE_PROP));
		googleOAuthLink.append("&");
		
		// Google Response type
		googleOAuthLink.append(GOOGLE_RESPONSE_TYPE);
		googleOAuthLink.append("=");
		googleOAuthLink.append(Play.application().configuration().getString(GOOGLE_RESPONSE_TYPE_PROP));
		
		return googleOAuthLink.toString();
	}
	
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
	
	
	// <--------------------------------------------------------------------------->
	// - 							private methods
	// <--------------------------------------------------------------------------->	
	/**
	 * Build the Google Token request link
	 * 
	 * @param code The authorization code returned from the initial request
	 * 
	 * @return the Google Token request link
	 */
	private static String getAccessTokenParams( String code ) {
	
		StringBuilder	googleAccessTokenParams		=	new StringBuilder();
		
		// Google Client ID
		googleAccessTokenParams.append(GOOGLE_CLIENT_ID);
		googleAccessTokenParams.append("=");
		googleAccessTokenParams.append( Play.application().configuration().getString(GOOGLE_CLIENT_ID_PROP) );
		googleAccessTokenParams.append("&");
		
		// Google redirect URL
		googleAccessTokenParams.append(GOOGLE_REDIRECT_URL);
		googleAccessTokenParams.append("=");
		googleAccessTokenParams.append(Play.application().configuration().getString(GOOGLE_REDIRECT_URL_PROP));
		googleAccessTokenParams.append("&");
		
		// Google code
		googleAccessTokenParams.append(GOOGLE_CODE);
		googleAccessTokenParams.append("=");
		googleAccessTokenParams.append(code);
		googleAccessTokenParams.append("&");
		
		// Google Client secret
		googleAccessTokenParams.append(GOOGLE_CLIENT_SECRET);
		googleAccessTokenParams.append("=");
		googleAccessTokenParams.append(Play.application().configuration().getString(GOOGLE_CLIENT_SECRET_PROP));
		googleAccessTokenParams.append("&");
		
		
		// Google Grant Type
		googleAccessTokenParams.append(GOOGLE_GRANT_TYPE);
		googleAccessTokenParams.append("=");
		googleAccessTokenParams.append(Play.application().configuration().getString(GOOGLE_GRANT_TYPE_PROP));
				
		return googleAccessTokenParams.toString();
	}
	
}
