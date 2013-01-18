package controllers;

import static models.utils.constants.AuthentificationConstants.*;
import static models.utils.constants.UserConstants.*;

import models.User;
import models.Workshop;
import models.WorkshopSession;

import org.codehaus.jackson.JsonNode;

import dao.WorkshopDAO;

import play.Play;
import play.cache.Cache;
import play.db.jpa.Transactional;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import views.html.welcome.welcome;
import views.html.errors.error;

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
			//TODO: a externalisé avec la méthode de Rémi
			return ok ( error.render("Vous avez refusé l'identification via votre compte Google, vous ne pourrez pas profiter des privilèges liés aux utilisateurs identifiés. Google nous a retourné l'erreur suivante: [" + errorCode + "]") );
		}
		
		// Request an Access token
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
		Cache.set( Application.getUuid() + "connectedUser", new UserService().handleUserFromGoogleResponse( result ) );	
		
		return ok( welcome.render("Accueil", WorkshopDAO.getWorkshops() ));
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
		
		return ok( welcome.render("Accueil", WorkshopDAO.getWorkshops() ));
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
		return ROLE_ADMIN.equals( user != null ? user.getRole() : null);
	}
	
	public static boolean isSessionSpeaker( WorkshopSession session ) {
		User	user 	=	getUser();
		
		if ( user == null ) {
			return false;
		}
		
		return user.getFirstName().equals( session.getSpeaker().getFirstName() ) 
				&& user.getLastName().equals( session.getSpeaker().getLastName() ) ;
	}
	
	public static boolean isAuthor( Workshop worshop ) {
		User	user 	=	getUser();
		
		if ( user == null || worshop == null || worshop.getAuthor() == null) {
			return false;
		}
		
		return user.getFirstName().equals( worshop.getAuthor().getFirstName() ) 
				&& user.getLastName().equals( worshop.getAuthor().getLastName() ) ;
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
