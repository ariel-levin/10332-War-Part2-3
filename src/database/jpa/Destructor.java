package database.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the destructors database table.
 * 
 */
@Entity
@Table(name="destructors")
@NamedQuery(name="Destructor.findAll", query="SELECT d FROM Destructor d")
public class Destructor implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DestructorPK id;

	private String type;

	//bi-directional many-to-one association to War
	@ManyToOne
	@JoinColumn(name="warName")
	private War war;

	public Destructor() {
	}

	public DestructorPK getId() {
		return this.id;
	}

	public void setId(DestructorPK id) {
		this.id = id;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public War getWar() {
		return this.war;
	}

	public void setWar(War war) {
		this.war = war;
	}

}