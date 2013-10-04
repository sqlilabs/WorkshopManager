package models.utils.compare;


import com.avaje.ebean.Ebean;
import models.Workshop;
import models.WorkshopSession;
import org.fest.assertions.Assertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/3/13
 * Time: 7:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkshopsPlayedComparatorTest {

    private static Workshop ws1;
    private static Workshop ws2;
    private static Workshop ws3;

    @BeforeClass
    public static void initData() {

        ws1 			                        = 	new Workshop();
        WorkshopSession session1			    =	new WorkshopSession();
        try {
            session1.nextPlay					=	new SimpleDateFormat("dd-MM-yyyy").parse("07-12-2013");
        } catch (ParseException e) {

        }
        ws1.workshopSession.add(session1);

        ws2 		                            = 	new Workshop();
        WorkshopSession 	session2			=	new WorkshopSession();
        try {
            session2.nextPlay					=	new SimpleDateFormat("dd-MM-yyyy").parse("08-12-2013");
        } catch (ParseException e) {

        }
        ws2.workshopSession.add(session2);

        ws3 		                            = 	new Workshop();
        WorkshopSession 	session3			=	new WorkshopSession();
        try {
            session3.nextPlay					=	new SimpleDateFormat("dd-MM-yyyy").parse("08-12-2013");
        } catch (ParseException e) {

        }
        WorkshopSession 	session4			=	new WorkshopSession();
        try {
            session4.nextPlay					=	new SimpleDateFormat("dd-MM-yyyy").parse("07-12-2013");
        } catch (ParseException e) {

        }
        ws3.workshopSession.add(session3);
        ws3.workshopSession.add(session4);
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
        Assertions.assertThat(res2).isEqualTo(0);
    }

}
