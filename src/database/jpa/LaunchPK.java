package database.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the launches database table.
 * 
 */
@Embeddable
public class LaunchPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(insertable=false, updatable=false)
	private String warName;

	private String missileID;

	public LaunchPK() {
	}
	public String getWarName() {
		return this.warName;
	}
	public void setWarName(String warName) {
		this.warName = warName;
	}
	public String getMissileID() {
		return this.missileID;
	}
	public void setMissileID(String missileID) {
		this.missileID = missileID;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LaunchPK)) {
			return false;
		}
		LaunchPK castOther = (LaunchPK)other;
		return 
			this.warName.equals(castOther.warName)
			&& this.missileID.equals(castOther.missileID);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.warName.hashCode();
		hash = hash * prime + this.missileID.hashCode();
		
		return hash;
	}
}