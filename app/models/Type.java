package models;

import play.db.ebean.Model;

import javax.persistence.*;
import java.util.Date;

/**
 * This Object represent a comment on an event
 * 
 * @author ychartois
 *
 */
@Entity
@Table(name = "TYPE")
public class Type extends Model {

	@Id
	@GeneratedValue
	public Long id;

    @Column(name = "name")
    public String name;

    @Column(name = "color")
    public String color;

	/**
	 * the Finder definition which allows to request the object in database
	 */
	public static Finder<Long, Type> find = new Finder<Long, Type>(Long.class, Type.class);


	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->
	/**
	 * Constructor
	 */
	public Type() {
		super();
	}


    public Type(String name, String color) {
        super();
        this.name = name;
        this.color = color;
    }
}
