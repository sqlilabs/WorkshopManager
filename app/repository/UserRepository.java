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
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructor
	 */
	public UserRepository() {
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
					.contains("firstName", splittedName[0])
					.contains("lastName", splittedName[ splittedName.length-1 ])
					.findUnique();
		
	}

}