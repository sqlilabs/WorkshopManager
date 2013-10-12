package models;

import org.fest.assertions.Assertions;
import org.junit.Test;
import utils.BaseModel;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/10/13
 * Time: 6:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserTest extends BaseModel {
    @Test
    public void testSave() throws Exception {
        User user = new User();
        user.firstName= "test";
        user.lastName = "test";
        user.charterAgree = false;
        user.email = "test@test.com";
        user.role = Roles.ADMIN;
        user.save();

        User userSaved = User.find.where().eq("email", "test@test.com").findUnique();

        Assertions.assertThat( userSaved.charterAgree ).isFalse();
        Assertions.assertThat( userSaved.firstName ).isEqualTo( "test" );
    }

    @Test
    public void testDelete() throws Exception {
        // test before action
        User user = User.find.where().eq("email", "delete.dupont@test.com").findUnique();
        Assertions.assertThat( user ).isNotNull();

        // action
        user.delete();

        // test after action
        User userDeleted = User.find.where().eq("email", "delete.dupont@test.com").findUnique();
        Assertions.assertThat( userDeleted ).isNull();
    }
}
