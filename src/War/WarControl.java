package War;

import java.util.Calendar;
import java.util.Vector;

import Listeners.WarEventListener;
import Listeners.WarEventUIListener;
import View.*;


public class WarControl implements WarEventListener, WarEventUIListener {
	
	private War warModel;
	private AbstractWarView view;
	
	
	public WarControl(War warModel, AbstractWarView view){
		this.warModel = warModel;
		this.view = view;
		
		warModel.registerListener(this);
		view.registerListener(this);
	}
	
	//Method that related to the view
	@Override
	public void defenseLaunchMissile(String myMunitionsId, String missileId, String enemyMissileId) {
		view.showDefenseLaunchMissile(myMunitionsId,missileId,enemyMissileId);
	}

	@Override
	public void defenseLaunchMissile(String myMunitionsId, String type,	String missileId, String enemyLauncherId) {
		view.showDefenseLaunchMissile(myMunitionsId, type, missileId, enemyLauncherId);
	}

	@Override
	public void enemyLaunchMissile(String myMunitionsId, String missileId, String destination, int damage) {
		view.showEnemyLaunchMissile(myMunitionsId, missileId, destination, damage);	
	}

	@Override
	public void enemyLauncherIsVisible(String id, boolean visible) {
		view.showLauncherIsVisible(id,visible);
	}

	@Override
	public void defenseMissInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId, int damage) {
		view.showMissInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
	}

	@Override
	public void defenseHitInterceptionMissile(String whoLaunchedMeId, String missileId, String enemyMissileId) {
		view.showHitInterceptionMissile(whoLaunchedMeId, missileId, enemyMissileId);
	}

	@Override
	public void enemyHitDestination(String whoLaunchedMeId, String missileId, String destination, int damage, String launchTime) {
		view.showEnemyHitDestination(whoLaunchedMeId, missileId, destination, damage);
	}

	@Override
	public void defenseMissInterceptionLauncher(String whoLaunchedMeId,	String type, String missileId, String enemyLauncherId) {
		view.showMissInterceptionLauncher(whoLaunchedMeId,type, enemyLauncherId, missileId);
	}
	
	@Override
	public void defenseMissInterceptionHiddenLauncher(String whoLaunchedMeId, String type, String enemyLauncherId) {
		view.showMissInterceptionHiddenLauncher(whoLaunchedMeId,type, enemyLauncherId);
	}
	
	@Override
	public void defenseHitInterceptionLauncher(String whoLaunchedMeId, String type, String missileId, String enemyLauncherId) {
		view.showHitInterceptionLauncher(whoLaunchedMeId, type, enemyLauncherId, missileId);
	}
	
	@Override
	public void enemyLauncherAdded(String id, boolean isHidden) {
		view.enemyLauncherAdded(id, isHidden);
	}
	
	@Override
	public void ironDomeAdded(String id) {
		view.ironDomeAdded(id);
	}
	
	@Override
	public void launcherDestructorAdded(String id, String type) {
		view.launcherDestructorAdded(id, type);
	}
	
	@Override
	public void warHasBeenFinished() {	
		if (view instanceof ConsoleView) {
			try {
				((ConsoleView)view).join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		view.showWarHasBeenFinished();
	}

	@Override
	public void warHasBeenStarted() {
		view.showWarHasBeenStarted();
	}

	@Override
	public void noSuchObject(String type) {
		view.showNoSuchObject(type);
	}

	@Override
	public void missileNotExist(String defenseLauncherId, String enemyId) {
		view.showMissileNotExist(defenseLauncherId, enemyId);
	}
	
	@Override
	public void enemyLauncherNotExist(String defenseLauncherId,
			String launcherId) {
		view.showLauncherNotExist(defenseLauncherId, launcherId);
	}

	@Override
	public void enemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		view.showEnemyMissDestination(whoLaunchedMeId, id, destination, launchTime);
	}
		
	
	
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////////////////////////

	
	
	// Methods related to the model
	@Override
	public void reqfinishWar() {
		WarXMLReader.stopAllThreads();
		//warModel.finishWar();
		
		//notify the war
		synchronized (warModel) {
			warModel.notify();
		}
	}

	@Override
	public void showStatistics() {
		WarStatistics statistics = warModel.getStatistics();
		view.showStatistics(statistics.statisticsToArray());	
	}
	
	@Override
	public void showStatisticsByDate(Calendar startDate, Calendar endDate) {
		
		
	}

	@Override
	public Vector<String> chooseMissileToIntercept() {
		Vector<String> ids = warModel.getAllDuringFlyMissilesIds();
		return ids;
	}

	@Override
	public void interceptGivenMissile(String ironDomeId, String missileId) {
		warModel.interceptGivenMissile(ironDomeId, missileId);
	}

	@Override
	public void interceptGivenMissile(String missileId) {
		warModel.interceptGivenMissile(missileId);
	}
	
	@Override
	public void interceptGivenLauncher(String launcherId) {
		warModel.interceptGivenLauncher(launcherId);
	}

	@Override
	public void interceptGivenLauncher(String destructorId, String launcherId) {
		warModel.interceptGivenLauncher(destructorId,launcherId);
	}
	
	@Override
	public Vector<String> chooseLauncherToIntercept() {
		Vector<String> ids = warModel.getAllVisibleLaunchersIds();
		return ids;
	}
	
	@Override
	public Vector<String> showAllLaunchers() {
		Vector<String> ids = warModel.getAllLaunchersIds();
		return ids;
	}
	
	public Vector<String> showAllIronDomes() {
		Vector<String> ids = warModel.getAllIronDomesIds();
		return ids;
	}
	
	public Vector<String> showAllLauncherDestructors() {
		Vector<String> details = warModel.getAllLauncherDestructorsIdAndType();
		return details;
	}

	@Override
	public void addEnemyMissile(String launcherId, String destination, int damage, int flyTime) {
		warModel.launchEnemyMissile(launcherId, destination, damage, flyTime);
	}

	@Override
	public String addEnemyLauncher(String launcherId, boolean isHidden) {
		String id = warModel.addEnemyLauncher(launcherId, isHidden);
		return id;
	}
	
	@Override
	public String addEnemyLauncher() {
		String id = warModel.addEnemyLauncher();
		return id;
	}

	@Override
	public String addIronDome() {
		String id = warModel.addIronDome();
		return id;
	}
	
	@Override
	public String addIronDome(String id) {
		String iId = warModel.addIronDome(id);
		return iId;
	}
	
	@Override
	public String addDefenseLauncherDestructor(String type) {
		String id = warModel.addDefenseLauncherDestructor(type);
		return id;
	}

	@Override
	public String[] getAllWarDestinations() {
		String[] warTargets = warModel.getAllTargetCities();
		return warTargets;
	}
	
	@Override
	public boolean isLauncherHidden(String launcherID) {
		boolean isHidden = warModel.isLauncherHidden(launcherID);
		return isHidden;
	}
	
	@Override
	public boolean isLauncherAliveAndVisible(String launcherID) {
		boolean isAliveAndVisible = warModel.isLauncherAliveAndVisible(launcherID);
		return isAliveAndVisible;
	}
	
	@Override
	public boolean isMissileOnAir(String missileID) {
		boolean isOnAir = warModel.isMissileOnAir(missileID);
		return isOnAir;
	}
	
}

