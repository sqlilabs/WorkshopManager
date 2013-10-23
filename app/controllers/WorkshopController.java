package controllers;

import static models.utils.constants.WorkShopConstants.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import models.*;
import org.apache.commons.lang.StringUtils;

import com.avaje.ebean.Ebean;

import models.utils.enums.ActionEnum;
import models.utils.formatter.UserFormatter;
import models.utils.helpers.ActionsUtils;
import models.utils.helpers.FilesUtils;
import play.Play;
import play.data.Form;
import play.db.ebean.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import play.mvc.Security;
import views.html.errors.error;
import views.html.workshops.addWorkshop;
import views.html.workshops.planWorkshop;
import views.html.workshops.addComment;
import views.html.workshops.addRessources;

import javax.naming.Context;

/**
 * The controller contains the actions allowed on events
 * 
 * @author ychartois
 */
public class WorkshopController extends Controller {



	// <--------------------------------------------------------------------------->
	// - 							      Actions(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Display a blank form for Workshop.
	 * 
	 * @return the workshopForm empty
	 */
	public static Result blankWorkshop() {
		return ok(addWorkshop.render(play.data.Form.form(Workshop.class), ID_NOT_IN_TABLE));
	}

	/**
	 * Handle the 'new workshop form' submission
	 * 
	 * @return the welcome page or the addWorkshop page if the validation has
	 *         errors
	 */
	@Transactional
    @Security.Authenticated(Secured.class)
	public static Result saveWorkshop(Long id) {
		Form<Workshop> workshopForm = play.data.Form.form(Workshop.class).bindFromRequest();

		if (workshopForm.hasErrors()) {
			return badRequest(addWorkshop.render(workshopForm, id));
		}

		Workshop 		workshopNew 		= 	workshopForm.get();
		
		// We get the event from the database if it's a modification
		if ( id != ID_NOT_IN_TABLE ) {
			Workshop 	ws 					= 	Workshop.find
                                                    .fetch("author")
                                                    .fetch("potentialParticipants")
                                                    .fetch("speakers")
                                                    .where()
                                                        .eq("id", id)
                                                    .findUnique();

			//Workshop.find.byId(id);

			// we update the new instance
			workshopNew.speakers				=	ws.speakers;
			workshopNew.potentialParticipants	=	ws.potentialParticipants ;
			workshopNew.author 					=	ws.author;
			workshopNew.creationDate			=	ws.creationDate;

            // We set the event image. If it's not a new image, we keep the old one
			String		image					=	uploadImage();
			workshopNew.image					=	image.endsWith("default.png") ? ws.image : image ;

			ActionsUtils.logAction( ActionEnum.MODIFY_WORKSHOP, Secured.getUser(), workshopNew.subject);
		}
		else {
			workshopNew.author 				=	Secured.getUser() ;
			workshopNew.creationDate		= 	new Date();
			workshopNew.image				=	uploadImage();

			ActionsUtils.logAction( ActionEnum.NEW_WORKSHOP, Secured.getUser(), workshopNew.subject);
			
		}
        
		if (id == ID_NOT_IN_TABLE) {
			Ebean.save(workshopNew);
		} 
		else {
			workshopNew.id					=	id;
			Ebean.update(workshopNew);
		}

		return redirect(routes.Application.newWorkshops() + "#" + id);
	}

	/**
     * Return the event modification form
     *
	 * @param id  event id
	 * @return the event modification form
	 */
	@Transactional
    @Security.Authenticated(Secured.class)
	public static Result modifyWorkshop(Long id) {
        Workshop ws = Workshop.find.byId(id);

        if (!Secured.isMemberOf(Roles.ADMIN) && !UsersController.isAuthor(ws)) {
            return forbidden();
        }

        Form<Workshop> workshopForm = play.data.Form.form(Workshop.class).fill(ws);

		return ok( addWorkshop.render(workshopForm, id) );
	}

	/**
     * Allows to delete a Workshop
     *
	 * @param id  event id
	 * @return the welcome page
	 */
	@Transactional
    @Security.Authenticated(Secured.class)
	public static Result deleteWorkshop(Long id) {
		Workshop ws = Workshop.find.byId(id);

        if (!Secured.isMemberOf(Roles.ADMIN) && !UsersController.isAuthor(ws)) {
            return forbidden();
        }

		Ebean.delete(ws);
		ActionsUtils.logAction( ActionEnum.DELETE_WORKSHOP, Secured.getUser(), ws.subject);

		return redirect( routes.Application.newWorkshops() );
	}

