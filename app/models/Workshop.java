package models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

/**
 * Le Java Bean contenant les informations génériques du Workshop.
 * 
 * @author cachavezley
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
	 * L'identifiant
	 */
	@Id
    @GeneratedValue
    public Long id;

	/**
	 * Le sujet du workshop.
	 */
	@Required
	@Column(length=100)
	public String subject;
	
	/**
	 * Résumé court du workshop
	 */
	@Column(length=300)
	@Required
	public String summary;
	
	/**
	 * La description du contenu du workshop.
	 */
	@Column(length = 1000)
	public String description;
	
	/**
	 * L'url de l'image à utiliser.
	 */
	public String image;
	
	/**
	 * Les speakers proposé du workshop
	 */
	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(
			name="WORKSHOP_SPEAKERS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> speakers = new HashSet<User>();
	
	/**
	 * La WorkshopSession contient les informations relatives à la planification du Workshop
	 */
	@OneToMany(mappedBy="workshop", cascade=CascadeType.ALL)
	public List<WorkshopSession> workshopSession = new ArrayList<WorkshopSession>();
	
	/**
	 * Who created the workshop
	 */
	@ManyToOne()
	public User author;
	
	/**
	 * When it as created
	 */
	public Date creationDate;
	
	/**
	 * Les personnes intéressées par le workshop
	 */
	@ManyToMany()
	@JoinTable(
			name="POTENTIAL_PARTICIPANTS",
			joinColumns = @JoinColumn(name="workshop_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	public Set<User> potentialParticipants = new HashSet<User>();
	
	/**
	 * Les commentaires du workshop
	 */
	@OneToMany(mappedBy="workshop", cascade=CascadeType.ALL)
	public Set<Comment> comments = new HashSet<Comment>();
	
	/**
	 * Ressources of the workshop (file/link to the workshop support)
	 */
	@OneToOne(cascade=CascadeType.ALL)
	public Ressources workshopRessources;
	
	/**
	 * Définition d'un finder qui va permettre de faire les accès à la base
	 */
	public static Finder<Long, Workshop> find = new Finder<Long, Workshop>(Long.class, Workshop.class);
	
	
	//<--------------------------------------------------------------------------->
	//-							 Constructeur(s)	        
	//<--------------------------------------------------------------------------->
	/**
	 * Constructeur par défaut
	 */
	public Workshop() {
		super();
	}

}
