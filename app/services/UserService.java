package services;

import models.User;
import play.libs.OpenID;

/**
 * This class contains the methods relative to Users
 *
 * @author ychartois
 *
 */
public class UserService {

	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->
	/**
	 * Constructor
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
