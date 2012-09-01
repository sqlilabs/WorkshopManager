import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;

import play.Application;
import play.GlobalSettings;

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
		Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true,true);
	}

}
