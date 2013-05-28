package services;

import static models.utils.constants.AuthentificationConstants.*;

import org.codehaus.jackson.JsonNode;

import play.libs.WS;

import repository.UserRepository;


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
	 * <p>
	 * Return user info from oAuth token.
	 * </p>
	 * 
	 * @param accessToken
	 *            access token
	 * @return json that represents the user info
	 */
	public JsonNode getUserInfo(String accessToken) {
		return WS.url(GOOGLE_USER_INFO_URL).setQueryParameter(GOOGLE_ACCESS_TOKEN, accessToken).get().get().asJson(); 
	}
	
	
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
		User		user 		=	UserRepository.getUserWithName( googleResponse.get(GOOGLE_FIRST_NAME).asText()  + " " + googleResponse.get(GOOGLE_LAST_NAME).asText() ) ;
		
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
