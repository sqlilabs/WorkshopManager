
import models.User;
import models.utils.formatter.UserFormatter;
import play.Application;
import play.GlobalSettings;
import play.data.format.Formatters;

/**
 * Allows to define Global method for the app
 * 
 * @author cachavezley
 */
public class Global extends GlobalSettings {

	/* (non-Javadoc)
	 * @see play.GlobalSettings#onStart(play.Application)
	 */
	@Override
	public void onStart(Application arg0) {	
		// add a formater which takes your field and convert it to the proper object
    	// this will be called automatically when you call bindFromRequest()
		Formatters.register(User.class, new UserFormatter());
	}

}
