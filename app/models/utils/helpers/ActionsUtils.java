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
     * Allow to log and store a user action
     *
     * @param actionCode the action type
     * @param user the user responsible for the action
     * @param description the action description
     */
	@Transactional
	public static void logAction( ActionEnum actionCode, User user, String description ) {
        Action 			action 	= 	new Action();
        action.title			=	Messages.get( actionCode.getMessage() );
        action.description		=	description;
        action.author			=	user;
        action.creationDate		=	new Date();
        
        action.save();
    }

    /**
     * Help to format day of the action creation date
     *
     * @param action the logged action
     *
     * @return tha formatted date
     */
	public static String getDay( Action action ) {
		SimpleDateFormat 	sdf	 	= 	new SimpleDateFormat("dd");
		return sdf.format(action.creationDate);
	}

    /**
     * Help to format month of the action creation date
     *
     * @param action the logged action
     *
     * @return tha formatted date
     */
	public static String getMonth( Action action ) {
		SimpleDateFormat 	sdf	 	= 	new SimpleDateFormat("MMM");
		return sdf.format(action.creationDate);
	}

    /**
     * Help to format time of the action creation date
     *
     * @param action the logged action
     *
     * @return tha formatted date
     */
	public static String getTime( Action action ) {
		SimpleDateFormat 	sdf	 	= 	new SimpleDateFormat("HH:mm");
		return sdf.format(action.creationDate);
	}
	
	
	//<--------------------------------------------------------------------------->
	//-							 constructor(s)
	//<--------------------------------------------------------------------------->		
	/**
	 * constructor
	 */
	private ActionsUtils() {
		super();
	}

}