	/**
     * Return the event planning form
     *
	 * @param id  event id
	 * @return the planWorkshop page
	 */
	@Transactional
    @Security.Authenticated(Secured.class)
	public static Result planWorkshop(Long id) {

        if (!Secured.isMemberOf(Roles.ADMIN)) {
            return forbidden();
        }

		Form<WorkshopSession> workshopSessionForm = play.data.Form.form(WorkshopSession.class);

		return ok(planWorkshop.render(workshopSessionForm, id, -1l));
	}
	
	/**
     * Return the event planning form for modification of a already planned event
     *
	 * @param idWorkshop event id
	 * @return the planWorkshop page
	 */
	@Transactional
    @Security.Authenticated(Secured.class)
	public static Result modifyPlannedWorkshop(Long idWorkshop, Long idSession) {

        if (!Secured.isMemberOf(Roles.ADMIN)) {
            return forbidden();
        }

        WorkshopSession		session		= WorkshopSession.find.byId( idSession );
				
		Form<WorkshopSession> workshopSessionForm;
		if (session != null) {
			workshopSessionForm = play.data.Form.form(WorkshopSession.class).fill( session );
		} else {
			workshopSessionForm = play.data.Form.form(WorkshopSession.class);
		}

		return ok( planWorkshop.render(workshopSessionForm, idWorkshop, idSession) );
	}

	/**
	 * Allows to save an event session
     *
     * @param idWorkshop event id
	 * @return the welcome page or the planWorkshop page if the validation has
	 *         errors
	 */
	@Transactional
    @Security.Authenticated(Secured.class)
	public static Result saveWorkshopSession(Long idWorkshop, Long idSession) {

        if (!Secured.isMemberOf(Roles.ADMIN)) {
            return forbidden();
        }

		Form<WorkshopSession> filledForm = play.data.Form.form(WorkshopSession.class).bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(planWorkshop.render(filledForm, idWorkshop, idSession));
		}

		// We get the Workshop
		Workshop workshopToPlan 	= 	Workshop.find.byId(idWorkshop);
		boolean newSession 			= 	idSession == -1;

		// We set the WorkshopSession to the Workshop to Plan
		WorkshopSession workshopSession 	= filledForm.get();
		if ( !newSession ) {
			WorkshopSession	oldSession		=	WorkshopSession.find.byId( idSession ) ;
			workshopSession.id				=	idSession ;
			workshopSession.participants	=	new HashSet<User>(oldSession.participants);
			workshopToPlan.workshopSession.remove( oldSession );
			ActionsUtils.logAction( ActionEnum.MODIFY_SESSION, Secured.getUser(), workshopToPlan.subject);
		}
		else {
			ActionsUtils.logAction( ActionEnum.ADD_SESSION, Secured.getUser(), workshopToPlan.subject);
		}
		workshopToPlan.workshopSession.add(workshopSession);
		workshopSession.workshop			=	workshopToPlan;
		
		// We empty the potentialParticipants List
		workshopToPlan.potentialParticipants	= 	new HashSet<User>();

		// We save the new session
		if (!newSession) {
			Ebean.update(workshopSession);
		} else {
			Ebean.save(workshopSession);
		}
		Ebean.save(workshopToPlan);

