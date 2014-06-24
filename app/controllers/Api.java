package controllers;

import com.avaje.ebean.Ebean;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import models.User;
import models.WorkshopSession;
import models.apidatas.SessionData;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.cache.Cache;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.F;
import play.libs.Json;
import play.libs.OpenID;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import repository.WorkshopRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * The API provided for user who want to build other apps (like Android, ...)
 */
public class Api extends Controller {

	// <--------------------------------------------------------------------------->
	// - Actions Methods
	// <--------------------------------------------------------------------------->
    /**
     * this method prepare the OpenID call
     *
     * @return redirect on the verify service
     */
    public static Result auth() {

        // url are defined in Application.conf
        String providerUrl = Play.application().configuration().getString("openID.provider.url");
        String returnToUrl = "http://localhost:9000/api/login/verify"; // Specific API

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
     *
     * //TODO: POC !
     */
    public static Result verify( OpenID.UserInfo userInfo ) {
        // If the user is not from the specified domain he can't connect
        if ( !StringUtils.endsWith(userInfo.attributes.get("Email"), Play.application().configuration().getString("mail.filter")) ) {
            return forbidden();
        }

        // Init result
        ObjectNode result = Json.newObject();

        // We add the authenticated user to the session
        session().put("email", userInfo.attributes.get("Email"));  // I assume it will work?

        // We check if it's an existing user in base
        User user = Secured.getUser(); // based on the email in the session, and we just put one in it ;)

        if ( user == null ) {
            result.put("error","You can't create a new user with your Android App, you must login on the website first");
            return forbidden(result);
        }

        result.put("token", Application.getUuid()); // You get a token (an easy one to start :p), and all your android request must
                                                    // send this token, and all your service must test if the token is ok

        return ok(result);
    }


    /**
	 * This a REST webservice which return the planned workshops list
	 * 
	 * @return Result the http response
	 */
	// @Security.Authenticated(Secured.class)
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result wsWorkshopsPlanifies() {
		List<WorkshopSession> workshopSessionList = WorkshopRepository
				.getWorkshopsPlanifie();
		List<SessionData> wsPlanifies = new ArrayList<SessionData>(
				workshopSessionList.size());
		// we convert the workshopSession in sessionData to export in JSON
		for (WorkshopSession workshopSession : workshopSessionList) {
			wsPlanifies.add(new SessionData(workshopSession));
		}

		return ok(Json.toJson(wsPlanifies));
	}

	/**
	 * This a REST webservice which registers to a session
	 * 
	 * @return Result the http response
	 */
	@Transactional()
	@BodyParser.Of(play.mvc.BodyParser.Json.class)
	public static Result wsRegister() {
		ObjectNode result = Json.newObject();

		JsonNode json = request().body().asJson();
		String accessToken = json.findPath("accessToken").textValue();

		// get user info
//		JsonNode oAuth = new UserService().getUserInfo(accessToken);
//
//		String username = oAuth.findPath("email").getTextValue();

		Long workshopSessionId = json.findPath("workshopSessionId")
				.longValue();
		// We get the Workshop
		WorkshopSession currentSession = WorkshopSession.find
				.byId(workshopSessionId);

		// Get the connected User
		//User user = User.find.where().eq("email", username).findUnique();
        User user = Secured.getUser();

		// It's a Set, so no duplicate
		if (currentSession.limitePlace != 0
				&& currentSession.participants.size() < currentSession.limitePlace
				&& WorkshopController.notInOtherSession(currentSession,
                    user.email)) {
			currentSession.participants.add(user);
		} else {
			result.put("result", "error");
			result.put("message",
					Messages.get("error.participants.limit.reached"));
			return ok(result);
		}

		// We save the new Workshop
		Ebean.save(currentSession);

		result.put("result", "success");
		result.put("message", "");
		result.put("data", Json.toJson(new SessionData(currentSession)));
		return ok(result);
	}
}
