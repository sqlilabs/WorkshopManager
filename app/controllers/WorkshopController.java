/**
 * 
 */
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.TypedQuery;

import models.User;
import models.Workshop;
import models.WorkshopSession;
import models.utils.formatter.UserFormatter;
import play.api.templates.Html;
import play.data.Form;
import play.data.format.Formatters;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import scala.annotation.target.param;
import views.html.welcome.welcome;
import views.html.workshops.addWorkshop;
import views.html.workshops.planWorkshop;
import dao.UserDAO;
import dao.WorkshopDAO;

/**
 * Ce controller regroupe toutes les actions qui sont liées à l'ajout d'un nouveau Workshop
 * 
 * @author ychartois
 * @version 0.1
 */
public class WorkshopController extends Controller {
	
	/**
     * Defines a form wrapping the Workshop class.
     */ 
    private static Form<Workshop> workshopForm = form(Workshop.class);
    
    /**
     * Defines a form wrapping the WorkShopSession class.
     */ 
    private static Form<WorkshopSession> workshopSessionForm ;
	
	/**
	 * DATE_PATTERN pattern to convert date to String
	 */
	private static final String DATE_PATTERN = "dd/MM/yyyy";
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par defaut
	 */
	public WorkshopController() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							 Actions(s)	        
	//<--------------------------------------------------------------------------->	
    /**
     * Display a blank form for Workshop.
     * @return the workshopForm empty
     */ 
    public static Result blankWorkshop() {
        return ok( addWorkshop.render(workshopForm) );
    }
    
    /**
     * Handle the 'new workshop form' submission
     * @return the welcome page or the addWorkshop page if the validation has errors
     */
    @Transactional
    public static Result saveWorkshop() {
    	Form<Workshop> filledForm = workshopForm.bindFromRequest();
    	
        if (filledForm.hasErrors()) {
            return badRequest(addWorkshop.render(workshopForm));
        }
        
        //Sauver l'objet en base
        Workshop workshop = filledForm.get();
		JPA.em().persist(workshop);
		
		Html html = welcome.render("Workshop Manager", WorkshopDAO.getWorkshops());
        
        return ok(html);
    }
	
    /**
     * @param id id du workshop
     * @return la page a afficher suivant l'action qui a été déclenchée
     */
    @Transactional
    public static Result actionWorkshop( Long id ) {
		if ( request().body().asFormUrlEncoded().get("Planifier") != null ) {
			return planWorkshop(id);
		}
		else if ( request().body().asFormUrlEncoded().get("Supprimer") != null ) {
			return deleteWorkshop(id);
		}
		else if ( request().body().asFormUrlEncoded().get("Modifier") != null ) {
			return modifyWorkshop(id);
		}
		else {
			return status(NOT_IMPLEMENTED);
		}
    }
    
    /**
     * @param id id du workshop
     * @return la page permettant de modifier le workshop
     */
    private static Result modifyWorkshop( Long id ) {
    	Workshop ws = JPA.em().find(Workshop.class, id);
		
		if ( ws != null ) {
			workshopForm = form(Workshop.class).fill( ws );
		}
    	
    	return ok( addWorkshop.render(workshopForm) );
    }
    
	/**
	 * @param id l'identifiant du workshop
	 * @return the welcome page
	 */
	@Transactional
	public static Result deleteWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		JPA.em().remove(ws);
		
		for (String key : request().headers().keySet() ) {
			System.out.println( key );
		}
		
		Html html = welcome.render("Workshop Manager", WorkshopDAO.getWorkshops());
		
		return ok(html);
	}
	
	/**
	 * @param id l'identifiant du workshop
	 * @return the planWorkshop page
	 */
	@Transactional
	public static Result planWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		
		if ( ws.getWorkshopSession() != null ) {
			workshopSessionForm = form(WorkshopSession.class).fill( ws.getWorkshopSession() );
		}
		else {
			workshopSessionForm	= form(WorkshopSession.class);
		}
		
		return ok( planWorkshop.render( workshopSessionForm, id ) );
	}
	
    /**
     * @param id l'identifiant du workshop
     * @return the welcome page or the planWorkshop page if the validation has errors
     */
    @Transactional
    public static Result saveWorkshopSession( Long id ) {
    	
    	Form<WorkshopSession> 	filledForm 		= 	workshopSessionForm.bindFromRequest();
    	
        if (filledForm.hasErrors()) {
            return badRequest( planWorkshop.render(workshopSessionForm, id) );
        }
        
        // We get the Workshop
        Workshop workshopToPlan = JPA.em().find(Workshop.class, id);
        boolean newSession 		= workshopToPlan.getWorkshopSession() == null;
        
        
		// We set the WorkshopSession to the Workshop to Plan
        WorkshopSession workshopSession = filledForm.get();
        workshopToPlan.setWorkshopSession( workshopSession );
        
        // Sauver l'objet en base
        if ( !newSession ) {
        	JPA.em().merge( workshopSession );
        }
        else {
        	JPA.em().persist( workshopSession );
        }
		JPA.em().persist( workshopToPlan );
		
        return ok( welcome.render("Workshop Manager", WorkshopDAO.getWorkshops()) );
    }

    
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->	
	/**
	 * @return la liste des Workshops planifiés qui a été placé dans le context
	 */
	public static List<Workshop> getWorkshopsPlanifieFromContext() {
		List<Workshop> listWsPlanifie = (List<Workshop>) Http.Context.current().args.get("wsPlanifie");
		return listWsPlanifie != null ? listWsPlanifie : new ArrayList<Workshop>();
	}
	
	/**
	 * @return la date décorée
	 */
	public static String decorateDate( Date date ) {
		return new SimpleDateFormat(DATE_PATTERN).format(date);
	}
	
}
