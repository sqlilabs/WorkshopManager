/**
 * 
 */
package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;

/**
 * Un utilisateur dans l'application
 * 
 * @author cachavezley
 */
@Entity
@Table(name = "USER")
public class User extends Model {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7097349530301633288L;

	/**
	 * L'identifiant
	 */
	@Id
	@GeneratedValue
	public Long id;

	/**
	 * Le prénom.
	 */
	@Column(name = "first_name")
	public String firstName;
	
	/**
	 * Le nom de famille
	 */
	@Column(name = "last_name")
	public String lastName;
	
	/**
	 * Le mail.
	 */
	@Column(name = "email")
	public String email;
	
	/**
	 * the link to the profile picture
	 */
	@Column(name = "picture")
	public String picture;
	
	/**
	 * the role of the user
	 */
	@Column(name = "role", nullable=false )
	public String role;
	
	/**
	 * boolean to know if the user had accepted the charter
	 */
	@Column(name = "charterAgree")
	public boolean charterAgree;
	
	/**
	 * Définition d'un finder qui va permettre de faire les accès à la base
	 */
	public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->	
	/**
	 * Constructeur par défaut.
	 */
	public User() {
	}

	
	//<--------------------------------------------------------------------------->
	//-							Hashcode and equals        
	//<--------------------------------------------------------------------------->		
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		return true;
	}

}
