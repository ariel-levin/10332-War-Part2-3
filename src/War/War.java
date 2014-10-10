package War;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import Launchers.EnemyLauncher;
import Launchers.IronDome;
import Launchers.LauncherDestructor;
import Listeners.WarEventListener;
import Missiles.EnemyMissile;
import Utils.IdGenerator;

public class War extends Thread {
	private List<WarEventListener> allListeners;

	private ArrayList<IronDome> ironDomeArr = new ArrayList<IronDome>();
	private ArrayList<LauncherDestructor> launcherDestractorArr = new ArrayList<LauncherDestructor>();
	private ArrayList<EnemyLauncher> enemyLauncherArr = new ArrayList<EnemyLauncher>();
	private WarStatistics statistics;
	private String[] targetCities = { "Sderot", "Ofakim", "Beer-Sheva",
			"Netivot", "Tel-Aviv", "Re'ut" };

	
	public War() {
		allListeners = new LinkedList<WarEventListener>();
		statistics = new WarStatistics();

		registerListener(new WarLogger());
		WarLogger.addWarLoggerHandler("War");
	}

	public void run() {
		// throws event
		fireWarHasBeenStarted();

		// this thread will be alive until the war is over
		synchronized (this) {
			try {
				wait();

				stopAllMunitions();
				fireWarHasBeenFinished();
				sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}// synchronized

		// close all the handlers of the logger
		WarLogger.closeAllHandlers();
		WarLogger.closeWarHandler();
		//warHandler.close();
	}// run


	// this method stop all the munitions that are alive
	// in case the war is end
	// the method will wait until all munitions end there run
	private void stopAllMunitions() {
		for (EnemyLauncher el : enemyLauncherArr) {
			try {
				if (el.getCurrentMissile() != null) {
					el.getCurrentMissile().join();
				}
				
				el.stopRunning();
				el.interrupt();
				el.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (LauncherDestructor ld : launcherDestractorArr) {
			try {
				if (ld.getCurrentMissile() != null) {
					ld.getCurrentMissile().join();
				}
				
				ld.stopRunning();
				ld.interrupt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (IronDome ironDome : ironDomeArr) {
			try {
				if (ironDome.getCurrentMissile() != null) {
					ironDome.getCurrentMissile().join();
				}
				
				ironDome.stopRunning();
				ironDome.interrupt();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
	}// endAllmunitions

	// returns all missiles in air in current time
	public void getAllDuringFlyMissilesIds() {
		Vector<String> missileIds = new Vector<>();

		for (EnemyLauncher el : enemyLauncherArr) {
			if (el.getCurrentMissile() != null)
				missileIds.add(el.getCurrentMissile().getMissileId());
		}

		for (WarEventListener l : allListeners) {
			if (missileIds.size() == 0)
				l.sendMissileToIntercept(null);
			else
				l.sendMissileToIntercept(missileIds);
		}
	}

	// intercept given missile id
	public synchronized void interceptGivenMissile(String missileId) {
		IronDome ironDome = findFreeIronDome();

		if (ironDome == null) {
			fireNoSuchObject("Iron Dome");
		} else {
			interceptGivenMissile(missileId, ironDome);
		}
	}

	// intercept given missile id and iron dome id, Use for xml
	public synchronized void interceptGivenMissile(String ironDomeID,
			String missileId) {
		for (IronDome ironDome : ironDomeArr)
			if (ironDome.getIronDomeId().equals(ironDomeID)
					&& !ironDome.getIsBusy()) {
				interceptGivenMissile(missileId, ironDome);

				return;
			}

		fireNoSuchObject("Iron Dome");
	}

	//intercept given missile id and IronDome
	private void interceptGivenMissile(String missileId, IronDome ironDome) {
		EnemyMissile missileToDestroy;

		for (EnemyLauncher el : enemyLauncherArr) {
			missileToDestroy = el.getCurrentMissile();

			if (missileToDestroy != null
					&& missileToDestroy.getMissileId().equals(missileId)) {
				synchronized (ironDome) {
					ironDome.setMissileToDestroy(missileToDestroy);
					ironDome.notify();

					return;
				}// synchronized
			}// if
		}// for

		fireMissileNotExistEvent(ironDome.getIronDomeId(), missileId);
	}

	// finds free iron dome to use in war against enemy missiles
	public IronDome findFreeIronDome() {
		for (IronDome ironDome : ironDomeArr) {
			if (!ironDome.getIsBusy())
				return ironDome;
		}
	
		return null;
	}

	// intercept given missile id and launcher id, Use for xml
	public synchronized void interceptGivenLauncher(String destructorId,
			String launcherId) {
		for (LauncherDestructor ld : launcherDestractorArr)
			if (ld.getDestructorId().equals(destructorId) && !ld.getIsBusy()) {

				interceptGivenLauncher(launcherId, ld);

				// if found, not need to search more
				return;
			} else {
				switch (launcherId.charAt(0)) {
				case 'P':
					fireNoSuchObject("plane");
					break;

				case 'S':
					fireNoSuchObject("ship");
					break;
				}
			}

	}

	// intercept given launcher id
	public synchronized void interceptGivenLauncher(String launcherId) {
		LauncherDestructor ld = findFreeDestructor();
		
		if (ld == null) {
			switch (launcherId.charAt(0)) {
			case 'P':
				fireNoSuchObject("plane");
				break;

			case 'S':
				fireNoSuchObject("ship");
				break;
			}
		} else {
			interceptGivenLauncher(launcherId, ld);
		}
	}

	//intercept given missile id and launcher
	private void interceptGivenLauncher(String launcherId,
			LauncherDestructor destructor) {
		for (EnemyLauncher el : enemyLauncherArr) {
			
			if (el.getLauncherId().equals(launcherId) && el.isAlive()) {
				
				synchronized (destructor) {
					destructor.setEnemyLauncherToDestroy(el);
					destructor.notify();

					return;
				}
			}
		}

		fireMissileNotExistEvent(destructor.getDestructorId(), launcherId);
	}

	// finds free launcher destructor to use in war against enemy launchers
	public LauncherDestructor findFreeDestructor() {
		for (LauncherDestructor ld : launcherDestractorArr) {
			if (!ld.getIsBusy()){
				return ld;
			}
		}
		
		return null;
	}

	//returns vector of all visible launchers id's
	public void getAllVisibleLaunchersIds() {
		Vector<String> visibleIds = new Vector<>();
		
		for (EnemyLauncher el : enemyLauncherArr) {
			
			if (el.isAlive() && !el.getIsHidden()){
				visibleIds.add(el.getLauncherId());
			}
		}

		for (WarEventListener l : allListeners) {
			if (visibleIds.size() == 0)
				l.sendLauncherToIntercept(null);
			else
				l.sendLauncherToIntercept(visibleIds);
		}
	}

	//returns vector of all launchers id's
	public void getAllLaunchersIds() {
		Vector<String> launcherIds = new Vector<>();

		for (EnemyLauncher el : enemyLauncherArr) {
			if (el.isAlive()){
				launcherIds.add(el.getLauncherId());
			}
		}

		for (WarEventListener l : allListeners) {
			if (launcherIds.size() == 0)
				l.sendAllLaunchersID(null);
			else
				l.sendAllLaunchersID(launcherIds);
		}
	}

	//returns vector of all iron domes id's
	public void getAllIronDomesIds() {
		Vector<String> irondomeIds = new Vector<>();

		for (IronDome id : ironDomeArr) {
			if (id.isAlive()){
				irondomeIds.add(id.getIronDomeId());
			}
		}

		for (WarEventListener l : allListeners) {
			if (irondomeIds.size() == 0)
				l.sendAllIronDomesID(null);
			else
				l.sendAllIronDomesID(irondomeIds);
		}
	}
	
	//returns vector of all launcher destructors id's and types
	public void getAllLauncherDestructorsIdAndType() {
		Vector<String> destructorsDetails = new Vector<>();

		for (LauncherDestructor ld : launcherDestractorArr) {
			if (ld.isAlive()){
				destructorsDetails.add(ld.getDestructorId());
				destructorsDetails.add(ld.getDestructorType());
			}
		}

		for (WarEventListener l : allListeners) {
			if (destructorsDetails.size() == 0)
				l.sendAllLauncherDestructors(null);
			else
				l.sendAllLauncherDestructors(destructorsDetails);
		}
	}
	
	public void launchEnemyMissile(String launcherId, String destination,
			int damage, int flyTime) {
		for (EnemyLauncher el : enemyLauncherArr) {
			// Check if there is enemy launcher with given id
			if (el.getLauncherId().equals(launcherId) && el.isAlive()) {
				
				// Check if launcher is not in use
				if (el.getCurrentMissile() == null) {
					synchronized (el) {
						el.setMissileInfo(destination, damage, flyTime);
						el.notify();
					}//synchronized
					
				}//if
				
			}//if
		}//for
	}//method

	//add enemy launcher without given parameters
	public void addEnemyLauncher() {
		String id = IdGenerator.enemyLauncherIdGenerator();
		boolean isHidden = Math.random() < 0.5;

		addEnemyLauncher(id, isHidden);
	}

	//add enemy launcher with parameters
	public void addEnemyLauncher(String launcherId, boolean isHidden) {
		EnemyLauncher launcher = new EnemyLauncher(launcherId, isHidden, statistics);
		for (WarEventListener l : allListeners)
			launcher.registerListeners(l);
	
		launcher.start();
		enemyLauncherArr.add(launcher);
	
		for (WarEventListener l : allListeners)
			l.enemyLauncherAdded(launcher.getLauncherId());
	}

	//add iron dome without given parameters
	public void addIronDome() {
		String id = IdGenerator.ironDomeIdGenerator();
		addIronDome(id);
	}

	//add iron dome with given parameters
	public void addIronDome(String id) {
		IronDome ironDome = new IronDome(id, statistics);
		
		for (WarEventListener l : allListeners)
			ironDome.registerListeners(l);

		ironDome.start();
		
		ironDomeArr.add(ironDome);

		for (WarEventListener l : allListeners)
			l.ironDomeAdded(ironDome.getIronDomeId());
	}

	//add defense launcher destructor
	public void addDefenseLauncherDestructor(String type) {
		String id = IdGenerator.defenseLauncherDestractorIdGenerator(type.charAt(0));
		
		LauncherDestructor destructor = new LauncherDestructor(type, id, statistics);
		
		for (WarEventListener l : allListeners)
			destructor.registerListeners(l);

		destructor.start();
		
		launcherDestractorArr.add(destructor);

		for (WarEventListener l : allListeners)
			l.launcherDestructorAdded(destructor.getDestructorId(),
					destructor.getDestructorType());
	}

	public void registerListener(WarEventListener control) {
		for (IronDome iron : ironDomeArr)
			iron.registerListeners(control);
	
		for (LauncherDestructor launcherDestructor : launcherDestractorArr)
			launcherDestructor.registerListeners(control);
	
		for (EnemyLauncher EnemyLauncher : enemyLauncherArr)
			EnemyLauncher.registerListeners(control);
	
		allListeners.add(control);
	}

	// Event
	private void fireWarHasBeenFinished() {
		for (WarEventListener l : allListeners)
			l.warHasBeenFinished();
	}

	// Event
	private void fireWarHasBeenStarted() {
		for (WarEventListener l : allListeners)
			l.warHasBeenStarted();
	}

	// Event
	private void fireNoSuchObject(String type) {
		for (WarEventListener l : allListeners)
			l.noSuchObject(type);
	}

	// Event
	private void fireMissileNotExistEvent(String defenseLauncherId,
			String enemyId) {
		for (WarEventListener l : allListeners)
			l.missileNotExist(defenseLauncherId, enemyId);
	}

	public WarStatistics getStatistics() {
		return statistics;
	}

	public void getAllTargetCities() {
		for (WarEventListener l : allListeners)
			l.sendAllWarDestinations(this.targetCities);
	}

}

