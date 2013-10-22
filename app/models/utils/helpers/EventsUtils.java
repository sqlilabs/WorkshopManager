package models.utils.helpers;

import models.Workshop;
import models.WorkshopSession;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: ychartois
 * Date: 10/22/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class EventsUtils {

    /**
     * Return the most recent session from the session list
     *
     * @param workshop the workshop to test
     * @return the most recent session
     */
    public static WorkshopSession getMostRecentSession( Workshop workshop ) {

        WorkshopSession session	=	workshop.workshopSession.get(0);

        if ( workshop.workshopSession.size() == 1 )
            return session;

        for ( int i = 1; i < workshop.workshopSession.size(); i++ ) {
            if ( session.nextPlay.before( workshop.workshopSession.get(i).nextPlay ) ) {
                session	=	workshop.workshopSession.get(i);
            }
        }

        return session;
    }

    /**
     * Get the next session planned for an event
     *
     * @param workshop the workshop to test
     * @return the next session planned
     */
    public static WorkshopSession getNextPlannedSession( Workshop workshop ) {

        Date today       =   new Date();

        if ( workshop.workshopSession.size() == 0 )
            return null;

        WorkshopSession nextSession	= getMostRecentSession( workshop );
        for ( WorkshopSession session : workshop.workshopSession ) {
            if ( session.nextPlay.after(today) && session.nextPlay.before( nextSession.nextPlay ) ) {
                nextSession	=	session;
            }
        }

        return nextSession.nextPlay.after(today) ? nextSession : null;
    }
}
