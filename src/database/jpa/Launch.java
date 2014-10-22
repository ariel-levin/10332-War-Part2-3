package database.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the launches database table.
 * 
 */
@Entity
@Table(name="launches")
@NamedQuery(name="Launch.findAll", query="SELECT l FROM Launch l")
public class Launch implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LaunchPK id;

	private int damage;

	private String destination;

	private byte isHit;

	private byte isIntercepted;

	private String launcherID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	private String whoIntercepted;

	//bi-directional many-to-one association to War
	@ManyToOne
	@JoinColumn(name="warName")
	private War war;

	public Launch() {
	}

	public LaunchPK getId() {
		return this.id;
	}

	public void setId(LaunchPK id) {
		this.id = id;
	}

	public int getDamage() {
		return this.damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public String getDestination() {
		return this.destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public byte getIsHit() {
		return this.isHit;
	}

	public void setIsHit(byte isHit) {
		this.isHit = isHit;
	}

	public byte getIsIntercepted() {
		return this.isIntercepted;
	}

	public void setIsIntercepted(byte isIntercepted) {
		this.isIntercepted = isIntercepted;
	}

	public String getLauncherID() {
		return this.launcherID;
	}

	public void setLauncherID(String launcherID) {
		this.launcherID = launcherID;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getWhoIntercepted() {
		return this.whoIntercepted;
	}

	public void setWhoIntercepted(String whoIntercepted) {
		this.whoIntercepted = whoIntercepted;
	}

	public War getWar() {
		return this.war;
	}

	public void setWar(War war) {
		this.war = war;
	}

}