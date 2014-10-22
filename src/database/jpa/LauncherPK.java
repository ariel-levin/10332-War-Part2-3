package database.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the launchers database table.
 * 
 */
@Embeddable
public class LauncherPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String launcherID;

	@Column(insertable=false, updatable=false)
	private String warName;

	public LauncherPK() {
	}
	public String getLauncherID() {
		return this.launcherID;
	}
	public void setLauncherID(String launcherID) {
		this.launcherID = launcherID;
	}
	public String getWarName() {
		return this.warName;
	}
	public void setWarName(String warName) {
		this.warName = warName;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LauncherPK)) {
			return false;
		}
		LauncherPK castOther = (LauncherPK)other;
		return 
			this.launcherID.equals(castOther.launcherID)
			&& this.warName.equals(castOther.warName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.launcherID.hashCode();
		hash = hash * prime + this.warName.hashCode();
		
		return hash;
	}
}