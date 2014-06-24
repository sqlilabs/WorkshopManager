package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import models.User;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.cache.Cache;
import play.libs.Crypto;
import play.mvc.Controller;
import play.mvc.Result;
import services.Google;
import views.html.welcome.charter;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

/**
 * This controller got the action that allow the user to authenticate
 * 
 * @author ychartois
 */
public class AuthenticationController extends Controller {

    private static Google provider = new Google();
	
	//<--------------------------------------------------------------------------->
	//-							 Actions(s)	        
	//<--------------------------------------------------------------------------->	
    /**
     * this method prepare the OpenID call
     *
     * @return redirect on the verify service
     */
    public static Result auth() throws MalformedURLException, URISyntaxException {

        // Generate a token
        String token = generateToken();

        // Get the endpoint of the provider
        provider.getEndpoint().get(Application.TIMEOUT_WS);

        return redirect( provider.authenticationUrl(token) );
    }

    /**
     * this method verify the userInfo,
     *
     * @return @return to welcome page or the charter agreement page
     */
    public static Result verify() {

        // We get the OpenID info of the user
        // We retrieve the user information
        JsonNode tokenInfo = provider.getTokenInfo( request() ).get( Application.TIMEOUT_WS );
        String token = provider.getToken( tokenInfo );

        JsonNode userInfo = provider.getUserInfo( tokenInfo ).get(Application.TIMEOUT_WS);

        // If the user is not from the specified domain he can't connect
        if ( !StringUtils.endsWith( provider.getEmail(userInfo), Play.application().configuration().getString("mail.filter")) ) {
            return forbidden();
        }

        // We add the authenticated user to the session
        session().put("email", provider.getEmail(userInfo));

        // We check if it's an existing user in base
        User user = Secured.getUser();

        // if not we create it
        if ( user == null ) {
            user = provider.getUser(userInfo);
            user.charterAgree = false;
            user.picture = "/assets/images/avatar-default.png";
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
     * Allow to quit the app
     *
     * @return the HHTP code OK
     */
    public static Result logout() {
        // We clear the session
        session().clear();

        return redirect(routes.Application.welcome());
    }

    /**
     * Method that generate a token
     *
     * @return a unique token
     */
    public static String generateToken() {
        String uuid = java.util.UUID.randomUUID().toString();
        return Crypto.sign(uuid);
    }

}
