package models;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import models.utils.compare.CommentByDateComparator;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * It represent an event in the app. Originally it was designed to handle workshops,
 * not for generic events, which explains the name !
 * 
 * @author cachavezley, ychartois
 * @see WorkshopSession
 */
@Entity
@Table(name = "WORKSHOP")
public class Workshop extends Model {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -6669398454928349805L;

	/**
	 * id
	 */
	@Id
    @GeneratedValue
    public Long id;

	/**
	 * the subject of the event
	 */
	@Required
	@Column(length=100)
	public String subject;
	
	/**
	 * A short summary of the event
	 */
	@Column(length=300)
	@Required
	public String summary;
	
	/**
	 * The event description
	 */
	@Column(length = 1000)
	public String description;
	
	/**
	 * the link to the image event
	 */
	public String image;
	
	/**
	 * The foreseen speaker
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="WORKSHOP_SPEAKERS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> speakers = new HashSet<User>();
	
	/**
	 * The session contains all the information of the planned event
	 */
	@OneToMany(mappedBy="workshop", cascade=CascadeType.ALL)
	public List<WorkshopSession> workshopSession = new ArrayList<WorkshopSession>();
	
	/**
	 * Who created the workshop
	 */
	@ManyToOne()
	public User author;
	
	/**
	 * When it has been created
	 */
	public Date creationDate;
	
	/**
	 * The users interested by this event
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="POTENTIAL_PARTICIPANTS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> potentialParticipants = new HashSet<User>();
	
	/**
	 * The event comments
	 */
	@OneToMany(mappedBy="workshop", cascade=CascadeType.ALL)
	public Set<Comment> comments = new HashSet<Comment>();
	
	/**
	 * Ressources of the workshop (file/link to the workshop support)
	 */
	@OneToOne(cascade=CascadeType.ALL)
	public Ressources workshopRessources;
	
	/**
	 * the Finder definition which allows to request the object in database
	 */
	public static Finder<Long, Workshop> find = new Finder<Long, Workshop>(Long.class, Workshop.class);

    public List<Comment> getCommentByDate() {
        List<Comment> sortedComments = new ArrayList( comments );
        Collections.sort(sortedComments, new CommentByDateComparator()) ;
        return sortedComments;
    }
	
	//<--------------------------------------------------------------------------->
	//-							 Constructor(s)
	//<--------------------------------------------------------------------------->
	/**
	 * Constructor
	 */
	public Workshop() {
		super();
	}

}
