package models.utils.constants;

/**
 * Constant class for objects related to Workshop
 * 
 * @author ychartois
 *
 */
public class WorkShopConstants {

	
	//<--------------------------------------------------------------------------->
	//-							 	Constants	        
	//<--------------------------------------------------------------------------->
	/**
	 * Fake ID when the Object doesn't exist
	 */
	public static final long		ID_NOT_IN_TABLE		=	-1;
	
	/**
	 * DATE_PATTERN pattern to convert date to String
	 */
	public static final String 	    DATE_PATTERN 		= 	"dd/MM/yyyy";
	
	
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructor private because it's a constant class
	 */
	private WorkShopConstants() {
		super();
	}

}
