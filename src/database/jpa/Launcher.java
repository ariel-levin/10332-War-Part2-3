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

	private byte isDestroyed;

	private byte isHidden;

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

	public byte getIsDestroyed() {
		return this.isDestroyed;
	}

	public void setIsDestroyed(byte isDestroyed) {
		this.isDestroyed = isDestroyed;
	}

	public byte getIsHidden() {
		return this.isHidden;
	}

	public void setIsHidden(byte isHidden) {
		this.isHidden = isHidden;
	}

	public War getWar() {
		return this.war;
	}

	public void setWar(War war) {
		this.war = war;
	}

}