package services;

import static models.utils.constants.AuthentificationConstants.*;

import org.codehaus.jackson.JsonNode;

import dao.UserDAO;

import models.User;

/**
 * Cette classe regroupe les services lié aux Users
 * @author ychartois
 *
 */
public class UserService {

	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par defaut
	 */
	public UserService() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							 Service(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Get the user from the database, update it or creat it with the informations 
	 * returned by the user info link
	 * 
	 * @param googleResponse the JSON Node returned by the user info link
	 * 
	 * @return user from base or new user
	 */
	public User handleUserFromGoogleResponse( JsonNode googleResponse ) {
		
		// We get the user from the database if it exists
		User		user 		=	UserDAO.getUserWithName( googleResponse.get(GOOGLE_FIRST_NAME).asText()  + " " + googleResponse.get(GOOGLE_LAST_NAME).asText() ) ;
		
		// If not, we create it
		if ( user == null ) {
			user					=	new User();
			user.firstName			=	googleResponse.get(GOOGLE_FIRST_NAME).asText();
			user.lastName			=	googleResponse.get(GOOGLE_LAST_NAME).asText();
			JsonNode 	picture		=	googleResponse.get(GOOGLE_PICTURE);
			user.picture			=	picture != null ? picture.asText() : "/assets/images/avatar-default.png";
		}
		
		// Dans tous les cas on met son email à jour
		user.email				=	googleResponse.get(GOOGLE_EMAIL).asText();
		
		return user;
	}

}
