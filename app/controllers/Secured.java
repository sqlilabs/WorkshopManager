package controllers;

import models.User;

import org.apache.commons.lang3.StringUtils;

import play.cache.Cache;
import play.mvc.Http.Context;
import play.mvc.Result;
import play.mvc.Security;

// Access rights
public class Secured extends Security.Authenticator {

	@Override
	public String getUsername(Context ctx) {
		return ctx.session().get("email");
	}

	@Override
	public Result onUnauthorized(Context ctx) {
		return unauthorized();
	}

	// Access rights

	public static boolean isMemberOf(String role) {
		User user = getUser();
		return StringUtils.equals(user.role, role);
	}

	/**
	 * <p>
	 * Récupère l'utilisateur connecté.
	 * </p>
	 * 
	 * @return l'utilisateur connecté
	 */
	public static User getUser() {
		User user = (User) Cache.get(Context.current().request().username());
		if (user == null) {
			user = User.find.where()
					.eq("email", Context.current().request().username())
					.findUnique();
			Cache.set(Context.current().request().username(), user);
		}
		return user;
	}

}