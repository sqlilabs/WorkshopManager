package controllers;

import static models.utils.constants.WorkShopConstants.*;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import models.Comment;
import models.Ressources;
import models.User;
import models.Workshop;
import models.WorkshopSession;
import models.utils.formatter.UserFormatter;
import models.utils.helpers.FilesUtils;
import play.Play;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.i18n.Messages;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;
import views.html.errors.error;
import views.html.workshops.addWorkshop;
import views.html.workshops.planWorkshop;
import views.html.workshops.addComment;
import views.html.workshops.addRessources;

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
		
		// We empty the potentialParticipants List
		workshopToPlan.setPotentialParticipants( new HashSet<User>() );

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
    	
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	JPA.em().find(Workshop.class, id);
        
        if ( currentWorkshop.getSpeakers().size() < Play.application().configuration().getInt( "speaker.limit" )) {
        	// Get the connected User
            User		user				=	AuthentificationController.getUser();
            
            // It's a Set, so no duplicate
            currentWorkshop.getSpeakers().add( user );
            
            // We save the new Workshop
            JPA.em().persist( currentWorkshop );
    	}
        else {
        	return ok ( error.render( Messages.get("error.speaker.limit.reached")) );
        }
        
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
    

	/**
	 * Prepare the form to add comments
	 * 
	 * @param id the workshop ID
	 * @return the comment form view
	 */
	public static Result addComment(Long id) {
		Form<Comment> commentForm = form(Comment.class);

		return ok( addComment.render(commentForm, id) );
	}
    
    /**
     * Save the comments
     * 
     * @param id the workshop ID
     * @return redirect on workshop already played view
     */
    @Transactional
    public static Result saveComment( Long id ) {
    	Form<Comment> 	filledForm 	= 	form(Comment.class).bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
			return badRequest(addComment.render(filledForm, id));
		}
    	
    	// Get the workshop from base
    	Workshop 		ws 			= 	JPA.em().find(Workshop.class, id);
    	
    	//We create the comment instance
    	Comment 		comment 	= 	filledForm.get();
    	comment.setCreationDate(new Date());
    	comment.setAuthor( AuthentificationController.getUser() );
    	comment.setWorkshop( ws );
    	
    	// We add the new comment
    	ws.getComments().add( comment );
    	
    	// We save the objects in base
        JPA.em().persist( comment );
        JPA.em().merge( ws );
    	
        return redirect(routes.Application.workshops());
    }
    
    
    /**
     * Prepare the form to add ressources
     * 
	 * @param id the workshop ID
	 * @return the ressources form view
	 */
    @Transactional
    public static Result addWorkshopRessources(Long id) {
    	Workshop 	ws 			= 	JPA.em().find(Workshop.class, id);
    	Ressources 	ressources	=	ws.getWorkshopRessources();
    	
    	// if we already set ressources, we want to fill the form with our old datas
    	Form<Ressources> ressourcesForm = null;
    	if ( ressources != null ) {
    		ressourcesForm 		= 	form(Ressources.class).fill(ressources);
    	}
    	else {
    		ressourcesForm 		= 	form(Ressources.class);
    	}

		return ok( addRessources.render(ressourcesForm, id) );
	}
    
    /**
     * Save the new ressources
     * 
     * @param id the workshop ID
     * @return redirect on workshop already played view
     */
    @Transactional
    public static Result saveWorkshopRessources( Long id ) {
    	Form<Ressources> 	filledForm 	= 	form(Ressources.class).bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
			return badRequest(addRessources.render(filledForm, id));
		}
    	
    	// Get the workshop from base
    	Workshop 		ws 			= 	JPA.em().find(Workshop.class, id);
    	
    	boolean			update		=	 ws.getWorkshopRessources() != null;
    	
    	//We create the Ressources instance
    	Ressources 		ressources 	= 	filledForm.get();
    	ressources.setWorkshopSupportFile( uploadRessources( ws ) );
    	if ( update ) {
    		ressources.setId( ws.getWorkshopRessources().getId() );
    	}
    	
    	// We add the new ressources
    	ws.setWorkshopRessources( ressources );
    	
    	// We save the objects in base
    	if (update) {
    		JPA.em().merge( ressources );
    	}
    	else {
    		JPA.em().persist( ressources );
    	}
        JPA.em().merge( ws );
    	
        return redirect(routes.Application.workshops());
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
	 * Permet de découper la description du workshop courte
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
	
	/**
	 * Permet de découper la description du workshop complète
	 * 
	 * @param workshop un workshop
	 * @return la description du workshop tronquée
	 */
	public static String getFullWorkshopDescription( Workshop workshop ) {
		return workshop.getDescription() ;
	}
	
	/**
	 * @param speakers the list of speakers
	 * @return the nam of the foorseen User
	 */
	public static String getForseenSpeaker( Set<User> speakers ) {
		
		if ( speakers.isEmpty() ) {
			return "";
		}
		
		User user = (User) speakers.toArray()[speakers.size()-1];
		return new UserFormatter().print(user, Locale.FRANCE);
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
    	String imageLocation 	= 	Play.application().configuration().getString("workshop.images.url") + File.separator;
    	String defaultImage 	= 	imageLocation + "default.png";
    	
    	// Gestion de la sauvegarde des fichiers uploadés (images)
 		MultipartFormData body = request().body().asMultipartFormData(); 
 		FilePart picture = body.getFile("image");
 		if (picture != null) {
 			String fileName = picture.getFilename();
 			File file = picture.getFile();

 			// On sauvegarde le fichier
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
	 * Upload la ressource spécifiée et retourne le lien vers cette ressource
	 * 
	 * @return le chemin vers la ressource qui a été uploadée dans le système
	 */
	private static String uploadRessources( Workshop workshop ) {
		SimpleDateFormat 	simpleDateFormat	=	new SimpleDateFormat("[yyyy-MM]");
		String				destFolderName		=	simpleDateFormat.format( workshop.getWorkshopSession().getNextPlay() ) + " - " + workshop.getSubject();
    	String 				ressourceLocation 	= 	Play.application().configuration().getString("workshop.ressources.url") + File.separator + destFolderName + File.separator;
    	
    	// Gestion de la sauvegarde des fichiers uploadés (images)
 		MultipartFormData 	body 				= 	request().body().asMultipartFormData(); 
 		FilePart 			ressource 			= 	body.getFile("workshopSupportFile");
 		
 		if (ressource != null) {
 			String 			fileName 			= 	ressource.getFilename();
 			File 			file 				= 	ressource.getFile();

 			// On sauvegarde le fichier
 				
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
	
	

}
