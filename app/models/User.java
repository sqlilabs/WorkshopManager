/**
 * 
 */
package models;

/**
 * Un utilisateur dans l'application
 * 
 * @author cachavezley
 */
public class User {
	
	/**
	 * Le prénom.
	 */
	private String firstName;
	
	/**
	 * Le nom de famille
	 */
	private String lastName;
	
	/**
	 * Le mail.
	 */
	private String email;
	
	/**
	 * Constructeur par défaut.
	 */
	public User() {
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

}
