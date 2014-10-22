package database.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the destructors database table.
 * 
 */
@Embeddable
public class DestructorPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String destructorID;

	@Column(insertable=false, updatable=false)
	private String warName;

	public DestructorPK() {
	}
	public String getDestructorID() {
		return this.destructorID;
	}
	public void setDestructorID(String destructorID) {
		this.destructorID = destructorID;
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
		if (!(other instanceof DestructorPK)) {
			return false;
		}
		DestructorPK castOther = (DestructorPK)other;
		return 
			this.destructorID.equals(castOther.destructorID)
			&& this.warName.equals(castOther.warName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.destructorID.hashCode();
		hash = hash * prime + this.warName.hashCode();
		
		return hash;
	}
}