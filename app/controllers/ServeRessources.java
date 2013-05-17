package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import play.mvc.Controller;
import play.mvc.Result;

public class ServeRessources extends Controller {

	
	// <--------------------------------------------------------------------------->
	// - 							Actions(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Action qui retourne l'image passé en paramètre de la route
	 * 
	 * @param filename le nom du fichier
	 * @return l'image
	 */
	public static Result at( String filename ) {
		
		byte[] file = null;

        try {
            file = IOUtils.toByteArray( new FileInputStream( new File("public/ressources/"+filename ) ) );
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
	// - 							Constructeur(s)
	// <--------------------------------------------------------------------------->
	/**
	 * Constructeur
	 */
	private ServeRessources() {
		super();
	}

}
