/**
 * 
 */
package repository;

import java.util.ArrayList;
import java.util.List;

import models.User;
import play.data.format.Formatters;

/**
 * @author ychartois
 *
 */
public class UserRepository {

	
	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructor
	 */
	public UserRepository() {
		super();
	}
	
	
	//<--------------------------------------------------------------------------->
	//-								Repository methods
	//<--------------------------------------------------------------------------->	
	/**
	 * @return the list of user (because all the user can be speakers)
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
		String[] 			splitName 	=	completeName.split(" ");
		
		return 	User.find.where()			
					.contains("firstName", splitName[0])
					.contains("lastName", splitName[ splitName.length-1 ])
					.findUnique();
		
	}

}
