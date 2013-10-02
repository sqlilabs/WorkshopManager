package services;

import static models.utils.constants.AuthentificationConstants.*;

import com.avaje.ebean.Ebean;
import org.codehaus.jackson.JsonNode;

import play.cache.Cache;
import play.libs.OpenID;
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
     * Create a new user
     *
     * @return the new user
     */
    public User createNewUser(OpenID.UserInfo userInfo) {
        // We create the user
        User user = new User();
        user.firstName		= userInfo.attributes.get("FirstName");
        user.lastName		= userInfo.attributes.get("LastName");
        user.email			= userInfo.attributes.get("Email");
        String image        = userInfo.attributes.get("Image");
        user.picture		= image == null || image.isEmpty() ? "/assets/images/avatar-default.png" : image;

        return user;
    }
}
