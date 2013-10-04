package repository;

import com.avaje.ebean.Ebean;
import models.Workshop;
import models.WorkshopSession;
import org.fest.assertions.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import play.test.FakeApplication;
import play.test.Helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

public class WorkshopRepositoryTest  {

	public static FakeApplication app;
	 
	  @BeforeClass
	  public static void startApp() {
	    app = Helpers.fakeApplication( Helpers.inMemoryDatabase() );
	    Helpers.start(app);
	    
	    // Workshop without session
        Workshop			wsWithoutSession 	= 	new Workshop();
		wsWithoutSession.subject 				= 	"With no session";
		Ebean.save(wsWithoutSession);
		
		// Workshop with session
		Workshop 			wsWithSession		= 	new Workshop();
		wsWithSession.subject 					= 	"With session";
		WorkshopSession 	session0			=	new WorkshopSession();
		wsWithSession.workshopSession.add( session0 );
		Ebean.save(wsWithSession);
		
		// Workshop with session but not planned
        Workshop			wsWithoutDate 		= 	new Workshop();
        wsWithoutDate.subject 					= 	"With no date";
        WorkshopSession 	session				=	new WorkshopSession();
        wsWithoutDate.workshopSession.add( session );
		Ebean.save(wsWithoutDate);
		
		// Workshop with session and planned in the future
        Workshop			wsWithDate 			= 	new Workshop();
        wsWithDate.subject 						= 	"With date";
        WorkshopSession 	session2			=	new WorkshopSession();
        try {
			session2.nextPlay						=	new SimpleDateFormat("dd-MM-yyyy").parse("07-12-5872");
		} catch (ParseException e) {

		}
        wsWithDate.workshopSession.add(session2);
		Ebean.save(wsWithDate);
		
		// Workshop with session and planned in the past
        Workshop			wsWithOldDate 		= 	new Workshop();
        wsWithOldDate.subject 					= 	"With old date";
        WorkshopSession 	session3			=	new WorkshopSession();
        try {
			session3.nextPlay						=	new SimpleDateFormat("dd-MM-yyyy").parse("07-12-1980");
		} catch (ParseException e) {

		}
        wsWithOldDate.workshopSession.add(session3);
		Ebean.save(wsWithOldDate);
	  }
	 
	  @AfterClass
	  public static void stopApp() {
	    Helpers.stop(app);
	  }
   
    /**
     * Test of getWorkshops method, of class WorkshopRepository.
     */
    @Test
    public void testGetWorkshops() {
		List<Workshop> list = WorkshopRepository.getWorkshops();
		
		// We check that there is only one Workshop
		Assertions.assertThat(list.size()).isEqualTo(1);
		// And we chack that it's the good one
		Assertions.assertThat(list.get(0).subject).isEqualTo("With no session");
    }

    /**
     * Test of getWorkshopsPlanifie method, of class WorkshopRepository.
     * @throws ParseException 
     */
    @Test
    public void testGetWorkshopsPlanifie() throws ParseException {
		List<WorkshopSession> list = WorkshopRepository.getWorkshopsPlanifie();

        // We check that there is only one Workshop
		Assertions.assertThat(list.size()).isEqualTo(1);
        // And we chack that it's the good one
		Assertions.assertThat(list.get(0).workshop.subject).isEqualTo("With date");
    }

    /**
     * Test of getWorkshopsAlreadyPlayed method, of class WorkshopRepository.
     * @throws ParseException 
     */
    @Test
    public void testGetWorkshopsAlreadyPlayed() throws ParseException {
		
		List<Workshop> list = WorkshopRepository.getWorkshopsAlreadyPlayed();

        // We check that there is only one Workshop
		Assertions.assertThat(list.size()).isEqualTo(1);
        // And we chack that it's the good one
		Assertions.assertThat(list.get(0).subject).isEqualTo("With old date");
    }



}
