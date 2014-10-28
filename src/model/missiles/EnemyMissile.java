package model.missiles;

import java.util.LinkedList;
import java.util.List;

import utils.Utils;
import utils.WarStatistics;
import listeners.WarEventListener;
import model.launchers.EnemyLauncher;


/** Enemy missile, is been created by the Enemy launcher **/
public class EnemyMissile extends Thread {
	
	private List<WarEventListener> allListeners;

	private String id;
//	private String whoLaunchedMeId;
	private EnemyLauncher whoLaunchedMeId;
	private String destination;
	private int flyTime;
	private int damage;
	private WarStatistics statistics;
	private String launchTime;
	private boolean beenHit = false;
	private boolean onAir = false;

	
	public EnemyMissile(String id, String destination, int flyTime, int damage,
			EnemyLauncher whoLaunchedMeId, WarStatistics statistics) {
		allListeners = new LinkedList<WarEventListener>();

		this.id = id;
		this.destination = destination;
		this.flyTime = flyTime;
		this.damage = damage;
		this.whoLaunchedMeId = whoLaunchedMeId;
		this.statistics = statistics;
	}

	public void run() {
		launchTime = Utils.getCurrentTime();

		try {
			onAir = true;
			// fly time
			sleep(flyTime * Utils.SECOND);
			onAir = false;

			// Interrupt is thrown when Enemy missile has been hit.
		} catch (InterruptedException ex) {
			// this event was already being thrown by the missile (defense) who
			// hit this missile.
			
//			synchronized (this) {
//				beenHit = true;
//			}
			
		} finally {
			synchronized (this) {
				if (!beenHit) {
					if (Utils.randomSuccesRate()) {
						fireHitEvent();
					} else {
						fireMissEvent();
					}
				}
			}
			
			// release the enemy launcher
			if (whoLaunchedMeId.isOccupied()) {
				try {
					synchronized (whoLaunchedMeId) {
						whoLaunchedMeId.notify();
					}
				} catch (IllegalMonitorStateException e) {}
			}
			
		}// finally
	}// run

	// Event
	private void fireHitEvent() {
		for (WarEventListener l : allListeners) {
			l.enemyHitDestination(whoLaunchedMeId.getLauncherId(), id,
					destination, damage, launchTime);
		}

		// update the war statistics
		statistics.increaseNumOfHitTargetMissiles();
		statistics.increaseTotalDamage(damage);
	}

	// Event
	private void fireMissEvent() {
		for (WarEventListener l : allListeners) {
			l.enemyMissDestination(whoLaunchedMeId.getLauncherId(), id,
					destination, launchTime);
		}
	}

	// Event
	public void registerListeners(WarEventListener listener) {
		allListeners.add(listener);
	}
	
	public void intercept() {
		beenHit = true;
		try {
			interrupt();
		} catch (Exception e) {}
	}
	
	public String getMissileId() {
		return id;
	}

	public int getDamage() {
		return damage;
	}
	
	public boolean isBeenHit(){
		return beenHit;
	}

	public boolean isOnAir() {
		return onAir;
	}

	public String getWhoLaunchedMeId() {
		return whoLaunchedMeId.getLauncherId();
	}

	public String getDestination() {
		return destination;
	}

	public String getLaunchTime() {
		return launchTime;
	}

}
