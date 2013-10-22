package views.helpers;

import models.User;
import org.junit.Test;
import play.i18n.Messages;
import utils.BaseModel;

import java.util.HashSet;
import java.util.Set;

import static org.fest.assertions.api.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/22/13
 * Time: 11:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class ViewsHelperTest extends BaseModel {

    @Test
    public void testGetForeseenSpeaker_noSpeaker() throws Exception {
        // the action
        String result = ViewsHelper.getForeseenSpeaker(new HashSet<User>());

        // after action
        assertThat(result).isEmpty();
    }

    @Test
    public void testGetForeseenSpeaker_oneSpeaker() throws Exception {
        // prepare data
        Set<User> users = new HashSet<>();
        users.add( User.find.where().eq("email", "marie.dupont@test.com").findUnique() );

        // the action
        String result = ViewsHelper.getForeseenSpeaker(users);

        // after action
        assertThat(result).isEqualTo("marie dupont");
    }

    @Test
    public void testGetForeseenSpeaker_twoSpeaker() throws Exception {
        // prepare data
        Set<User> users = new HashSet<>();
        users.add( User.find.where().eq("email", "marie.dupont@test.com").findUnique() );
        users.add( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );

        // the action
        String result = ViewsHelper.getForeseenSpeaker(users);

        // after action
        assertThat(result).isEqualTo("marie dupont " + Messages.get("and.or") + " sylvie dupont");
    }
}
