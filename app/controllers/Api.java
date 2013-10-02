package controllers;

import java.util.ArrayList;
import java.util.List;

import models.User;
import models.WorkshopSession;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.node.ObjectNode;

import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import repository.WorkshopRepository;
import services.UserService;

import com.avaje.ebean.Ebean;

import controllers.data.SessionData;

public class Api extends Controller {

	// <--------------------------------------------------------------------------->
	// - Actions Methods
	// <--------------------------------------------------------------------------->
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
		// conversion des sessions en données à sérialiser en json
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
		String accessToken = json.findPath("accessToken").getTextValue();

		// get user info
//		JsonNode oAuth = new UserService().getUserInfo(accessToken);
//
//		String username = oAuth.findPath("email").getTextValue();

		Long workshopSessionId = json.findPath("workshopSessionId")
				.getLongValue();
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
