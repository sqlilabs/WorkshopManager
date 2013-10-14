package controllers;

import com.avaje.ebean.Ebean;
import models.User;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.cache.Cache;
import play.libs.F;
import play.libs.OpenID;
import play.mvc.Controller;
import play.mvc.Result;
import services.UserService;
import views.html.welcome.charter;

import java.util.HashMap;
import java.util.Map;

/**
 * This controller got the action that allow the user to authenticate
 * 
 * @author ychartois
 */
public class AuthenticationController extends Controller {
	
	//<--------------------------------------------------------------------------->
	//-							 Actions(s)	        
	//<--------------------------------------------------------------------------->	
    /**
     * this method prepare the OpenID call
     *
     * @return redirect on the verify service
     */
    public static Result auth() {

        // url are defined in Application.conf
        String providerUrl = Play.application().configuration().getString("openID.provider.url");
        String returnToUrl = Play.application().configuration().getString("openID.redirect.url");

        // We construct the OpenID map
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Email", "http://schema.openid.net/contact/email");
        attributes.put("FirstName", "http://schema.openid.net/namePerson/first");
        attributes.put("LastName", "http://schema.openid.net/namePerson/last");
        attributes.put("Image", "http://schema.openid.net/media/image/48x48");

        //We call the OpenID provider
        F.Promise<String> redirectUrl = OpenID.redirectURL(providerUrl, returnToUrl, attributes);

        return redirect( redirectUrl.get() );
    }

    /**
     * this method verify the userInfo,
     *
     * @return @return to welcome page or the charter agreement page
     */
    public static Result verify() {
        // We get the OpenID info of the user
        F.Promise<OpenID.UserInfo> userInfoPromise = OpenID.verifiedId();

        return verify( userInfoPromise.get() );
    }


    /**
     *  get the user from the database or cache or create it if it does not exist
     *  this method is outside verify() to simplify tests ( OpenID.verifiedId() is not easy to test)
     *
     * @param userInfo  userInfo given by the provider
     * @return @return to welcome page or the charter agreement page
     */
    public static Result verify( OpenID.UserInfo userInfo ) {
        // If the user is not from the specified domain he can't connect
        if ( !StringUtils.endsWith( userInfo.attributes.get("Email"), Play.application().configuration().getString("mail.filter")) ) {
            return forbidden();
        }

        // We add the authenticated user to the session
        session().put("email", userInfo.attributes.get("Email"));

        // We check if it's an existing user in base
        User user = Secured.getUser();

        // if not we create it
        if ( user == null ) {
            user = new UserService().createNewUser(userInfo);
        }

        if ( user.charterAgree  ) {
            // We save the new instance and save it in cache and redirect to welcome page
            Ebean.save( user );
            Cache.set( Application.getUuid() + "connectedUser", user );

            return redirect(controllers.routes.Application.welcome());
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

        return ok();
    }

}
