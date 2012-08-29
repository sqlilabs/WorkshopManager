package controllers;

import java.util.List;

import javax.persistence.TypedQuery;

import models.Workshop;
import play.*;
import play.api.templates.Html;
import play.data.Form;
import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
	
	static Form<Workshop> workshopForm = form(Workshop.class);

	/**
	 * La page par défaut
	 * @return un redirect vers la liste de workshops
	 */
	public static Result index() {
		return redirect(routes.Application.list());
	}
	
	/**
	 * Affiche la liste des workshops
	 * @return
	 */
	@Transactional(readOnly = true)
	public static Result list() {
		TypedQuery<Workshop> query = JPA.em().createQuery("SELECT ws FROM Workshop ws", Workshop.class);
		List<Workshop> list = query.getResultList();
		return ok(
				views.html.index.render(
						list, 
						workshopForm));
	}
	
	/**
	 * @return
	 */
	@Transactional
	public static Result newWorkshop() {
		Form<Workshop> filledForm = workshopForm.bindFromRequest();
		
		if(filledForm.hasErrors()) {
			List<Workshop> list = JPA.em().createQuery("SELECT ws FROM Workshop ws", Workshop.class).getResultList();
			Html html = views.html.index.render(list , filledForm);
			return badRequest(html);
			
		} else {
			Workshop workshop = filledForm.get();
			JPA.em().persist(workshop);
			
			List<Workshop> list = JPA.em().createQuery("SELECT ws FROM Workshop ws", Workshop.class).getResultList();
			Html html = views.html.index.render(list , filledForm);
			
			return ok(html);
		}
	}
	
	/**
	 * @param id l'identifiant du workshop
	 * @return
	 */
	@Transactional
	public static Result deleteWorkshop(Long id) {
		Workshop ws = JPA.em().find(Workshop.class, id);
		JPA.em().remove(ws);
		
		List<Workshop> list = JPA.em().createQuery("SELECT ws FROM Workshop ws", Workshop.class).getResultList();
		Html html = views.html.index.render(list , workshopForm);
		
		return ok(html);
	}
	

}
