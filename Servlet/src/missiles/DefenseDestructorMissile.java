package missiles;

import launchers.EnemyLauncher;
import utils.Utils;
import utils.WarStatistics;


/** Missile for Plane or Ship **/
public class DefenseDestructorMissile extends Thread {

	private String id;
	private String whoLaunchedMeId;
	private String whoLaunchedMeType;
	private EnemyLauncher launcherToDestroy;
	private WarStatistics statistics;

	public DefenseDestructorMissile(String id, EnemyLauncher LauncherToDestroy,
			String whoLunchedMeId, String whoLaunchedMeType,
			WarStatistics statistics) {

		this.id = id;
		this.launcherToDestroy = LauncherToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.whoLaunchedMeType = whoLaunchedMeType;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (launcherToDestroy) {
			if (launcherToDestroy.isAlive() && !launcherToDestroy.getIsHidden()
					&& Utils.randomDestractorSucces()) {
				// Check if the launcher is hidden or not
				launcherToDestroy.interrupt();
			}
		}// synchronized
	
		if(launcherToDestroy.isInterrupted()){
			fireHitEvent();
			
		}else {
			fireMissEvent();
		}
	}

	// Event
	private void fireHitEvent() {
		System.out.println("[" + Utils.getCurrentTime() + "] "
				+ whoLaunchedMeType + ": " + whoLaunchedMeId
				+ " fired missile: " + id
				+ " and intercept succesfully the Launcher: "
				+ launcherToDestroy.getLauncherId());
		
		// update statistics
		statistics.increaseNumOfLauncherDestroyed();
	}
	
	public void fireMissEvent() {
		
		System.out.println("[" + Utils.getCurrentTime() + "] "
				+ whoLaunchedMeType + ": " + whoLaunchedMeId
				+ " fired missile: " + id + " but missed the Launcher: "
				+ launcherToDestroy.getLauncherId());
	}

	public String getMissileId() {
		return id;
	}

	public String getWhoLaunchedMeId() {
		return whoLaunchedMeId;
	}

	public String getWhoLaunchedMeType() {
		return whoLaunchedMeType;
	}
	
}
