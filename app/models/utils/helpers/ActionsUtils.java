package models.utils.helpers;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.Action;
import models.User;
import models.utils.enums.ActionEnum;
import play.db.ebean.Transactional;
import play.i18n.Messages;


/**
 * Helpers methods relative to Files
 * 
 * @author ychartois
 */
public class ActionsUtils {

	/**
	 * Action utilitaire permettant de logguer une novuelle action et la sauver en base.
	 * 
	 * @param actionCode
	 * @param user
	 * @param workshop
	 */
	@Transactional
	public static void logAction( ActionEnum actionCode, User user, String description, String parameter ) {
        Action 			action 	= 	new Action();
        action.title			=	Messages.get( actionCode.getMessage() );
        action.description		=	description;
        action.author			=	user;
        action.creationDate		=	new Date();
        
        action.save();
    }
	
	public static String getDay( Action action ) {
		SimpleDateFormat 	sdf	 	= 	new SimpleDateFormat("dd");
		return sdf.format(action.creationDate);
	}
	
	public static String getMonth( Action action ) {
		SimpleDateFormat 	sdf	 	= 	new SimpleDateFormat("MMM");
		return sdf.format(action.creationDate);
	}
	
	public static String getTime( Action action ) {
		SimpleDateFormat 	sdf	 	= 	new SimpleDateFormat("HH:mm");
		return sdf.format(action.creationDate);
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->		
	/**
	 * <constructor
	 */
	private ActionsUtils() {
		super();
	}

}
