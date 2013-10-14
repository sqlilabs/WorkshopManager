package utils;

import com.avaje.ebean.Ebean;
import models.User;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import play.api.mvc.RequestHeader;
import play.cache.Cache;
import play.libs.Yaml;
import play.mvc.Http;
import play.test.FakeApplication;

import static org.mockito.Mockito.mock;
import static play.test.Helpers.*;

import java.util.HashMap;
import java.util.List;

public class BaseModel {

    protected static final String UUID = "123456";

    /**
     * the fake application, a server based on a in memory database
     */
    public static FakeApplication app;

    @BeforeClass
    public static void startApp() {
        app = fakeApplication(inMemoryDatabase());
        start(app);

        //We load the mock datas
        Ebean.save((List) Yaml.load("test-data.yml"));
    }

    @AfterClass
    public static void stopApp() {
        stop(app);
    }

    /**
     * Allow to create a fake session when we want to test a method from the controller which have no route
     */
    public void fakeSession() {
       Http.Context.current.set(new Http.Context( 1l, mock(RequestHeader.class), mock(Http.Request.class), new HashMap<String, String>(),
               new HashMap <String, String>(), new HashMap <String, Object>()));
       Http.Context.current().session().put("uuid", UUID);
    }

    /**
     * Put the connected user in Cache
     *
     * @param user the connected user
     */
    public void cacheConnectedUser( User user) {
        Cache.set(UUID + "connectedUser", user);
    }

    /**
     * Put the new user in Cache
     *
     * @param user the new user
     */
    public void cacheNewUser( User user) {
        Cache.set(UUID + "newUser", user);
    }
}
