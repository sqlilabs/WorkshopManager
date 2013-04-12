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

import com.avaje.ebean.Ebean;

import models.Comment;
import models.Ressources;
import models.User;
import models.Workshop;
import models.Workshop.WorkshopStatus;
import models.WorkshopSession;
import models.utils.formatter.UserFormatter;
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
		return ok(addWorkshop.render(play.data.Form.form(Workshop.class), ID_NOT_IN_TABLE));
	}

	/**
	 * Handle the 'new workshop form' submission
	 * 
	 * @return the welcome page or the addWorkshop page if the validation has
	 *         errors
	 */
	@Transactional
	public static Result saveWorkshop(Long id) {
		Form<Workshop> workshopForm = play.data.Form.form(Workshop.class).bindFromRequest();

		if (workshopForm.hasErrors()) {
			return badRequest(addWorkshop.render(workshopForm, id));
		}

		// On récupère le workshop depuis le formulaire
		Workshop 		workshopNew 		= 	workshopForm.get();
		
		// Et le workshop depuis la base si c'est une modification
		if ( id != ID_NOT_IN_TABLE ) {
			Workshop 	ws 					= 	Workshop.find.byId(id);
			// on met à jour la nouvelle instance avec les anciennes données
			workshopNew.speakers				=	ws.speakers;
			workshopNew.potentialParticipants	=	ws.potentialParticipants ;
			workshopNew.author 					=	ws.author;
			workshopNew.creationDate			=	ws.creationDate;
		}
		else {
			// On affecte l'auteur connecté
			workshopNew.author 				=	AuthentificationController.getUser() ;
			// et la date de création
			workshopNew.creationDate		= 	new Date();
			workshopNew.status				=	WorkshopStatus.NEW.getStatus();
		}
        
		// On set l'image du workshop
		workshopNew.image					=	uploadImage();

		if (id == ID_NOT_IN_TABLE) {
			Ebean.save(workshopNew);
		} else {
			workshopNew.id					=	id;
			Ebean.update(workshopNew);
		}

		return redirect(routes.Application.newWorkshops() + "#" + id);
	}

	/**
	 * @param id
	 *            id du workshop
	 * @return la page permettant de modifier le workshop
	 */
	@Transactional
	public static Result modifyWorkshop(Long id) {
		Workshop ws = Workshop.find.byId(id);
		Form<Workshop> workshopForm = play.data.Form.form(Workshop.class).fill(ws);

		return ok( addWorkshop.render(workshopForm, id) );
	}

	/**
	 * @param id
	 *            l'identifiant du workshop
	 * @return the welcome page
	 */
	@Transactional
	public static Result deleteWorkshop(Long id) {
		Workshop ws = Workshop.find.byId(id);
		Ebean.delete(ws);

		for (String key : request().headers().keySet()) {
			System.out.println(key);
		}

		return redirect( routes.Application.welcome() );
	}

	/**
	 * @param id
	 *            l'identifiant du workshop
	 * @return the planWorkshop page
	 */
	@Transactional
	public static Result planWorkshop(Long id) {
		Form<WorkshopSession> workshopSessionForm = play.data.Form.form(WorkshopSession.class);

		return ok(planWorkshop.render(workshopSessionForm, id, -1l));
	}
	
	/**
	 * @param id
	 *            l'identifiant du workshop
	 * @return the planWorkshop page
	 */
	@Transactional
	public static Result modifyPlannedWorkshop(Long idWorkshop, Long idSession) {
		Workshop 			ws 			= Workshop.find.byId( idWorkshop );
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
	 * @param id
	 *            l'identifiant du workshop
	 * @return the welcome page or the planWorkshop page if the validation has
	 *         errors
	 */
	@Transactional
	public static Result saveWorkshopSession(Long idWorkshop, Long idSession) {
		Form<WorkshopSession> filledForm = play.data.Form.form(WorkshopSession.class).bindFromRequest();

		if (filledForm.hasErrors()) {
			return badRequest(planWorkshop.render(filledForm, idWorkshop, idSession));
		}

		// We get the Workshop
		Workshop workshopToPlan 	= 	Workshop.find.byId(idWorkshop);
		boolean newSession 			= 	idSession == -1;
		
		if ( !newSession ) {
			workshopToPlan.workshopSession.remove( WorkshopSession.find.byId( idSession ) );
		}		

		// We set the WorkshopSession to the Workshop to Plan
		WorkshopSession workshopSession = filledForm.get();
		if ( !newSession ) {
			workshopSession.id		=	idSession ;
		}
		workshopToPlan.workshopSession.add(workshopSession);
		workshopToPlan.status		=	WorkshopStatus.PLANNED.getStatus();
		
		// We empty the potentialParticipants List
		workshopToPlan.potentialParticipants	= 	new HashSet<User>();

		// Sauver l'objet en base 
		if (!newSession) {
			Ebean.update(workshopSession);
		} else {
			Ebean.save(workshopSession);
		}
		Ebean.save(workshopToPlan);

		return redirect( routes.Application.welcome() + "#" + idWorkshop );
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
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        if ( currentWorkshop.speakers.size() < Play.application().configuration().getInt( "speaker.limit" )) {
        	// Get the connected User
            User		user				=	AuthentificationController.getUser();
            
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
     * Allow to remove a proposal Speaker to the speaker List for a selected workshop
     * 
     * @param id workshop id
     * @return the welcome page
     */
    @Transactional
    public static Result removeSpeaker( Long id ) {
    	// We get the Workshop
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.speakers.remove( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.newWorkshops() + "#" + id);
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
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.potentialParticipants.add( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.welcome() + "#" + id);
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
        Workshop	currentWorkshop 	= 	Workshop.find.byId(id);
        
        // Get the connected User
        User		user				=	AuthentificationController.getUser();
        
        // It's a Set, so no duplicate
        currentWorkshop.potentialParticipants.remove( user );
        
        // We save the new Workshop
        Ebean.save(currentWorkshop);
    	
        return redirect(routes.Application.welcome() + "#" + id);
    }
    

	/**
	 * Prepare the form to add comments
	 * 
	 * @param id the workshop ID
	 * @return the comment form view
	 */
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
    	comment.author				=	AuthentificationController.getUser();
    	comment.workshop			=	ws;
    	
    	// We add the new comment
    	ws.comments.add( comment );
    	
    	// We save the objects in base
    	Ebean.save( comment );
        Ebean.update( ws );
    	
        return redirect( routes.Application.workshops() + "#" + id);
    }
    
    
    /**
     * Prepare the form to add ressources
     * 
	 * @param id the workshop ID
	 * @return the ressources form view
	 */
    @Transactional
    public static Result addWorkshopRessources(Long id) {
    	Workshop 	ws 			= 	Workshop.find.byId(id);
    	Ressources 	ressources	=	ws.workshopRessources;
    	
    	// if we already set ressources, we want to fill the form with our old datas
    	Form<Ressources> ressourcesForm = null;
    	if ( ressources != null ) {
    		ressourcesForm 		= 	play.data.Form.form(Ressources.class).fill(ressources);
    	}
    	else {
    		ressourcesForm 		= 	play.data.Form.form(Ressources.class);
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
    	Form<Ressources> 	filledForm 	= 	play.data.Form.form(Ressources.class).bindFromRequest();
    	
    	if (filledForm.hasErrors()) {
			return badRequest(addRessources.render(filledForm, id));
		}
    	
    	// Get the workshop from base
    	Workshop 		ws 				= 	Workshop.find.byId(id);
    	
    	boolean			update			=	 ws.workshopRessources != null;
    	
    	//We create the Ressources instance
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
    	
        return redirect( routes.Application.workshops() + "#" + id );
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
		return date != null ? new SimpleDateFormat(DATE_PATTERN).format(date) : Messages.get("unknow.date") ;
	}
	
	/**
	 * Permet de découper la description du workshop courte
	 * 
	 * @param workshop un workshop
	 * @return la description du workshop tronquée
	 */
	public static String getWorkshopDescription( Workshop workshop ) {
		int maxlength = Play.application().configuration().getInt( "detail.workshop.main.view" );

		return ( workshop.description.length() > maxlength ) 
				? workshop.description.substring(0, maxlength) + "..." 
				:  workshop.description ;
	}
	
	/**
	 * Permet de découper la description du workshop complète
	 * 
	 * @param workshop un workshop
	 * @return la description du workshop tronquée
	 */
	public static String getFullWorkshopDescription( Workshop workshop ) {
		return workshop.description ;
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
		String				destFolderName		=	simpleDateFormat.format( workshop.workshopSession.get(0).nextPlay ) + " - " + workshop.subject;
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
