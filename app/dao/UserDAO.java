/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.TypedQuery;

import models.User;
import play.data.format.Formatters;
import play.db.jpa.JPA;

/**
 * @author ychartois
 *
 */
public class UserDAO {

	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructor
	 */
	public UserDAO() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-								Méthodes DAO
	//<--------------------------------------------------------------------------->	
	/**
	 * @return la liste des users
	 */
	public static List<String> getSpeakers() {
		TypedQuery<User> 	query 	= 	JPA.em().createQuery("SELECT user FROM User user WHERE user.email IS NOT null", User.class);
		List<User> 			list 	= 	query.getResultList();
		List<String> 		result 	= 	new ArrayList<String>( list.size() );
		
		for ( User currentUser : list ) {
			result.add( Formatters.print( currentUser ) );
		}
		
		return result;
	}
	
	/**
	 * @param completeName the complete name composed as FirstName + LastName
	 * @return the selected user
	 */
	public static User getSpeakerWithName( String completeName ) {
		String[] 			splittedName 	=	completeName.split(" ");
		String 				queryStr		=	"SELECT user FROM User user WHERE user.firstName = '" + splittedName[0] + "'  AND user.lastName IS '" + splittedName[1] + "'";
		TypedQuery<User> 	query 			= 	JPA.em().createQuery(queryStr, User.class);
		
		User user = query.getSingleResult();
		
		return user;
	}

}
