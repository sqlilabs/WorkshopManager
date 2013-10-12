package repository;

import models.User;
import org.fest.assertions.Assertions;
import org.junit.Test;
import utils.BaseModel;

import java.util.List;

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

        Assertions.assertThat(list.size()).isEqualTo(4);
    }

    /**
     * We check that it's well sorted
     */
    @Test
    public void testGetSpeakers_is_well_sorted() {
        List<String> list = UserRepository.getSpeakers();

        Assertions.assertThat(list.get(0)).isEqualTo("alain dupont");
        Assertions.assertThat(list.get(1)).isEqualTo("delete dupont");
        Assertions.assertThat(list.get(2)).isEqualTo("greg dupont");
        Assertions.assertThat(list.get(3)).isEqualTo("sylvie dupont");
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
