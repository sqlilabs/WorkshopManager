package controllers;

import models.User;
import models.Workshop;
import models.WorkshopSession;
import org.junit.Before;
import org.junit.Test;
import play.i18n.Messages;
import play.mvc.Result;
import utils.BaseModel;
import views.helpers.ViewsHelper;

import java.util.*;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
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
        form.put("nextPlay", "16/10/2013 14:00");
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
        form.put("nextPlay", "16/10/2013 14:00");
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
    public void testAddSpeaker_in_speaker_limit() throws Exception {
        // before action
        assertThat( Workshop.find.byId(1l).speakers.size() ).isEqualTo(1);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/addSpeaker/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        Workshop workshop1 = Workshop.find.byId(1l);
        assertThat( workshop1.speakers.size() ).isEqualTo(2);

        // cleaning
        workshop1.speakers.remove( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop1.save();
    }

    @Test
    public void testAddSpeaker_out_off_speaker_limit() throws Exception {
                // the action
        Result result = route(fakeRequest(GET, "/workshops/addSpeaker/2")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testRemoveSpeaker() throws Exception {
        // prepare data
        Workshop workshop = Workshop.find.byId(1l);
        workshop.speakers.add( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop.save();

        // before action
        assertThat( workshop.speakers.size() ).isEqualTo(2);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/removeSpeaker/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( Workshop.find.byId(1l).speakers.size() ).isEqualTo(1);

    }

    @Test
    public void testAddParticipant_and_place_available() throws Exception {
        // Mocks
        Workshop workshop = Workshop.find.byId(1l);
        WorkshopSession mockSession = mock( WorkshopSession.class );
        when( mockSession.nextPlay ).thenReturn( new Date());
        when( mockSession.workshop ).thenReturn(workshop);

        // before action
        assertThat( workshop.workshopSession.get(0).participants.size() ).isEqualTo(2);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/addParticipant/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        Workshop workshop1 = Workshop.find.byId(1l);
        assertThat( workshop1.workshopSession.get(0).participants.size() ).isEqualTo(3);

        // cleaning
        workshop1.workshopSession.get(0).participants.remove( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop1.workshopSession.get(0).save();
    }

    @Test
    public void testAddParticipant_and_place_unavailable() throws Exception {
        // before action
        assertThat( Workshop.find.byId(3l).workshopSession.get(0).participants.size() ).isEqualTo(3);
        assertThat( Workshop.find.byId(3l).workshopSession.get(0).limitePlace ).isEqualTo(3);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/addParticipant/3")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testNotInOtherSession_false() throws Exception {
        // Mocks
        fakeSession();
        Workshop workshop = Workshop.find.byId(3l);
        WorkshopSession mockSession = mock( WorkshopSession.class );
        when( mockSession.nextPlay ).thenReturn( workshop.workshopSession.get(1).nextPlay );
        when( mockSession.workshop ).thenReturn(workshop);

        // the action
        boolean result = WorkshopController.notInOtherSession( mockSession, "sylvie.dupont@test.com" );

        // after action
        assertThat(result).isEqualTo(false);
    }

    @Test
    public void testNotInOtherSession_true() throws Exception {
        // Mocks
        fakeSession();
        Workshop workshop = Workshop.find.byId(3l);
        WorkshopSession mockSession = mock( WorkshopSession.class );
        when( mockSession.nextPlay ).thenReturn( workshop.workshopSession.get(1).nextPlay );
        when( mockSession.workshop ).thenReturn(workshop);

        // the action
        boolean result = WorkshopController.notInOtherSession( mockSession, "delete.dupont@test.com" );

        // after action
        assertThat(result).isEqualTo(true);
    }

    @Test
    public void testRemoveParticipant() throws Exception {
        // prepare data
        Workshop workshop = Workshop.find.byId(3l);
        workshop.workshopSession.get(0).participants.add( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop.workshopSession.get(0).save();
        workshop.save();

        // before action
        assertThat( workshop.workshopSession.get(0).participants.size() ).isEqualTo(4);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/removeParticipant/3")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( Workshop.find.byId(3l).workshopSession.get(0).participants.size() ).isEqualTo(3);

    }

    @Test
    public void testAddPotentialParticipant() throws Exception {
        // before action
        assertThat( Workshop.find.byId(1l).potentialParticipants.size() ).isEqualTo(2);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/addPotentialParticipant/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        Workshop workshop1 = Workshop.find.byId(1l);
        assertThat( workshop1.potentialParticipants.size() ).isEqualTo(3);

        // cleaning
        workshop1.potentialParticipants.remove( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop1.save();
    }

    @Test
    public void testRemovePotentialParticipant() throws Exception {
        // prepare data
        Workshop workshop = Workshop.find.byId(1l);
        workshop.potentialParticipants.add( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop.save();

        // before action
        assertThat(workshop.potentialParticipants.size()).isEqualTo(3);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/removePotentialParticipant/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( Workshop.find.byId(1l).potentialParticipants.size() ).isEqualTo(2);
    }

    @Test
    public void testAddInterestedParticipant() throws Exception {
        // before action
        assertThat( Workshop.find.byId(1l).potentialParticipants.size() ).isEqualTo(2);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/addPotentialParticipant/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        Workshop workshop1 = Workshop.find.byId(1l);
        assertThat( workshop1.potentialParticipants.size() ).isEqualTo(3);

        // cleaning
        workshop1.potentialParticipants.remove( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop1.save();
    }

    @Test
    public void testRemoveInterestedParticipant() throws Exception {
        // prepare data
        Workshop workshop = Workshop.find.byId(1l);
        workshop.potentialParticipants.add( User.find.where().eq("email", "sylvie.dupont@test.com").findUnique() );
        workshop.save();

        // before action
        assertThat( workshop.potentialParticipants.size() ).isEqualTo(3);

        // the action
        Result result = route(fakeRequest(GET, "/workshops/removePotentialParticipant/1")
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( Workshop.find.byId(1l).potentialParticipants.size() ).isEqualTo(2);
    }

    @Test
    public void testAddComment() throws Exception {
        Result result = route(fakeRequest(GET, "/workshop/addComment/1")
                            .withSession("uuid", UUID)
                            .withSession("email", "sylvie.dupont@test.com"));
        assertThat(status(result)).isEqualTo(OK);
        assertThat(contentType(result)).isEqualTo("text/html");
    }

    @Test
    public void testSaveComment() throws Exception {
        // prepare data
        Map<String, String> form = new HashMap<>();
        form.put("comment", "a great comment");

        // before action
        assertThat( Workshop.find.byId(1l).comments.size() ).isEqualTo(0);

        // the action
        Result result = route(fakeRequest(POST, "/workshop/saveComment/1/-1")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        Workshop workshop = Workshop.find.byId(1l);
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( workshop.comments.size() ).isEqualTo(1);
        assertThat( workshop.comments.iterator().next().author.firstName).isEqualTo("sylvie");
        assertThat( workshop.comments.iterator().next().comment).isEqualTo("a great comment");
    }

    @Test
    public void testAddWorkshopResources_as_admin() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshop/addRessources/1")
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testAddWorkshopResources_as_author() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshop/addRessources/1")
                .withSession("uuid", UUID)
                .withSession("email", "greg.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(OK);
    }

    @Test
    public void testAddWorkshopResources_as_standard() throws Exception {
        // the action
        Result result = route(fakeRequest(GET, "/workshop/addRessources/1")
                .withSession("uuid", UUID)
                .withSession("email", "marie.dupont@test.com"));

        // the tests
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

    @Test
    public void testSaveWorkshopResources_as_admin() throws Exception {
        // prepare data
        Map<String, String> form = new HashMap<>();
        form.put("workshopSupportLink", "http://google.com");
        form.put("workshopSupportFile", "");

        // before action
        assertThat( Workshop.find.byId(1l).workshopRessources ).isNull();

        // the action
        Result result = route(fakeRequest(POST, "/workshop/saveRessources/1")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "alain.dupont@test.com"));

        // after action
        Workshop workshop = Workshop.find.byId(1l);
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( workshop.workshopRessources ).isNotNull();
        assertThat( workshop.workshopRessources.workshopSupportLink).isEqualTo("http://google.com");
        assertThat( workshop.workshopRessources.workshopSupportFile).isNullOrEmpty();
    }

    @Test
    public void testSaveWorkshopResources_as_OneWorkshopSpeaker() throws Exception {
        // prepare data
        Map<String, String> form = new HashMap<>();
        form.put("workshopSupportLink", "http://google.com");
        form.put("workshopSupportFile", "");

        // the action
        Result result = route(fakeRequest(POST, "/workshop/saveRessources/1")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "sylvie.dupont@test.com"));

        // after action
        Workshop workshop = Workshop.find.byId(1l);
        assertThat(status(result)).isEqualTo(SEE_OTHER);
        assertThat( workshop.workshopRessources ).isNotNull();
        assertThat( workshop.workshopRessources.workshopSupportLink).isEqualTo("http://google.com");
        assertThat( workshop.workshopRessources.workshopSupportFile).isNullOrEmpty();
    }

    @Test
    public void testSaveWorkshopResources_as_standard() throws Exception {
        // prepare data
        Map<String, String> form = new HashMap<>();
        form.put("workshopSupportLink", "http://google.com");
        form.put("workshopSupportFile", "");

        // the action
        Result result = route(fakeRequest(POST, "/workshop/saveRessources/1")
                .withFormUrlEncodedBody(form)
                .withSession("uuid", UUID)
                .withSession("email", "marie.dupont@test.com"));

        // after action
        assertThat(status(result)).isEqualTo(FORBIDDEN);
    }

}
