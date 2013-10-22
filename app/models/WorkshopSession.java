/**
 * 
 */
package models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


/**
 * This class represents a planned session of an event
 * 
 * @author cachavezley, ychartois
 *
 */
@Entity
@Table(name = "WORKSHOP_SESSION")
public class WorkshopSession extends Model {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 9112271812179769416L;

	/**
	 * id
	 */
	@Id
	@GeneratedValue
	public Long id;
	
	/**
	 * The event location
	 */
	@Required
	@Column(length=50)
	public String location;
	
	/**
	 * the limit of participants for this event session
	 */ 
	@Column(name = "limite_place",length=2)
	@NotNull
	public int limitePlace;
	
	/**
	 * the event nextPlay or lastPlay. If null the event has never been played
	 */
	@Required
	public Date nextPlay;	
	
	/**
	 * the event speaker
	 */
	@ManyToOne(cascade=CascadeType.PERSIST)
	public User speaker;

	/**
	 * the workshop related to this comment
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "workshop_id")
	public Workshop workshop;
	
	/**
	 * The participants of this session.
	 */
	@ManyToMany()
	@JoinTable(
			name="PARTICIPANTS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> participants = new HashSet<User>();
	
	/**
	 * the Finder definition which allows to request the object in database
	 */
	public static Finder<Long, WorkshopSession> find = new Finder<Long, WorkshopSession>(Long.class, WorkshopSession.class);
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->
	/**
	 * Constructor
	 */
	public WorkshopSession() {
	}
	
}
