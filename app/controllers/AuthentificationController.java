package controllers;

import static models.utils.constants.AuthentificationConstants.*;
import play.Play;
import play.api.Application;
import play.api.templates.Html;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.header;
import views.html.welcome.welcome;
import dao.WorkshopDAO;

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
	 * 
	 * @return
	 */
	public static Result callback() {
		
		String codeRetour = request().queryString().get(CALLBACK_GOOGLE_CODE)[CALLBACK_GOOGLE_INFO_INDEX];
		
		Html html = header.render(codeRetour);
		return ok( html );
	}
	
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->

	/**
	 * Build the Google OAuth link
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
}
