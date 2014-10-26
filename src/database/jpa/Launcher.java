package database.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the launchers database table.
 * 
 */
@Entity
@Table(name="launchers")
@NamedQuery(name="Launcher.findAll", query="SELECT l FROM Launcher l")
public class Launcher implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LauncherPK id;

	private boolean isDestroyed;

	private boolean isHidden;

	//bi-directional many-to-one association to War
	@ManyToOne
	@JoinColumn(name="warName")
	private War war;

	public Launcher() {
	}

	public LauncherPK getId() {
		return this.id;
	}

	public void setId(LauncherPK id) {
		this.id = id;
	}

	public boolean getIsDestroyed() {
		return this.isDestroyed;
	}

	public void setIsDestroyed(boolean isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public boolean getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(boolean isHidden) {
		this.isHidden = isHidden;
	}

	public War getWar() {
		return this.war;
	}

	public void setWar(War war) {
		this.war = war;
	}

}