package models.utils.helpers;

import models.Comment;
import models.User;
import models.Workshop;
import org.fest.assertions.Assertions;
import org.junit.Test;
import utils.BaseModel;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/31/13
 * Time: 4:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class UsersUtilsTest extends BaseModel {

    @Test
    public void testIsSessionSpeaker_true() throws Exception {
        // Fake the session
        fakeSession();

        // Prepare data
        User connectedUser = User.find.where().eq("email", "greg.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Workshop workshop =  Workshop.find.byId(2l);

        // the action
        boolean result = UsersUtils.isSessionSpeaker(workshop.workshopSession.get(0));

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
        boolean result = UsersUtils.isSessionSpeaker(workshop.workshopSession.get(0));

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
        boolean result = UsersUtils.isWorkshopAuthor(workshop);

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
        boolean result = UsersUtils.isWorkshopAuthor(workshop);

        // test after action
        Assertions.assertThat(result).isFalse();
    }

    @Test
    public void testIsCommentAuthor_true() throws Exception {
        // Fake the session
        fakeSession();

        // Prepare data
        User connectedUser = User.find.where().eq("email", "greg.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Comment comment =  Comment.find.byId(1l);

        // the action
        boolean result = UsersUtils.isCommentAuthor(comment);

        // test after action
        Assertions.assertThat(result).isTrue();
    }

    @Test
    public void testIsCommentAuthor_false() throws Exception {
        // Fake the session
        fakeSession();

        // Prepare data
        User connectedUser = User.find.where().eq("email", "sylvie.dupont@test.com").findUnique();
        cacheConnectedUser( connectedUser );
        Comment comment =  Comment.find.byId(1l);

        // the action
        boolean result = UsersUtils.isCommentAuthor(comment);

        // test after action
        Assertions.assertThat(result).isFalse();
    }
}
