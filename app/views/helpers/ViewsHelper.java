package views.helpers;

import controllers.Application;
import models.User;
import models.Workshop;
import models.WorkshopSession;
import models.utils.formatter.UserFormatter;
import models.utils.helpers.EventsUtils;
import play.i18n.Messages;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/22/13
 * Time: 9:53 PM
 * To change this template use File | Settings | File Templates.
 */
public final class ViewsHelper {

    /**
     * Help to build the title of a workshop in the already played view
     *
     * @param workshop  a workshop
     * @return the title we want to display
     */
    public static String buildAlreadyPlayedTitle( Workshop workshop ) {

        WorkshopSession mostRecentSession = EventsUtils.getMostRecentSession(workshop);

        if ( mostRecentSession.nextPlay.before( new Date() )) {
            return Messages.get("form.plan.workshop.last.date") + ": " + decorateDate(mostRecentSession.nextPlay, Application.conf("date.format.short"))
                    + " " + Messages.get("form.plan.workshop.last.speaker") + " " + mostRecentSession.speaker.firstName + " " + mostRecentSession.speaker.lastName;
        }
        else {
            WorkshopSession nextSession = EventsUtils.getNextPlannedSession( workshop );
            return Messages.get("form.plan.workshop.next.date") + ": " + decorateDate(nextSession.nextPlay, Application.conf("date.format.short"))
                    + " " + Messages.get("form.plan.workshop.last.speaker") + " " + nextSession.speaker.firstName + " " + nextSession.speaker.lastName;
        }

    }

    /**
     * Allows to format date
     *
     * @return the decorated date
     */
    public static String decorateDate(Date date, String format) {
        return date != null ? new SimpleDateFormat( format ).format(date) : Messages.get("unknown.date") ;
    }

    /**
     * Get the foreseen Speaker (i.e. user who volunteered as speaker )
     *
     * @param speakers the list of speakers
     * @return the full name of the foreseen User
     */
    public static String getForeseenSpeaker( Set<User> speakers ) {

        if ( speakers.isEmpty() ) {
            return "";
        }

        User[] speakerArray = new User[speakers.size()];
        speakerArray =  speakers.toArray( speakerArray );

        StringBuffer toReturn = new StringBuffer( new UserFormatter().print(speakerArray[0], Locale.FRANCE) );
        for ( int i = 1; i < speakerArray.length; i++ ) {
            toReturn.append( " " );
            toReturn.append( Messages.get("and.or") );
            toReturn.append( " " );
            toReturn.append( new UserFormatter().print( speakerArray[i], Locale.FRANCE) );
        }

        return toReturn.toString();
    }

    // <--------------------------------------------------------------------------->
    // - 							Constructor(s)
    // <--------------------------------------------------------------------------->
    /**
     * Constructor
     */
    private ViewsHelper() {
        super();
    }
}
