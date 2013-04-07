package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import models.Workshop;
import models.WorkshopSession;

import org.fest.assertions.Assertions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import play.test.FakeApplication;
import play.test.Helpers;

import com.avaje.ebean.Ebean;

import utils.BaseModel;

public class WorkshopDAOTest extends BaseModel {

	public static FakeApplication app;
	 
	  @BeforeClass
	  public static void startApp() {
	    app = Helpers.fakeApplication( Helpers.inMemoryDatabase() );
	    Helpers.start(app);
	    
	    // Un workshop sans session
        Workshop			wsWithoutSession 	= 	new Workshop();
		wsWithoutSession.subject 				= 	"With no session";
		Ebean.save(wsWithoutSession);
		
		// Un Workshop avec session
		Workshop 			wsWithSession		= 	new Workshop();
		wsWithSession.subject 					= 	"With session";
		WorkshopSession 	session0				=	new WorkshopSession();
		wsWithSession.workshopSession 			= 	session0;
		Ebean.save(session0);
		Ebean.save(wsWithSession);
		
		// Un workshop sans date next Play
        Workshop			wsWithoutDate 		= 	new Workshop();
        wsWithoutDate.subject 					= 	"With no date";
        WorkshopSession 	session				=	new WorkshopSession();
        wsWithoutDate.workshopSession 			= 	session;
		Ebean.save(session);
		Ebean.save(wsWithoutDate);
		
		// Un workshop avec date next Play
        Workshop			wsWithDate 			= 	new Workshop();
        wsWithDate.subject 						= 	"With date";
        WorkshopSession 	session2			=	new WorkshopSession();
        try {
			session2.nextPlay						=	new SimpleDateFormat("dd-MM-yyyy").parse("07-12-5872");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        wsWithDate.workshopSession 				= 	session2;
		Ebean.save(session2);
		Ebean.save(wsWithDate);
		
		// Un workshop avec date passée next Play
        Workshop			wsWithOldDate 		= 	new Workshop();
        wsWithOldDate.subject 					= 	"With old date";
        WorkshopSession 	session3			=	new WorkshopSession();
        try {
			session3.nextPlay						=	new SimpleDateFormat("dd-MM-yyyy").parse("07-12-1980");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        wsWithOldDate.workshopSession 			= 	session3;
		Ebean.save(session3);
		Ebean.save(wsWithOldDate);
	  }
	 
	  @AfterClass
	  public static void stopApp() {
	    Helpers.stop(app);
	  }
   
    /**
     * Test of getWorkshops method, of class WorkshopDAO.
     */
    @Test
    public void testGetWorkshops() {
		List<Workshop> list = WorkshopDAO.getWorkshops();
		
		// On vérifie qu'il y a bien un seul workshop
		Assertions.assertThat(list.size()).isEqualTo(1);
		// On vérifie bien que c'est celui sans session
		Assertions.assertThat(list.get(0).subject).isEqualTo("With no session");
    }

    /**
     * Test of getWorkshopsPlanifie method, of class WorkshopDAO.
     * @throws ParseException 
     */
    @Test
    public void testGetWorkshopsPlanifie() throws ParseException {
		List<Workshop> list = WorkshopDAO.getWorkshopsPlanifie();
		
		// On vérifie qu'il y a bien un seul workshop
		Assertions.assertThat(list.size()).isEqualTo(1);
		// On vérifie bien que c'est celui avec une date
		Assertions.assertThat(list.get(0).subject).isEqualTo("With date");
    }

    /**
     * Test of getWorkshopsAlreadyPlayed method, of class WorkshopDAO.
     * @throws ParseException 
     */
    @Test
    public void testGetWorkshopsAlreadyPlayed() throws ParseException {
		
		List<Workshop> list = WorkshopDAO.getWorkshopsAlreadyPlayed();
		
		// On vérifie qu'il y a bien un seul workshop
		Assertions.assertThat(list.size()).isEqualTo(1);
		// On vérifie bien que c'est celui avec une date
		Assertions.assertThat(list.get(0).subject).isEqualTo("With old date");
    }



}