		return redirect( routes.Application.welcome() + "#" + idWorkshop );
    }

    /**
     * Allows to add a proposal Speaker to the speaker List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addSpeaker( Long id ) {
    	
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        if ( currentWorkshop.speakers.size() < Play.application().configuration().getInt( "speaker.limit" )) {
        	// Get the connected User
            User		user				=	Secured.getUser();
            
            // It's a Set, so no duplicate
            currentWorkshop.speakers.add( user );
            
            // We save the new Workshop
            Ebean.update(currentWorkshop);
    	}
        else {
        	return ok ( error.render( Messages.get("error.speaker.limit.reached")) );
        }
        
        return redirect(routes.Application.newWorkshops() + "#" + id);
    }
    
    /**
     * Allows to remove a proposal Speaker to the speaker List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result removeSpeaker( Long id ) {
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.speakers.remove( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.newWorkshops() + "#" + id);
    }
    
    /**
     * Allows to add a participant to the potential participants List for a selected session
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addParticipant( Long id ) {
    	// We get the Workshop
    	WorkshopSession		currentSession 		= 	WorkshopSession.find.byId(id);
        
        // Get the connected User
        User				user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        if ( (currentSession.limitePlace == 0  || currentSession.limitePlace != 0 && currentSession.participants.size() < currentSession.limitePlace) && notInOtherSession( currentSession ) ) {
        	currentSession.participants.add( user );
        }
        else {
        	return ok ( error.render( Messages.get("error.participants.limit.reached")) );
        }
        
        // We save the new Workshop
        Ebean.save(currentSession);
    	
        return redirect(routes.Application.welcome() + "#" + id);
	}
    
    /**
     * Check if the user is not in an other session which is planned or just played 
     * during the month
     * 
     * @param currentSession the surrent workshop session
     * @return true if the user has not already join an other session
     */
    static boolean notInOtherSession( WorkshopSession currentSession ) {
    	String username = Secured.getUser().email;
		return notInOtherSession(currentSession, username);
	}
    
    /**
     * Check if the user is not in an other session which is planned or just played 
     * during the month
     * 
     * @param currentSession the surrent workshop session
     * @return true if the user has not already join an other session
     */
    static boolean notInOtherSession( WorkshopSession currentSession, String username) {
		Workshop 			workshop 	= 	currentSession.workshop;
		
		// On calcule la date un mois avant la date du workshop courant
		GregorianCalendar	calendar	=	new GregorianCalendar();
		calendar.setTime(currentSession.nextPlay);
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		Date				lastMonth	=	calendar.getTime(); 
		for ( WorkshopSession session : workshop.workshopSession ) {
			for (User user : session.participants) {
				if (user.email.equals(username)) {
					// On ne check pas les sessions qui ont déjà eu lieu les mois précédent
					if ( session.nextPlay.before( lastMonth ) ) {
						continue;
					}
					return false;
				}
			}
		}
		
		return true;
	}

	/**
     * Allows to remove a participant to the potential participants List for a selected session
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result removeParticipant( Long id ) {
    	// We get the Workshop
    	WorkshopSession		currentSession 		= 	WorkshopSession.find.byId(id);
        
        // Get the connected User
        User				user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        currentSession.participants.remove( user );
        
        // We save the new Workshop
        Ebean.save(currentSession);
    	
        return redirect(routes.Application.welcome() + "#" + id);
    }
    
    /**
     * Allows to add a participant to the potential participants List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addPotentialParticipant( Long id ) {
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.potentialParticipants.add( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.newWorkshops() + "#" + id);
	}
    
    /**
     * Allows to remove a participant to the potential participants List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result removePotentialParticipant( Long id ) {
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.potentialParticipants.remove( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.newWorkshops() + "#" + id);
    }
    
    /**
     * Allows to add a participant to the potential participants List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addInterrestedParticipant( Long id ) {
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.potentialParticipants.add( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.workshops() + "#" + id);
	}
    
    /**
     * Allows to remove a participant to the potential participants List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result removeInterrestedParticipant( Long id ) {
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	Secured.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.potentialParticipants.remove( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.workshops() + "#" + id);
    }

	/**
	 * Prepare the form to add comments
	 * 
	 * @param id the workshop ID
	 * @return the comment form view
	 */
    @Security.Authenticated(Secured.class)
	public static Result addComment(Long id) {
		Form<Comment> commentForm = play.data.Form.form(Comment.class);

		return ok( addComment.render(commentForm, id) );
	}
    
    /**
     * Save the comments
     * 
     * @param id the workshop ID
     * @return redirect on workshop already played view
     */
    @Security.Authenticated(Secured.class)
    @Transactional
    public static Result saveComment( Long id ) {
    	Form<Comment> 	filledForm 	= 	play.data.Form.form(Comment.class).bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
			return badRequest( addComment.render(filledForm, id) );
		}
    	
    	// Get the workshop from base
    	Workshop 		ws 			= 	Workshop.find.byId(id);
    	
    	//We create the comment instance
    	Comment 		comment 	= 	filledForm.get();
    	comment.creationDate 		= 	new Date();
    	comment.author				=	Secured.getUser();
    	comment.workshop			=	ws;
    	
    	// We add the new comment
    	ws.comments.add( comment );
    	
    	// We save the objects in base
    	Ebean.save( comment );
        Ebean.update( ws );
        ActionsUtils.logAction( ActionEnum.COMMENT, Secured.getUser(), ws.subject);
    	
        return redirect( routes.Application.workshops() + "#" + id);
    }
    
    
    /**
     * Prepare the form to add resources
     * 
	 * @param id the workshop ID
	 * @return the resources form view
	 */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result addWorkshopRessources(Long id) {
    	Workshop 	ws 			= 	Workshop.find.byId(id);

        if (!Secured.isMemberOf(Roles.ADMIN) && !UsersController.isAuthor(ws)) {
            return forbidden();
        }

    	Ressources 	ressources	=	ws.workshopRessources;
    	
    	// if we already set resources, we want to fill the form with our old data
    	Form<Ressources> resourcesForm = null;
    	if ( ressources != null ) {
    		resourcesForm 		= 	play.data.Form.form(Ressources.class).fill(ressources);
    	}
    	else {
    		resourcesForm 		= 	play.data.Form.form(Ressources.class);
    	}

		return ok( addRessources.render(resourcesForm, id) );
	}
    
    /**
     * Save the new resources
     * 
     * @param id the workshop ID
     * @return redirect on workshop already played view
     */
    @Transactional
    @Security.Authenticated(Secured.class)
    public static Result saveWorkshopRessources( Long id ) {
    	Form<Ressources> 	filledForm 	= 	play.data.Form.form(Ressources.class).bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
			return badRequest(addRessources.render(filledForm, id));
		}
    	
    	// Get the workshop from base
    	Workshop 		ws 				= 	Workshop.find.byId(id);

        if (!Secured.isMemberOf(Roles.ADMIN) && !UsersController.isAuthor(ws)) {
            return forbidden();
        }

    	boolean			update			=	 ws.workshopRessources != null;
    	
    	//We create the Resources instance
    	Ressources 		ressources 		= 	filledForm.get();
    	ressources.workshopSupportFile	=	uploadRessources( ws );
    	if ( update ) {
    		ressources.id				=	ws.workshopRessources.id;
    	}
    	
    	// We add the new ressources
    	ws.workshopRessources 			= 	ressources;
    	
    	// We save the objects in base
    	if (update) {
    		Ebean.update( ressources );
    	}
    	else {
    		Ebean.save( ressources );
    	}
    	Ebean.update( ws );
    	ActionsUtils.logAction( ActionEnum.ADD_SUPPORT, Secured.getUser(), ws.subject);
    	
        return redirect( routes.Application.workshops() + "#" + id );
    }
    
    
    
	// <--------------------------------------------------------------------------->
	// -                             helper methods
	// <--------------------------------------------------------------------------->
	/**
	 * @return the planned workshops list which is stored in the context
	 */
	public static List<Workshop> getWorkshopsPlanifieFromContext() {
		@SuppressWarnings("unchecked")
		List<Workshop> listWsPlanifie = (List<Workshop>) Http.Context.current().args.get("wsPlanifie");
		return listWsPlanifie != null ? listWsPlanifie
				: new ArrayList<Workshop>();
	}


    // <--------------------------------------------------------------------------->
	// -                        helper methods private
	// <--------------------------------------------------------------------------->	
	/**
     * Upload the specified image and return the link to it
	 * 
	 * @return the image link in the system
	 */
	private static String uploadImage() {
    	String imageLocation 	= 	Play.application().configuration().getString("workshop.images.url") + File.separator;
    	String defaultImage 	= 	imageLocation + "default.png";
    	
 		MultipartFormData body = request().body().asMultipartFormData();
 		FilePart picture = body != null ? body.getFile("image") : null;
 		if (picture != null) {
 			String fileName = picture.getFilename();
 			File file = picture.getFile();

            // We save the file
			String myUploadPath = Play.application().path()
					+ File.separator
					+ Play.application().configuration().getString("workshop.images.directory");
			
			// We check if the dest file exists
			File dest = new File(myUploadPath, fileName); 
			if ( dest.exists() ) {
				dest.delete();
			}
				
			// If the file copy encounter an exception, we use the default picture
			if ( FilesUtils.fastCopyFileCore( file, dest ) ) {
				return imageLocation + fileName;
			}
 		}
 		
 		return defaultImage;
    }
	
	/**
	 * Upload the specified File and return the link to it
	 * 
	 * @return the File link in the system
	 */
	private static String uploadRessources( Workshop workshop ) {
		SimpleDateFormat 	simpleDateFormat	=	new SimpleDateFormat("[yyyy-MM]");
		String				destFolderName		=	simpleDateFormat.format( workshop.workshopSession.get(0).nextPlay ) + " - " + workshop.subject;
    	String 				ressourceLocation 	= 	Play.application().configuration().getString("workshop.ressources.url") + File.separator + destFolderName + File.separator;
    	
 		MultipartFormData 	body 				= 	request().body().asMultipartFormData();
 		FilePart 			ressource 			= 	body != null ? body.getFile("workshopSupportFile") : null;
 		
 		if (ressource != null && !StringUtils.EMPTY.equals( ressource.getFilename()) ) {
 			String 			fileName 			= 	ressource.getFilename();
 			File 			file 				= 	ressource.getFile();

 			// We save the file
			String 			myUploadPath 		= 	Play.application().path() + File.separator
														+ Play.application().configuration().getString("workshop.ressources.directory")
														+ File.separator + destFolderName;
			File			destFolder			=	new File(myUploadPath);
			destFolder.mkdirs();
			
			// We check if the dest file exists
			File dest = new File(myUploadPath, fileName); 
			if ( dest.exists() ) {
				dest.delete();
			}
				
			// If the file copy encounter an exception, we use the default picture
			if ( FilesUtils.fastCopyFileCore( file, dest ) ) {
				return ressourceLocation + fileName;
			}
 		}
 		
 		return null;
    }
	
	// <--------------------------------------------------------------------------->
	// -                                Constructor(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Default Constructor
	 */
	private WorkshopController() {
		super();
	}

}
