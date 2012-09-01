package controllers;

import java.util.ArrayList;
import java.util.List;

import play.mvc.Controller;
import play.mvc.Result;
import views.html.welcome.welcome;

public class Application extends Controller {
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      
	
	// <--------------------------------------------------------------------------->
	// - 							Actions Methods
	// <--------------------------------------------------------------------------->	
	/**
	 * This method is the action that render the welcome page
	 * 
	 * @return Result the http response
	 */
	public static Result welcome() {	
		// We render the welcome page
		return ok(welcome.render("Your new application is ready.", getWorkshops()));
	}

	
	// <--------------------------------------------------------------------------->
	// - 							helper methods
	// <--------------------------------------------------------------------------->	
	public static List<String> getWorkshops() {
		List<String> wks = new ArrayList<String>();
		for (int i = 1; i <= 5; i++) {
			StringBuffer sb = new StringBuffer();
			sb.append("WorksShop_");
			sb.append(i);
			wks.add(sb.toString());
		}
		return wks;
	}

	
	// <--------------------------------------------------------------------------->
	// - 							Constructor(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Constructor by default
	 */
	public Application() {
		super();
	}

}