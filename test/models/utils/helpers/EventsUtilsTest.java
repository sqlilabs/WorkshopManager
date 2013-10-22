package models.utils.helpers;

import models.Workshop;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;
import utils.BaseModel;

import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/22/13
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventsUtilsTest extends BaseModel {

    private Workshop ws2;
    private Workshop ws3;

    @Before
    public void initData() {
        //We retrieve the workshops
        ws2 = Workshop.find.byId(2l);
        ws3 = Workshop.find.byId(3l);
    }

    @Test
    public void testGetMostRecentSession() throws Exception {
        Assertions.assertThat( EventsUtils.getMostRecentSession(ws3).nextPlay ).isEqualTo( new SimpleDateFormat("yyy-MM-dd HH:mm").parse("2400-12-20 1:00")) ;
    }

    @Test
    public void testGetNextPlannedSession() throws Exception {
        Assertions.assertThat( EventsUtils.getNextPlannedSession(ws3).nextPlay ).isEqualTo( new SimpleDateFormat("yyy-MM-dd HH:mm").parse("2400-11-04 1:00")) ;
    }

    @Test
    public void testGetNextPlannedSession_null() throws Exception {
        Assertions.assertThat( EventsUtils.getNextPlannedSession(ws2) ).isNull();
    }
}
