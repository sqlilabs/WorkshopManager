package controllers;

import models.User;
import models.Workshop;
import models.WorkshopSession;
import org.junit.Before;
import org.junit.Test;
import play.mvc.Result;
import utils.BaseModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

    Workshop workshop;
    WorkshopSession workshopSession;

    @Before
    public void setUp() throws Exception {
        workshop = new Workshop();
        workshop.subject = "a unique test subject";
        workshop.summary = "a summary";
        workshop.description = "a description";
        workshop.image = "/path/newImage.jpg";

        workshopSession = new WorkshopSession();
        workshopSession.limitePlace = 10;
        workshopSession.location = "somewhere nice !";
        workshopSession.speaker = User.find.where().eq("email","greg.dupont@test.com").findUnique();
        workshopSession.nextPlay = new Date();
    }

    @Test
    public void testBlankWorkshop() throws Exception {
        Result result = route(fakeRequest(GET, "/workshops/add"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
    }

    @Test
    public void testSaveWorkshop_newWorkshop() throws Exception {

        // Prepare datas
        Map<String, String> form = new HashMap<>();
        form.put("subject", workshop.subject);
        form.put("summary", workshop.summary);
        form.put("description", workshop.description);
        form.put("image", workshop.image);

        // the action
        Result result = callAction(controllers.routes.ref.WorkshopController.saveWorkshop(-1l), fakeRequest(POST, "/workshops/add")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);

        Workshop newWorkshop = Workshop.find.where().eq("subject", workshop.subject).findUnique();
        assertThat(newWorkshop).isNotNull();
        assertThat(newWorkshop.author.email).isEqualTo("sylvie.dupont@test.com");
        assertThat(newWorkshop.creationDate).isNotNull();
        assertThat(newWorkshop.image).endsWith("default.png");

        //cleaning
        newWorkshop.delete();
    }

    @Test
    public void testSaveWorkshop_modifyWorkshop() throws Exception {
        // Prepare datas
        Map<String, String> form = new HashMap<>();
        form.put("subject", "a new unique test subject");
        form.put("summary", "a new summary");
        form.put("description", "a new description");
        form.put("image", "/newPath/newImage.jpg");

        workshop.save();

        // the action
        Result result = callAction(controllers.routes.ref.WorkshopController.saveWorkshop(workshop.id), fakeRequest(POST, "/workshops/add")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);

        Workshop newWorkshop = Workshop.find.byId(workshop.id);
        assertThat(newWorkshop.subject).isEqualTo("a new unique test subject");

        //cleaning
        newWorkshop.delete();
    }

    @Test
    public void testModifyWorkshop_as_admin() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/modifier/1")
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testModifyWorkshop_as_author() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/modifier/1")
                .withSession("uuid", UUID)
                .withSession("email", "greg.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testModifyWorkshop_as_standard() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/modifier/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testDeleteWorkshop_as_admin() throws Exception {
        // Prepare datas
        workshop.save();

        // the action
        Result result = route(fakeRequest(GET, "/workshops/supprimer/" + workshop.id)
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);

        Workshop newWorkshop = Workshop.find.byId(workshop.id);
        assertThat(newWorkshop).isNull();
    }

    @Test
    public void testDeleteWorkshop_as_author() throws Exception {
        // Prepare datas
        workshop.author = User.find.where().eq("email","greg.dupont@test.com").findUnique();
        workshop.save();

        // the action
        Result result = route(fakeRequest(GET, "/workshops/supprimer/" + workshop.id)
                .withSession("uuid", UUID)
                .withSession("email", "greg.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);

        Workshop newWorkshop = Workshop.find.byId(workshop.id);
        assertThat(newWorkshop).isNull();
    }

    @Test
    public void testDeleteWorkshop_as_standard() throws Exception {
        // Prepare datas
        workshop.author = User.find.where().eq("email","greg.dupont@test.com").findUnique();
        workshop.save();

        // the action
        Result result = route(fakeRequest(GET, "/workshops/supprimer/" + workshop.id)
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);

        Workshop newWorkshop = Workshop.find.byId(workshop.id);
        assertThat(newWorkshop).isNotNull();

        //Cleaning
        workshop.delete();
    }

    @Test
    public void testPlanWorkshop_as_admin() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/planifier/1")
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testPlanWorkshop_as_author() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/planifier/1")
                .withSession("uuid", UUID)
                .withSession("email", "greg.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testPlanWorkshop_as_standard() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/planifier/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testModifyPlannedWorkshop_as_admin() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/modif/session/1/1")
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testModifyPlannedWorkshop_as_speaker() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/modif/session/1/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testModifyPlannedWorkshop_as_standard() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshops/modif/session/1/1")
                .withSession("uuid", UUID)
                .withSession("email", "greg.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testSaveWorkshopSession_new_session_as_admin() throws Exception {
        // Prepare datas
        workshop.save();

        Map<String, String> form = new HashMap<>();
        form.put("location", workshopSession.location);
        form.put("limitePlace", Integer.toString(workshopSession.limitePlace));
        form.put("nextPlay", "2013-10-16");
        form.put("Speaker", workshopSession.speaker.firstName + " " + workshopSession.speaker.lastName);

        // before action
        assertThat(workshop.workshopSession.size()).isEqualTo(0);

        // the action
        Result result = route(fakeRequest(POST, "/workshops/planifier/" + workshop.id + "/-1")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);

        Workshop newWorkshop = Workshop.find.byId( workshop.id );
        assertThat(newWorkshop.workshopSession.size()).isEqualTo(1);
        assertThat(newWorkshop.workshopSession.get(0).location).isEqualTo(workshopSession.location);
        assertThat(newWorkshop.workshopSession.get(0).speaker.firstName).isEqualTo(workshopSession.speaker.firstName);

        //cleaning
        newWorkshop.workshopSession.get(0).delete();
        newWorkshop.delete();
    }

    @Test
    public void testSaveWorkshopSession_modify_session_as_admin() throws Exception {
        // Prepare datas
        workshop.workshopSession.add(workshopSession);
        workshopSession.save();
        workshop.save();

        Map<String, String> form = new HashMap<>();
        form.put("location", "a new really great location");
        form.put("limitePlace", Integer.toString(workshopSession.limitePlace));
        form.put("nextPlay", "2013-10-16");
        form.put("Speaker", workshopSession.speaker.firstName + " " + workshopSession.speaker.lastName);

        // before action
        assertThat(workshop.workshopSession.size()).isEqualTo(1);

        // the action
        Result result = route(fakeRequest(POST, "/workshops/planifier/" + workshop.id + "/" + workshopSession.id)
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(SEE_OTHER);

        Workshop newWorkshop = Workshop.find.byId( workshop.id );
        assertThat(newWorkshop.workshopSession.size()).isEqualTo(1);
        assertThat(newWorkshop.workshopSession.get(0).location).isEqualTo("a new really great location");

        //cleaning
        newWorkshop.delete();
    }

    @Test
    public void testSaveWorkshopSession_as_speaker() throws Exception {
        // the action
        Result result = route(fakeRequest(POST, "/workshops/planifier/1/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testSaveWorkshopSession_as_standard() throws Exception {
        // the action
        Result result = route(fakeRequest(POST, "/workshops/planifier/1/1")
                .withSession("uuid", UUID)
                .withSession("email", "greg.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
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
