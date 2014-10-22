package missiles;

import utils.Utils;
import utils.WarStatistics;


/** Missile for iron dome **/
public class DefenseMissile extends Thread {

	private String id;
	private String whoLaunchedMeId;
	private EnemyMissile missileToDestroy;
	private WarStatistics statistics;

	
	public DefenseMissile(String id, EnemyMissile missileToDestroy,
			String whoLunchedMeId, WarStatistics statistics) {

		this.id = id;
		this.missileToDestroy = missileToDestroy;
		this.whoLaunchedMeId = whoLunchedMeId;
		this.statistics = statistics;
	}

	public void run() {
		synchronized (missileToDestroy) {
			// Check if the missile is still in the air before trying to destroy
			if (missileToDestroy.isAlive() && Utils.randomSuccesRate()) {
				missileToDestroy.interrupt();
			}
		}//synchronized
				
		if (missileToDestroy.isInterrupted()){
			fireHitEvent();
		}
		else{
			fireMissEvent();
		}
	}// run

	// Event
	private void fireHitEvent() {
		System.out.println("[" + Utils.getCurrentTime() + "] Iron Dome: "
				+ whoLaunchedMeId + " fired missile: " + id
				+ " and intercept succesfully the missile: "
				+ missileToDestroy.getMissileId());
		
		// update statistics
		statistics.increaseNumOfInterceptMissiles();
	}
	
	public void fireMissEvent() {
		System.out.println("[" + Utils.getCurrentTime() + "] Iron Dome: "
				+ whoLaunchedMeId + " fired missile: " + id
				+ " but missed the missile: " + missileToDestroy.getMissileId());
	}

	public String getMissileId() {
		return id;
	}

	public String getWhoLunchedMeId() {
		return whoLaunchedMeId;
	}

}
