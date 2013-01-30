package controllers;

import static models.utils.constants.WorkShopConstants.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import models.User;
import models.Workshop;
import models.WorkshopSession;
import play.Logger;
import play.Play;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.workshops.addWorkshop;
import views.html.workshops.planWorkshop;

/**
 * Ce controller regroupe toutes les actions qui sont liées à l'ajout d'un
 * nouveau Workshop
 * 
 * @author ychartois
 * @version 0.1
 */
public class WorkshopController extends Controller {

	// <--------------------------------------------------------------------------->
	// - Constructeur(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Constructeur par defaut
	 */
	public WorkshopController() {
		super();
	}

	// <--------------------------------------------------------------------------->
	// - Actions(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Display a blank form for Workshop.
	 * 
	 * @return the workshopForm empty
	 */
	public static Result blankWorkshop() {
		return ok(addWorkshop.render(form(Workshop.class), ID_NOT_IN_TABLE));
	}

	/**
	 * Handle the 'new workshop form' submission
	 * 
	 * @return the welcome page or the addWorkshop page if the validation has
	 *         errors
	 */
	@Transactional
	public static Result saveWorkshop(Long id) {
		Form<Workshop> workshopForm = form(Workshop.class).bindFromRequest();

		if (workshopForm.hasErrors()) {
			return badRequest(addWorkshop.render(workshopForm, id));
		}

		// On récupère le workshop depuis le formulaire
		Workshop 		workshopNew 	= 	workshopForm.get();
		
		// Et le workshop depuis la base si c'est une modification
		if ( id != ID_NOT_IN_TABLE ) {
			Workshop 	ws 				= 	JPA.em().find(Workshop.class, id);
			// on met à jour la nouvelle instance avec les anciennes données
			workshopNew.setSpeakers( ws.getSpeakers() );
			workshopNew.setPotentialParticipants( ws.getPotentialParticipants() );
		}

        // On affecte l'auteur connecté
		workshopNew.setAuthor( AuthentificationController.getUser() );
        
		// On set l'image du workshop
		workshopNew.setImage( uploadImage() );

		if (id == ID_NOT_IN_TABLE) {
			JPA.em().persist(workshopNew);
		} else {
			workshopNew.setId(id);
			JPA.em().merge(workshopNew);
		}

		return redirect(routes.Application.welcome());
	}

	/**
	 * @param id
	 *            id du workshop
	 * @return la page permettant de modifier le workshop
	 */
	@Transactional
	public static Result modifyWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		Form<Workshop> workshopForm = form(Workshop.class).fill(ws);

