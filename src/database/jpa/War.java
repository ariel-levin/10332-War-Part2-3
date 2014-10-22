package database.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the wars database table.
 * 
 */
@Entity
@Table(name="wars")
@NamedQuery(name="War.findAll", query="SELECT w FROM War w")
public class War implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String warName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date endTime;

	@Temporal(TemporalType.TIMESTAMP)
	private Date startTime;

	//bi-directional many-to-one association to Destruction
	@OneToMany(mappedBy="war")
	private List<Destruction> destructions;

	//bi-directional many-to-one association to Destructor
	@OneToMany(mappedBy="war")
	private List<Destructor> destructors;

	//bi-directional many-to-one association to Interception
	@OneToMany(mappedBy="war")
	private List<Interception> interceptions;

	//bi-directional many-to-one association to Irondome
	@OneToMany(mappedBy="war")
	private List<Irondome> irondomes;

	//bi-directional many-to-one association to Launcher
	@OneToMany(mappedBy="war")
	private List<Launcher> launchers;

	//bi-directional many-to-one association to Launch
	@OneToMany(mappedBy="war")
	private List<Launch> launches;

	public War() {
	}

	public String getWarName() {
		return this.warName;
	}

	public void setWarName(String warName) {
		this.warName = warName;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public List<Destruction> getDestructions() {
		return this.destructions;
	}

	public void setDestructions(List<Destruction> destructions) {
		this.destructions = destructions;
	}

	public Destruction addDestruction(Destruction destruction) {
		getDestructions().add(destruction);
		destruction.setWar(this);

		return destruction;
	}

	public Destruction removeDestruction(Destruction destruction) {
		getDestructions().remove(destruction);
		destruction.setWar(null);

		return destruction;
	}

	public List<Destructor> getDestructors() {
		return this.destructors;
	}

	public void setDestructors(List<Destructor> destructors) {
		this.destructors = destructors;
	}

	public Destructor addDestructor(Destructor destructor) {
		getDestructors().add(destructor);
		destructor.setWar(this);

		return destructor;
	}

	public Destructor removeDestructor(Destructor destructor) {
		getDestructors().remove(destructor);
		destructor.setWar(null);

		return destructor;
	}

	public List<Interception> getInterceptions() {
		return this.interceptions;
	}

	public void setInterceptions(List<Interception> interceptions) {
		this.interceptions = interceptions;
	}

	public Interception addInterception(Interception interception) {
		getInterceptions().add(interception);
		interception.setWar(this);

		return interception;
	}

	public Interception removeInterception(Interception interception) {
		getInterceptions().remove(interception);
		interception.setWar(null);

		return interception;
	}

	public List<Irondome> getIrondomes() {
		return this.irondomes;
	}

	public void setIrondomes(List<Irondome> irondomes) {
		this.irondomes = irondomes;
	}

	public Irondome addIrondome(Irondome irondome) {
		getIrondomes().add(irondome);
		irondome.setWar(this);

		return irondome;
	}

	public Irondome removeIrondome(Irondome irondome) {
		getIrondomes().remove(irondome);
		irondome.setWar(null);

		return irondome;
	}

	public List<Launcher> getLaunchers() {
		return this.launchers;
	}

	public void setLaunchers(List<Launcher> launchers) {
		this.launchers = launchers;
	}

	public Launcher addLauncher(Launcher launcher) {
		getLaunchers().add(launcher);
		launcher.setWar(this);

		return launcher;
	}

	public Launcher removeLauncher(Launcher launcher) {
		getLaunchers().remove(launcher);
		launcher.setWar(null);

		return launcher;
	}

	public List<Launch> getLaunches() {
		return this.launches;
	}

	public void setLaunches(List<Launch> launches) {
		this.launches = launches;
	}

	public Launch addLaunch(Launch launch) {
		getLaunches().add(launch);
		launch.setWar(this);

		return launch;
	}

	public Launch removeLaunch(Launch launch) {
		getLaunches().remove(launch);
		launch.setWar(null);

		return launch;
	}

}