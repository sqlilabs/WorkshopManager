package controllers;

import org.junit.Test;
import play.mvc.Result;
import utils.BaseModel;

import static org.fest.assertions.api.Assertions.assertThat;
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
    @Test
    public void testAuth() throws Exception {
        Result result = route(fakeRequest(GET, "/login/auth"));
        assertThat(status(result)).isEqualTo(SEE_OTHER);
    }

    @Test
    public void testLogout() throws Exception {
        Result result = route(fakeRequest(GET, "/logout").withSession("email", "test@test.com"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat( session(result).get("email") ).isNullOrEmpty();
    }
}
