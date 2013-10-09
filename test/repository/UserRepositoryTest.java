package repository;

import com.avaje.ebean.Ebean;
import models.Roles;
import models.User;
import models.Workshop;
import models.WorkshopSession;
import org.fest.assertions.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.specs2.internal.scalaz.Alpha;
import play.libs.Yaml;
import play.test.FakeApplication;
import play.test.Helpers;
import utils.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/9/13
 * Time: 2:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class UserRepositoryTest extends BaseModel {

    /**
     * We check that the firstname is first
     */
    @Test
    public void testGetSpeakers_first_name_first() {
        List<String> list = UserRepository.getSpeakers();

        Assertions.assertThat(list.get(0)).startsWith("alain");
    }

    /**
     * We check that the user without email adress is not in it
     */
    @Test
    public void testGetSpeakers_no_user_without_email() {
        List<String> list = UserRepository.getSpeakers();

        Assertions.assertThat(list.size()).isEqualTo(3);
    }

    /**
     * We check that it's well sorted
     */
    @Test
    public void testGetSpeakers_is_well_sorted() {
        List<String> list = UserRepository.getSpeakers();

        Assertions.assertThat(list.get(0)).isEqualTo("alain dupont");
        Assertions.assertThat(list.get(1)).isEqualTo("greg dupont");
        Assertions.assertThat(list.get(2)).isEqualTo("sylvie dupont");
    }

    /**
     * We that we can retrieve the user from it name
     */
    @Test
    public void testGetUserWithName() {
        User user = UserRepository.getUserWithName("alain dupont");

        Assertions.assertThat(user.firstName).isEqualTo("alain");
        Assertions.assertThat(user.lastName).isEqualTo("dupont");
        Assertions.assertThat(user.email).isEqualTo("alain.dupont@test.com");
    }

}
