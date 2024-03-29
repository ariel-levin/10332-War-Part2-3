package database.jpa;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;


/**
 * The persistent class for the destructions database table.
 * 
 */
@Entity
@Table(name="destructions")
@NamedQuery(name="Destruction.findAll", query="SELECT d FROM Destruction d")
public class Destruction implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="my_seq")
	@SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", allocationSize=1)
	private int dbID;

	private String destructorID;

	private String destructorType;

	private boolean isHit;

	private String targetID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	//bi-directional many-to-one association to War
	@ManyToOne
	@JoinColumn(name="warName")
	private War war;

	public Destruction() {
	}

	public int getDbID() {
		return this.dbID;
	}

	public void setDbID(int dbID) {
		this.dbID = dbID;
	}

	public String getDestructorID() {
		return this.destructorID;
	}

	public void setDestructorID(String destructorID) {
		this.destructorID = destructorID;
	}

	public String getDestructorType() {
		return this.destructorType;
	}

	public void setDestructorType(String destructorType) {
		this.destructorType = destructorType;
	}

	public boolean getIsHit() {
		return this.isHit;
	}

	public void setIsHit(boolean isHit) {
		this.isHit = isHit;
	}

	public String getTargetID() {
		return this.targetID;
	}

	public void setTargetID(String targetID) {
		this.targetID = targetID;
	}

	public Date getTime() {
		return this.time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public War getWar() {
		return this.war;
	}

	public void setWar(War war) {
		this.war = war;
	}

}