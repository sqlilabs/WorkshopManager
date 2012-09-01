import org.hibernate.cfg.Configuration;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.junit.Test;


public class DataBaseSetUpTest {

	@Test
	public void test() {
		/*
		 * Recréé la BDD avec les informations des mappings
		 * décrits dans hibernate.cfg.xml
		 */
		Configuration cfg = new Configuration().configure();
		SchemaExport export = new SchemaExport(cfg);
		
		export.create(true,true);
	}

}
