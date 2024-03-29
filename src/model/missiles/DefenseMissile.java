package model.missiles;

import java.util.LinkedList;
import java.util.List;

import utils.Utils;
import utils.WarStatistics;
import listeners.WarEventListener;


/** Missile for iron dome **/
public class DefenseMissile extends Thread {
	
	private List<WarEventListener> allListeners;

	private String id;
	private String whoLaunchedMeId;
	private EnemyMissile missileToDestroy;
	private WarStatistics statistics;

	
	public DefenseMissile(String id, EnemyMissile missileToDestroy,
			String whoLunchedMeId, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.missileToDestroy = missileToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (missileToDestroy) {
			// Check if the missile is still in the air before trying to destroy
//			if (missileToDestroy.isAlive() && Utils.randomSuccesRate()) {
			if (missileToDestroy.isOnAir() && Utils.randomSuccesRate()) {
//				missileToDestroy.interrupt();
				missileToDestroy.intercept();
			}
		}//synchronized
				
//		if (missileToDestroy.isInterrupted()) {
		if (missileToDestroy.isBeenHit()) {
			fireHitEvent();
		}
		else{
			fireMissEvent();
		}
	}// run

	// Event
	private void fireHitEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseHitInterceptionMissile(whoLaunchedMeId, id,
					missileToDestroy.getMissileId());
		}

		// update statistics
		statistics.increaseNumOfInterceptMissiles();
	}

	// Event
	private void fireMissEvent() {
		for (WarEventListener l : allListeners) {
			l.defenseMissInterceptionMissile(whoLaunchedMeId, id,
					missileToDestroy.getMissileId(),
					missileToDestroy.getDamage());
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

	public EnemyMissile getMissileToDestroy() {
		return missileToDestroy;
	}

}
