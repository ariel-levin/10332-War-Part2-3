package database.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the interceptions database table.
 * 
 */
@Entity
@Table(name="interceptions")
@NamedQuery(name="Interception.findAll", query="SELECT i FROM Interception i")
public class Interception implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="my_seq")
	@SequenceGenerator(name="my_seq",sequenceName="MY_SEQ", allocationSize=1)
	private int dbID;

	private String irondomeID;

	private byte isHit;

	private String targetID;

	@Temporal(TemporalType.TIMESTAMP)
	private Date time;

	//bi-directional many-to-one association to War
	@ManyToOne
	@JoinColumn(name="warName")
	private War war;

	public Interception() {
	}

	public int getDbID() {
		return this.dbID;
	}

	public void setDbID(int dbID) {
		this.dbID = dbID;
	}

	public String getIrondomeID() {
		return this.irondomeID;
	}

	public void setIrondomeID(String irondomeID) {
		this.irondomeID = irondomeID;
	}

	public byte getIsHit() {
		return this.isHit;
	}

	public void setIsHit(byte isHit) {
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