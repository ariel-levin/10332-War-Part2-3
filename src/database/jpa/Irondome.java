package database.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the irondomes database table.
 * 
 */
@Entity
@Table(name="irondomes")
@NamedQuery(name="Irondome.findAll", query="SELECT i FROM Irondome i")
public class Irondome implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private IrondomePK id;

	//bi-directional many-to-one association to War
	@ManyToOne
	@JoinColumn(name="warName")
	private War war;

	public Irondome() {
	}

	public IrondomePK getId() {
		return this.id;
	}

	public void setId(IrondomePK id) {
		this.id = id;
	}

	public War getWar() {
		return this.war;
	}

	public void setWar(War war) {
		this.war = war;
	}

}