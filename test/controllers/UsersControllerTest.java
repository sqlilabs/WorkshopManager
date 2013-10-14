package controllers;

import com.fasterxml.jackson.databind.JsonNode;

import models.Roles;
import models.User;
import models.Workshop;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import play.api.mvc.RequestHeader;
import play.cache.Cache;
import play.libs.Json;
import play.mvc.Call;
import play.mvc.Http;
import play.mvc.Http.Context;
import play.mvc.Result;
import utils.BaseModel;

import java.util.HashMap;
import java.util.Map;

import static play.test.Helpers.*;
import static org.mockito.Mockito.mock;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/10/13
 * Time: 4:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsersControllerTest extends BaseModel {

    public User user;

    @Before
    public void prepareData() {
        user = new User();
        user.firstName= "test";
        user.lastName = "test";
        user.charterAgree = false;
        user.email = "test@test.com";
        user.picture = "path/test.jpg";
        user.role = Roles.ADMIN;
    }

    @Test
    public void testCreateNewUser() throws Exception {
        // Prepare data
        cacheNewUser(user);

        // the action
        Result result = route(fakeRequest(GET, "/login/createUser").withSession("uuid", UUID));

        // test after action
        Assertions.assertThat(status(result)).isEqualTo(SEE_OTHER);
        User userSaved = User.find.where().eq("email", "test@test.com").findUnique();
        Assertions.assertThat( userSaved.charterAgree ).isTrue();
        Assertions.assertThat( userSaved.role).isEqualTo( Roles.valueOf(Application.conf("default.role")) );

        // Clean data
        userSaved.delete();
    }

    @Test
    public void testModifyUserPicture() throws Exception {
        // Prepare data
        user.save();
        cacheConnectedUser(user);
        Map<String, String> form = new HashMap<>();
        form.put("image", "newPath/newPicture.gif");

        // the action
        Result result = route(fakeRequest(PUT, "/ws/modifyUserPicture")
                .withSession("uuid", UUID)
                .withSession("email", user.email)
                .withFormUrlEncodedBody(form));

        // test after action
        Assertions.assertThat(status(result)).isEqualTo(OK);
        User userSaved = User.find.where().eq("email", "test@test.com").findUnique();
        Assertions.assertThat( userSaved.picture ).isEqualTo("newPath/newPicture.gif");
        Assertions.assertThat( contentType(result) ).isEqualTo( "application/json" );

        // Clean data
        userSaved.delete();
    }

    @Test
    public void testModifyUserPicture_notIdentified() throws Exception {
        // the action
        Result result = route(fakeRequest(PUT, "/ws/modifyUserPicture"));

        // test after action
        Assertions.assertThat(status(result)).isEqualTo(UNAUTHORIZED);
    }

    @Test
    public void testIsSessionSpeaker_true() throws Exception {
        // Fake the session
    	fakeSession();
    	
        // Prepare data
        User connectedUser = User.find.where().eq("email", "greg.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Workshop workshop =  Workshop.find.byId(2l);

        // the action
        boolean result = UsersController.isSessionSpeaker( workshop.workshopSession.get(0) );

        // test after action
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testIsSessionSpeaker_false() throws Exception {
        // Fake the session
        fakeSession();

        // Prepare data
        User connectedUser = User.find.where().eq("email", "sylvie.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Workshop workshop =  Workshop.find.byId(2l);

        // the action
        boolean result = UsersController.isSessionSpeaker( workshop.workshopSession.get(0) );

        // test after action
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsAuthor_true() throws Exception {
        // Fake the session
        fakeSession();

        // Prepare data
        User connectedUser = User.find.where().eq("email", "sylvie.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Workshop workshop =  Workshop.find.byId(3l);

        // the action
        boolean result = UsersController.isAuthor( workshop );

        // test after action
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testIsAuthor_false() throws Exception {
        // Fake the session
        fakeSession();

        // Prepare data
        User connectedUser = User.find.where().eq("email", "greg.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Workshop workshop =  Workshop.find.byId(3l);

        // the action
        boolean result = UsersController.isAuthor( workshop );

        // test after action
        Assertions.assertThat(result).isFalse();
    }
}
