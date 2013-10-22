package models.utils.compare;


import com.avaje.ebean.Ebean;
import models.Workshop;
import models.WorkshopSession;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import play.libs.Yaml;
import utils.BaseModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/3/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkshopsPlayedComparatorTest extends BaseModel {

    private Workshop ws1;
    private Workshop ws2;
    private Workshop ws3;

    @Before
    public void initData() {
        //We retrieve the workshops
        ws1 = Workshop.find.byId(1l);
        ws2 = Workshop.find.byId(2l);
        ws3 = Workshop.find.byId(3l);
    }

    @Test
    public void compareGreater() {
        int res = new WorkshopsPlayedComparator().compare(ws1, ws2);
        Assertions.assertThat(res).isPositive();
    }

    @Test
    public void compareLess() {
        int res = new WorkshopsPlayedComparator().compare(ws2, ws1);
        Assertions.assertThat(res).isNegative();
    }

    @Test
    public void compare_with_two_session() {
        int res1 = new WorkshopsPlayedComparator().compare(ws1, ws3);
        Assertions.assertThat(res1).isPositive();

        int res2 = new WorkshopsPlayedComparator().compare(ws2, ws3);
        Assertions.assertThat(res2).isPositive();
    }

}
