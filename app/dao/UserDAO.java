/**
 * 
 */
package dao;

import java.util.ArrayList;
import java.util.List;

import models.User;
import play.data.format.Formatters;

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
		List<User> 			list 	= 	User.find.where()
											.isNotNull("email")
											.orderBy("firstName asc")
											.findList();
				
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
	public static User getUserWithName( String completeName ) {
		String[] 			splittedName 	=	completeName.split(" ");
		
		return 	User.find.where()			
					.eq("firstName", splittedName[0])
					.eq("lastName", splittedName[1])
					.findUnique();
		
	}

}
