/**
 * 
 */
package models.utils.formatter;

import java.text.ParseException;
import java.util.Locale;

import dao.UserDAO;

import play.data.format.Formatters.SimpleFormatter;
import models.User;

/**
 * @author ychartois
 *
 */
public class UserFormatter extends SimpleFormatter<User>{

	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructor
	 */
	public UserFormatter() {
		super();
	}

	
	//<--------------------------------------------------------------------------->
	//-						Methods of SimpleFormatter       
	//<--------------------------------------------------------------------------->	
	/* (non-Javadoc)
	 * @see play.data.format.Formatters.SimpleFormatter#parse(java.lang.String, java.util.Locale)
	 */
	@Override
	public User parse(String completeName, Locale loc) throws ParseException {
		return UserDAO.getUserWithName( completeName );
	}

	/* (non-Javadoc)
	 * @see play.data.format.Formatters.SimpleFormatter#print(java.lang.Object, java.util.Locale)
	 */
	@Override
	public String print(User user, Locale loc) {
		return user.firstName+ " " + user.lastName;
	}

}
