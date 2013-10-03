package controllers;

import models.Roles;
import models.User;

import org.apache.commons.lang3.StringUtils;

import play.cache.Cache;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

/**
 * This class handle the security methods of this application
 *
 * @author ychartois
 */
public class Secured extends Security.Authenticator {

    /**
     * get the user name from the session
     *
     * @return the user name (for us it's the email adress)
     */
	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

    /**
     * method call when no username is in session
     *
     * @return the unauthorized HTTP code
     */
	@Override
	public Result onUnauthorized(Context ctx) {
		return unauthorized();
	}

    /**
     * Test if the user is a member of the specified role
     *
     * @param role the application role
     *
     * @return true if the user is a member of the specified role
     */
    public static boolean isMemberOf(Roles role) {
        User user = getUser();
        return user.role == role;
    }

	/**
	 * <p>
	 * Get the connected user
	 * </p>
	 * 
	 * @return the connected user
	 */
    public static User getUser() {
        String 	userId 	= 	Context.current().session().get("email");

        // We get the user from cache if it exists
        User 	user 	= 	(User) Cache.get( Application.getUuid() + "connectedUser" );

        // Otherwise, we get it from the database
        if (user == null) {
            user = User.find.where().eq("email", userId).findUnique();
            Cache.set( userId, user );
        }

        return user;
    }

}