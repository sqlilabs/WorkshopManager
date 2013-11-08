package models.utils.helpers;

import controllers.Secured;
import models.Comment;
import models.User;
import models.Workshop;
import models.WorkshopSession;

/**
 * Created with IntelliJ IDEA.
 * @author ychartois
 * Date: 10/31/13
 * Time: 3:56 PM
 * To change this template use File | Settings | File Templates.
 */
public final class UsersUtils {

    //<--------------------------------------------------------------------------->
    //-							helper methods
    //<--------------------------------------------------------------------------->
    /**
     * Determine if the connected user is the speaker of the session
     *
     * @param session the workshop session
     *
     * @return true if the connected user is the speaker of the session
     */
    public static boolean isSessionSpeaker( WorkshopSession session ) {
        User user 	=	Secured.getUser();

        if ( user == null ) {
            return false;
        }

        return user.firstName.equals( session.speaker.firstName )
                && user.lastName.equals( session.speaker.lastName )
                && user.email.equals( session.speaker.email );
    }

    /**
     * Determine if the connected user was a speaker in a session of this Workshop
     *
     * @param workshop a workshop
     *
     * @return  true if the connected user was a speaker in a session of this Workshop
     */
    public static boolean isOneWorkshopSpeaker( Workshop workshop ) {
       for ( WorkshopSession currentSession : workshop.workshopSession ) {
           if ( isSessionSpeaker(currentSession) ) {
               return true;
           }
       }

       return false;
    }

    /**
     * Determine if the connected user is the author of the workshop
     *
     * @param workshop a workshop
     *
     * @return true if the connected user is the author of the workshop
     */
    public static boolean isWorkshopAuthor(Workshop workshop) {
        User	user 	=	Secured.getUser();

        if ( user == null || workshop == null || workshop.author == null) {
            return false;
        }

        return user.firstName.equals( workshop.author.firstName )
                && user.lastName.equals( workshop.author.lastName )
                && user.email.equals( workshop.author.email );
    }

    /**
     * Determine if the connected user is the author of the workshop
     *
     * @param comment a comment
     *
     * @return true if the connected user is the author of the workshop
     */
    public static boolean isCommentAuthor( Comment comment ) {
        User	user 	=	Secured.getUser();

        if ( user == null || comment == null || comment.author == null) {
            return false;
        }

        return user.firstName.equals( comment.author.firstName )
                && user.lastName.equals( comment.author.lastName )
                && user.email.equals( comment.author.email );
    }




    //<--------------------------------------------------------------------------->
    //-							 constructor(s)
    //<--------------------------------------------------------------------------->
    /**
     * constructor
     */
     private UsersUtils() {
         super();
     }


}
