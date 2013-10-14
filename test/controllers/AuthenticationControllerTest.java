package controllers;

import models.Roles;
import models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import play.Configuration;
import play.api.Play;
import play.cache.Cache;
import play.libs.F;
import play.libs.OpenID;
import play.mvc.Http;
import play.mvc.Result;
import play.test.FakeApplication;
import services.UserService;
import utils.BaseModel;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/9/13
 * Time: 4:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationControllerTest extends BaseModel {

    private User user;
    private OpenID.UserInfo userInfo;

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

    public void mockVerify() {
        Map<String, String> attributes = new HashMap<>();
        attributes.put("Email", user.email);
        attributes.put("FirstName", user.firstName);
        attributes.put("LastName", user.lastName);
        attributes.put("Image", null);

        userInfo = new OpenID.UserInfo("test", attributes);
    }

    @Test
    public void testAuth() throws Exception {
        F.Promise<String> redirectUrl = mock( F.Promise.class);
        when( redirectUrl.get() ).thenReturn("/verify");

        Result result = route(fakeRequest(GET, "/login/auth"));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }

    @Test
    public void testVerify_new_user_not_agreed() {
        //Prepare data
        mockVerify();

        //fake the session
        fakeSession();
        Http.Context.current().session().put("email", user.email);

        // call the action
        Result result = AuthenticationController.verify( userInfo );

        // Tests
        assertThat(status(result)).isEqualTo(OK);
        assertThat( Cache.get( UUID + "newUser" ) ).isNotNull();
    }

    @Test
    public void testVerify_new_user_agreed() {
        //Prepare data
        mockVerify();
        user.charterAgree = true;
        Cache.set( UUID + "connectedUser", user );

        //fake the session
        fakeSession();
        Http.Context.current().session().put("email", user.email);

        // call the action
        Result result = AuthenticationController.verify( userInfo );

        // Tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        User userSaved = User.find.where().eq("email", user.email).findUnique();
        assertThat( userSaved ).isNotNull();
    }

    @Test
    public void testVerify_new_user_not_with_good_domain() {
        // Prepare data
        mockVerify();

        // We have to modify the app conf (override the default application.conf)
        Map<String, String> conf = new HashMap<>();
        conf.putAll(inMemoryDatabase());
        conf.put("mail.filter", "@domain.com"); // the key we want to override

        start( fakeApplication( conf ) );


        //fake the session
        fakeSession();
        Http.Context.current().session().put("email", user.email);

        // call the action
        Result result = AuthenticationController.verify( userInfo );

        // Tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testLogout() throws Exception {
        Result result = route(fakeRequest(GET, "/logout").withSession("email", user.email));
        assertThat(status(result)).isEqualTo(OK);
        assertThat( session(result).get("email") ).isNullOrEmpty();
    }
}
