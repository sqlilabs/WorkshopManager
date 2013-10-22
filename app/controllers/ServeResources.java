package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * This controller allows to serve files resources as event slides
 *
 * @author ychartois
 */
public class ServeResources extends Controller {

	
	// <--------------------------------------------------------------------------->
	// - 							Actions(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Action which return the file given as route parameter
	 * 
	 * @param filename the file name
     *
	 * @return the file given as route parameter
	 */
	public static Result at( String filename ) {
		
		byte[] file = null;

        try {
            file = IOUtils.toByteArray( new FileInputStream( new File("public/resources/"+filename ) ) );
        } 
        catch (FileNotFoundException e) {
            return notFound();
        } 
        catch (IOException e) {
        	return notFound();          
        }
        
        if ( filename.endsWith("pdf")) {
        	response().setContentType("application/pdf");
        }
        else {
        	response().setContentType("application");
        }

        return ok(file);
    }   
	
	// <--------------------------------------------------------------------------->
	// - 							Constructor(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Constructor
	 */
	private ServeResources() {
		super();
	}

}
