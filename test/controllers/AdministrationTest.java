package controllers;

import models.Roles;
import models.User;
import org.junit.Test;
import play.mvc.Result;
import utils.BaseModel;

import java.util.HashMap;
import java.util.Map;

import static org.fest.assertions.api.Assertions.assertThat;
import static play.mvc.Http.Status.FORBIDDEN;
import static play.test.Helpers.*;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 11/12/13
 * Time: 10:46 AM
 * To change this template use File | Settings | File Templates.
 */
public class AdministrationTest extends BaseModel {
    @Test
    public void testAdminUsers_as_admin() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/admin/users/0")
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testAdminUsers_as_standard() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/admin/users/0")
                .withSession("uuid", UUID)
                .withSession("email", "marie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testUpdateRole_as_admin() throws Exception {
        // Prepare data
        Map<String, String> form = new HashMap<>();
        form.put("user", "marie.dupont@test.com");
        form.put("role", "ADMIN");

        // Test before action
        User user = User.find.where().eq("email", "marie.dupont@test.com").findUnique();
        assertThat( user.role ).isEqualTo( Roles.STANDARD );

        // the action
        Result result = route(fakeRequest(PUT, "/ws/admin/userRole")
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com")
                .withFormUrlEncodedBody(form));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
        User modifiedUser = User.find.where().eq("email", "marie.dupont@test.com").findUnique();
        assertThat( modifiedUser.role ).isEqualTo( Roles.ADMIN );

        // Cleaning
        modifiedUser.role = Roles.STANDARD;
        modifiedUser.save();
    }

    @Test
    public void testUpdateRole_as_standard() throws Exception {
        // Prepare data
        Map<String, String> form = new HashMap<>();
        form.put("user", "marie.dupont@test.com");
        form.put("role", "ADMIN");

        // the action
        Result result = route(fakeRequest(PUT, "/ws/admin/userRole")
                .withSession("uuid", UUID)
                .withSession("email", "marie.dupont@test.com")
                .withFormUrlEncodedBody(form));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }
}
