/**
 * 
 */
package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Un utilisateur dans l'application
 * 
 * @author cachavezley
 */
@Entity
@Table(name = "USER")
public class User implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7097349530301633288L;

	/**
	 * L'identifiant
	 */
	private Long id;

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
	 * @return the id
	 */
	@Id
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the firstName
	 */
	@Column(name = "first_name")
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
	@Column(name = "last_name")
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
