package model.missiles;

import java.util.LinkedList;
import java.util.List;

import utils.Utils;
import utils.WarStatistics;
import listeners.WarEventListener;
import model.launchers.EnemyLauncher;


/** Missile for Plane or Ship **/
public class DefenseDestructorMissile extends Thread {
	
	private List<WarEventListener> allListeners;

	private String id;
	private String whoLaunchedMeId;
	private String whoLaunchedMeType;
	private EnemyLauncher launcherToDestroy;
	private WarStatistics statistics;

	
	public DefenseDestructorMissile(String id, EnemyLauncher LauncherToDestroy,
			String whoLunchedMeId, String whoLaunchedMeType,
			WarStatistics statistics) {

		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.launcherToDestroy = LauncherToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.whoLaunchedMeType = whoLaunchedMeType;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (launcherToDestroy) {
//			if (launcherToDestroy.isAlive() && !launcherToDestroy.getIsHidden()
			if (!launcherToDestroy.isBeenHit() && !launcherToDestroy.getIsHidden()
					&& Utils.randomDestructorSuccess()) {
				// Check if the launcher is hidden or not
//				launcherToDestroy.interrupt();
				launcherToDestroy.destroyLauncher();
			}
		}// synchronized
	
		if(launcherToDestroy.isBeenHit()){
			fireHitEvent();
			
		}else {
			fireMissEvent();
		}
	}

	// Event
	private void fireHitEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionLauncher(whoLaunchedMeId,
					whoLaunchedMeType, id, launcherToDestroy.getLauncherId());
		}

		// update statistics
		statistics.increaseNumOfLauncherDestroyed();
	}

	// Event
	private void fireMissEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionLauncher(whoLaunchedMeId,
					whoLaunchedMeType, id, launcherToDestroy.getLauncherId());
		}
	}

	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
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

	public EnemyLauncher getLauncherToDestroy() {
		return launcherToDestroy;
	}

}
