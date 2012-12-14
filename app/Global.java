
import models.User;
import models.utils.formatter.UserFormatter;

import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import play.Application;
import play.GlobalSettings;
import play.Play;
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
		/*
		 * Recréé la BDD avec les informations des mappings
		 * décrits dans hibernate.cfg.xml
		 */
		if ( Play.application().isProd() ) {
			Configuration cfg = new Configuration().configure();
			SchemaExport export = new SchemaExport(cfg);
			
			export.create(true,true);
		}
		
		// add a formater which takes you field and convert it to the proper object
    	// this will be called autmaticaly when you call bindFromRequest()
		Formatters.register(User.class, new UserFormatter());
	}

}