		return ok( addWorkshop.render(workshopForm, id) );
	}

	/**
	 * @param id
	 *            l'identifiant du workshop
	 * @return the welcome page
	 */
	@Transactional
	public static Result deleteWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		JPA.em().remove(ws);

		for (String key : request().headers().keySet()) {
			System.out.println(key);
		}

		return redirect(routes.Application.welcome());
	}

	/**
	 * @param id
	 *            l'identifiant du workshop
	 * @return the planWorkshop page
	 */
	@Transactional
	public static Result planWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);

		Form<WorkshopSession> workshopSessionForm;
		if (ws.getWorkshopSession() != null) {
			workshopSessionForm = form(WorkshopSession.class).fill(
					ws.getWorkshopSession());
		} else {
			workshopSessionForm = form(WorkshopSession.class);
		}

		return ok(planWorkshop.render(workshopSessionForm, id));
	}

	/**
	 * @param id
	 *            l'identifiant du workshop
	 * @return the welcome page or the planWorkshop page if the validation has
	 *         errors
	 */
	@Transactional
	public static Result saveWorkshopSession(Long id) {

		Form<WorkshopSession> filledForm = form(WorkshopSession.class)
				.bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(planWorkshop.render(filledForm, id));
		}

		// We get the Workshop
		Workshop workshopToPlan 	= 	JPA.em().find(Workshop.class, id);
		boolean newSession 			= 	workshopToPlan.getWorkshopSession() == null;

		// We set the WorkshopSession to the Workshop to Plan
		WorkshopSession workshopSession = filledForm.get();
		workshopToPlan.setWorkshopSession(workshopSession);

		// Sauver l'objet en base
		if (!newSession) {
			JPA.em().merge(workshopSession);
		} else {
			JPA.em().persist(workshopSession);
		}
		JPA.em().persist(workshopToPlan);

		return redirect(routes.Application.welcome());
    }

    /**
     * Allow to add a proposal Speaker to the speaker List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    public static Result addSpeaker( Long id ) {
    	Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true,true);
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	JPA.em().find(Workshop.class, id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.getSpeakers().add( user );
        
        // We save the new Workshop
        JPA.em().persist( currentWorkshop );
    	
        return redirect(routes.Application.welcome());
    }
    
    /**
     * Allow to remove a proposal Speaker to the speaker List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    public static Result removeSpeaker( Long id ) {
    	Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true,true);
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	JPA.em().find(Workshop.class, id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.getSpeakers().remove( user );
        
        // We save the new Workshop
        JPA.em().persist( currentWorkshop );
    	
        return redirect(routes.Application.welcome());
    }
    
    /**
     * Allow to add a participant to the potential participants List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    public static Result addParticipant( Long id ) {
    	Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true,true);
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	JPA.em().find(Workshop.class, id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.getPotentialParticipants().add( user );
        
        // We save the new Workshop
        JPA.em().persist( currentWorkshop );
    	
        return redirect(routes.Application.welcome());
	}
    
    /**
     * Allow to remove a participant to the potential participants List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    public static Result removeParticipant( Long id ) {
    	Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true,true);
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	JPA.em().find(Workshop.class, id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.getPotentialParticipants().remove( user );
        
        // We save the new Workshop
        JPA.em().persist( currentWorkshop );
    	
        return redirect(routes.Application.welcome());
    }
    
    
    
	// <--------------------------------------------------------------------------->
	// - helper methods
	// <--------------------------------------------------------------------------->
	/**
	 * @return la liste des Workshops planifiés qui a été placé dans le context
	 */
	public static List<Workshop> getWorkshopsPlanifieFromContext() {
		List<Workshop> listWsPlanifie = (List<Workshop>) Http.Context.current().args.get("wsPlanifie");
		return listWsPlanifie != null ? listWsPlanifie
				: new ArrayList<Workshop>();
	}

	/**
	 * @return la date décorée
	 */
	public static String decorateDate(Date date) {
		return new SimpleDateFormat(DATE_PATTERN).format(date);
	}
	
	/**
	 * Permet de découper la description du workshop
	 * 
	 * @param workshop un workshop
	 * @return la description du workshop tronquée
	 */
	public static String getWorkshopDescription( Workshop workshop ) {
		int maxlength = Play.application().configuration().getInt( "detail.workshop.main.view" );

		return ( workshop.getDescription().length() > maxlength ) 
				? workshop.getDescription().substring(0, maxlength) + "..." 
				:  workshop.getDescription() ;
	}
	
	
	// <--------------------------------------------------------------------------->
	// - helper methods private
	// <--------------------------------------------------------------------------->	
	/**
	 * Upload l'image spécifié et retourne le lien vers cette image
	 * 
	 * @return le chemin vers l'image qui a été uploader dans le système
	 */
	private static String uploadImage() {
    	String imageLocation 	= 	Play.application().configuration().getString("workshop.images.url") + "/";
    	String defaultImage 	= 	imageLocation + "logo_sqli.png";
    	
    	// Gestion de la sauvegarde des fichiers uploadés (images)
 		MultipartFormData body = request().body().asMultipartFormData(); 
 		FilePart picture = body.getFile("image");
 		if (picture != null) {
 			String fileName = picture.getFilename();
 			// TODO : renommer le fichier avant de le sauvegarder
 			// TODO : vérifier si le fichier existe
 			// String contentType = picture.getContentType();
 			File file = picture.getFile();

 			// On sauvegarde le fichier
 			try {
 				String myUploadPath = Play.application().path()
 						+ "/"
 						+ Play.application().configuration().getString("workshop.images.directory");
 				
 				if ( !file.renameTo(new File(myUploadPath, fileName)) ) {
 					Logger.info("Erreur lors de la copie du fichier image " + fileName);
 				}
 				
 				return imageLocation + fileName;
 				
 			} catch (Exception e) {
 				// TODO : préciser les exceptions
 				// TODO Prévenir le user que le fichier ne s'est pas copié (utiliser les flash messages ?)
 				Logger.info("Erreur lors de la copie du fichier image "
 						+ fileName);
 			}
 		}
 		
 		return defaultImage;
    }

}
