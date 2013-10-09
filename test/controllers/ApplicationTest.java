package controllers;

import org.junit.Test;
import play.mvc.Http;
import play.mvc.Result;
import utils.BaseModel;

import static org.fest.assertions.api.Assertions.assertThat;
import static play.test.Helpers.*;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/9/13
 * Time: 3:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class ApplicationTest extends BaseModel{

    @Test
    public void testWelcome() throws Exception {
        Result result = route(fakeRequest(GET, "/"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
    }

    @Test
    public void testWorkshops() throws Exception {
        Result result = route(fakeRequest(GET, "/workshops"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
    }

    @Test
    public void testNewWorkshops() throws Exception {
        Result result = route(fakeRequest(GET, "/newWorkshops"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
    }
}
