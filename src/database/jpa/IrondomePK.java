package database.jpa;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the irondomes database table.
 * 
 */
@Embeddable
public class IrondomePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String irondomeID;

	@Column(insertable=false, updatable=false)
	private String warName;

	public IrondomePK() {
	}
	public String getIrondomeID() {
		return this.irondomeID;
	}
	public void setIrondomeID(String irondomeID) {
		this.irondomeID = irondomeID;
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
		if (!(other instanceof IrondomePK)) {
			return false;
		}
		IrondomePK castOther = (IrondomePK)other;
		return 
			this.irondomeID.equals(castOther.irondomeID)
			&& this.warName.equals(castOther.warName);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.irondomeID.hashCode();
		hash = hash * prime + this.warName.hashCode();
		
		return hash;
	}
}