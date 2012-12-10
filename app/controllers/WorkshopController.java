/**
 * 
 */
package controllers;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.TypedQuery;

import models.Workshop;
import models.WorkshopSession;
import play.api.templates.Html;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Http.Context;
import views.html.welcome.welcome;
import views.html.workshops.addWorkshop;
import views.html.workshops.planWorkshop;

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
    final static Form<Workshop> workshopForm = form(Workshop.class);
    
    /**
     * Defines a form wrapping the WorkShopSession class.
     */ 
    final static Form<WorkshopSession> workshopSessionForm = form(WorkshopSession.class);
	
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
     */ 
    public static Result blankWorkshop() {
        return ok( addWorkshop.render(workshopForm) );
    }
    
    /**
     * Handle the 'new workshop form' submission 
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
		
		Html html = welcome.render("Workshop Manager", getWorkshops());
        
        return ok(html);
    }
	
	/**
	 * @param id l'identifiant du workshop
	 * @return
	 */
	@Transactional
	public static Result deleteWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		JPA.em().remove(ws);
		
		Html html = welcome.render("Workshop Manager", getWorkshops());
		
		return ok(html);
	}
	
	@Transactional
	public static Result planWorkshop(Long id) {
		return ok( planWorkshop.render( workshopSessionForm, id ) );
	}
	
    @Transactional
    public static Result saveWorkshopSession( Long id ) {
    	Form<WorkshopSession> 	filledForm 		= 	workshopSessionForm.bindFromRequest();
    	
        if (filledForm.hasErrors()) {
            return badRequest( planWorkshop.render(workshopSessionForm, id) );
        }
        
        // We get the Workshop
        Workshop workshopToPlan = JPA.em().find(Workshop.class, id);
        
        
		// We set the WorkshopSession to the Workshop to Plan
        WorkshopSession workshopSession = filledForm.get();
        workshopToPlan.setWorkshopSession( workshopSession );
        
        // Sauver l'objet en base
        JPA.em().persist( workshopSession );
		JPA.em().persist( workshopToPlan );
		
        return ok( welcome.render("Workshop Manager", getWorkshops()) );
    }

    
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->	
    /*
	 * TODO Remplacer les helpers par un DAO
     */
	/**
	 * @return la liste des workshops non joué
	 */
	public static List<Workshop> getWorkshops() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws WHERE ws.workshopSession IS null", Workshop.class);
		List<Workshop> list = query.getResultList();
		return list;
	}
	
	/**
	 * @return la liste des workshops planifiés
	 */
	public static List<Workshop> getWorkshopsPlanifie() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws WHERE ws.workshopSession.nextPlay IS NOT null", Workshop.class);
		List<Workshop> list = query.getResultList();
		return list;
	}
	
	/**
	 * @return la liste des workshops déjà présentés
	 */
	public static List<Workshop> getWorkshopsAlreadyPlayed() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws WHERE ws.workshopSession.lastPlay IS NOT null", Workshop.class);
		List<Workshop> list = query.getResultList();
		return list;
	}
	
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
