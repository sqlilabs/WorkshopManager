package models;

import com.avaje.ebean.annotation.EnumValue;

/**
 * The application roles allowed
 * ADMIN: is allowed to do anything
 * STANDARD: can't do admin task except if he is the author of the event
 * 
 * @author ychartois
 */
public enum Roles {

	@EnumValue("admin")
	ADMIN,
	
	@EnumValue("standard")
	STANDARD;
}
