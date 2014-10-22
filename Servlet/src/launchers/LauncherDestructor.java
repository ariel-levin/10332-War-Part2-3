package launchers;

import missiles.DefenseDestructorMissile;
import utils.IdGenerator;
import utils.Utils;
import utils.WarStatistics;


/** Plane or Ship **/
public class LauncherDestructor extends Thread implements Munitions{

	private String id;
	private String type; // plane or ship
	private boolean isRunning = true;
	private boolean isBusy = false;
	private EnemyLauncher toDestroy;
	private WarStatistics statistics;
	private DefenseDestructorMissile currentMissile;

	
	public LauncherDestructor(String type, String id, WarStatistics statistics) {

		this.id = id;
		this.type = Utils.capitalize(type);
		this.statistics = statistics;

	}

	public void run() {

		while (isRunning) {
			synchronized (this) {
				try {
					// Wait until user try to destroy missile
					wait();
				} catch (InterruptedException ex) {
					// used for end the war
					stopRunning();
					break;
				}
			}// synchronized
			// with this boolean you can see if the launcher is available to use
			isBusy = true;

			// checking if the missile you would like to destroy is alive (as a thread)
			// is not null (if there is any missile) and if he isn't hidden
			try{
				if (toDestroy != null && toDestroy.isAlive() && !toDestroy.getIsHidden()) {
					launchMissile();
					
				} else {
					if (toDestroy != null) {
						fireLauncherIsHiddenEvent(toDestroy.getLauncherId());
					}
				}
			} catch (InterruptedException e) {
				//e.printStackTrace();
				stopRunning();
				break;
			}
			
			// update that the launcher is not in use
			isBusy = false;
			
			//update that there is no missile to this launcher
			currentMissile = null;
		}// while
		
	}// run

	
	// set the next target of this launcher destructor, called from the war
	public void setEnemyLauncherToDestroy(EnemyLauncher toDestroy) {
		this.toDestroy = toDestroy;
	}

	public void launchMissile() throws InterruptedException {
		// create launcher destructor missile
		createMissile();

		// sleep for launch time;
		sleep(Utils.LAUNCH_DURATION);

		if (toDestroy != null && toDestroy.isAlive() && !toDestroy.getIsHidden()) {
			// Throw event
			fireLaunchMissileEvent(currentMissile.getMissileId());

			// Start missile and wait until he will finish to be able
			// to shoot anther one
			currentMissile.start();
			currentMissile.join();
		}
		else{
			if (toDestroy.getIsHidden()){
				fireLauncherIsHiddenEvent(toDestroy.getLauncherId());
			}
			else{
				fireLauncherNotExist(toDestroy.getLauncherId());
			}
		}
	}



	public void createMissile() {
		// generate missile id
		String MissileId = IdGenerator.defenseDestractorLauncherMissileIdGenerator(type.charAt(0));

		// create new missile
		currentMissile = new DefenseDestructorMissile(MissileId, toDestroy, id,
				type, statistics);

		// register all listeners
//		for (WarEventListener l : allListeners)
//			currentMissile.registerListeners(l);
	}

	public DefenseDestructorMissile getCurrentMissile() {
		return currentMissile;
	}
	
	// Event
	private void fireLaunchMissileEvent(String missileId) {
		System.out.println("[" + Utils.getCurrentTime() + "] " + type + ": "
				+ id + " just launched missile: " + missileId
				+ " towards launcher: " + toDestroy.getLauncherId());
	}
	
	// Event
	private void fireLauncherIsHiddenEvent(String launcherId) {
		System.out.println("[" + Utils.getCurrentTime() + "] " + type + ": "
				+ id + " missed the Launcher: " + launcherId
				+ " because he is hidden");
	}
	
	// Event
	private void fireLauncherNotExist(String launcherId) {
		System.out.println("[" + Utils.getCurrentTime() + "] ERROR: "
				+ id + " tried to intercept, " + "but missed: "
				+ launcherId + " doesn't exist!");	
	}

	// check if can shoot from this current launcher destructor
	public boolean getIsBusy() {
		return isBusy;
	}

	public String getDestructorId() {
		return id;
	}

	@Override
	// use for end the thread
	public void stopRunning() {
		toDestroy = null;
		isRunning = false;
	}
	
}

