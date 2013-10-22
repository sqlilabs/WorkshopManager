package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;

import play.mvc.Controller;
import play.mvc.Result;


/**
 * This controller allows to serve uploaded images resources as the image of the event
 *
 * @author ychartois
 */
public class ServeImage extends Controller {

	
	// <--------------------------------------------------------------------------->
	// - 							Actions(s)
	// <--------------------------------------------------------------------------->
	/**
     * Action which return the image given as route parameter
	 * 
	 * @param filename the file name
     *
	 * @return the image given as route parameter
	 */
	public static Result at( String filename ) {
        response().setContentType("image");     
        byte[] file = null;

        try {
            file = IOUtils.toByteArray( new FileInputStream( new File("public/images/workshops/"+filename ) ) );
        } 
        catch (FileNotFoundException e) {
            return notFound();
        } 
        catch (IOException e) {
        	return notFound();          
        }

        return ok(file);
    }   
	
	// <--------------------------------------------------------------------------->
	// - 							Constructor(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Constructor
	 */
	private ServeImage() {
		super();
	}

}
