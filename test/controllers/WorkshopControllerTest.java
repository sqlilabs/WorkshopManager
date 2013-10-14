package controllers;

import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import utils.BaseModel;

import static org.fest.assertions.api.Assertions.assertThat;
import static play.mvc.Http.Status.OK;
import static play.test.Helpers.*;
import static play.test.Helpers.contentType;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/14/13
 * Time: 4:40 PM
 * To change this template use File | Settings | File Templates.
 */
public class WorkshopControllerTest extends BaseModel {

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testBlankWorkshop() throws Exception {
        Result result = route(fakeRequest(GET, "/workshops/add"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
    }

    @Test
    public void testSaveWorkshop() throws Exception {

    }

    @Test
    public void testModifyWorkshop() throws Exception {

    }

    @Test
    public void testDeleteWorkshop() throws Exception {

    }

    @Test
    public void testPlanWorkshop() throws Exception {

    }

    @Test
    public void testModifyPlannedWorkshop() throws Exception {

    }

    @Test
    public void testSaveWorkshopSession() throws Exception {

    }

    @Test
    public void testAddSpeaker() throws Exception {

    }

    @Test
    public void testRemoveSpeaker() throws Exception {

    }

    @Test
    public void testAddParticipant() throws Exception {

    }

    @Test
    public void testNotInOtherSession() throws Exception {

    }

    @Test
    public void testRemoveParticipant() throws Exception {

    }

    @Test
    public void testAddPotentialParticipant() throws Exception {

    }

    @Test
    public void testRemovePotentialParticipant() throws Exception {

    }

    @Test
    public void testAddInterrestedParticipant() throws Exception {

    }

    @Test
    public void testRemoveInterrestedParticipant() throws Exception {

    }

    @Test
    public void testAddComment() throws Exception {

    }

    @Test
    public void testSaveComment() throws Exception {

    }

    @Test
    public void testAddWorkshopRessources() throws Exception {

    }

    @Test
    public void testSaveWorkshopRessources() throws Exception {

    }

    @Test
    public void testGetWorkshopsPlanifieFromContext() throws Exception {

    }

    @Test
    public void testDecorateDate() throws Exception {

    }

    @Test
    public void testGetWorkshopDescription() throws Exception {

    }

    @Test
    public void testGetFullWorkshopDescription() throws Exception {

    }

    @Test
    public void testGetForseenSpeaker() throws Exception {

    }
}
