
import models.User;
import models.utils.formatter.UserFormatter;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;

/**
 * Permet de définir les opérations globales à l'Application.
 * 
 * @author cachavezley
 */
public class Global extends GlobalSettings {

	/* (non-Javadoc)
	 * @see play.GlobalSettings#onStart(play.Application)
	 */
	@Override
	public void onStart(Application arg0) {	
		// add a formater which takes you field and convert it to the proper object
    	// this will be called autmaticaly when you call bindFromRequest()
		Formatters.register(User.class, new UserFormatter());
	}

}
