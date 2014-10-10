package War;

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
	public void enemyLauncherAdded(String id) {
		view.enemyLauncherAdded(id);
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
	public void sendMissileToIntercept(Vector<String> missilesID) {
		view.getMissileToIntercept(missilesID);
	}
	
	@Override
	public void sendLauncherToIntercept(Vector<String> launchersID) {
		view.getLauncherToIntercept(launchersID);
	}
	
	@Override
	public void sendAllLaunchersID(Vector<String> launchersID) {
		view.getAllLaunchersID(launchersID);
	}
	
	@Override
	public void sendAllIronDomesID(Vector<String> ironDomesID) {
		view.getAllIronDomesID(ironDomesID);
	}
	
	@Override
	public void sendAllLauncherDestructors(Vector<String> destructorsDetails) {
		view.getAllLauncherDestructors(destructorsDetails);
	}
	
	@Override
	public void sendAllWarDestinations(String[] cities) {
		view.getAllWarDestinations(cities);
	}
	
	//Methods related to the model
	@Override
	public void finishWar() {
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

	public void reqMissileToIntercept() {
		warModel.getAllDuringFlyMissilesIds();
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
	
	public void reqLauncherToIntercept() {
		warModel.getAllVisibleLaunchersIds();
	}
	
	@Override
	public void reqAllLaunchersID() {
		warModel.getAllLaunchersIds();
	}
	
	public void reqAllIronDomesID() {
		warModel.getAllIronDomesIds();
	}
	
	public void reqAllLauncherDestructors() {
		warModel.getAllLauncherDestructorsIdAndType();
	}

	@Override
	public void addEnemyMissile(String launcherId, String destination, int damage, int flyTime) {
		warModel.launchEnemyMissile(launcherId, destination, damage, flyTime);
	}

	@Override
	public void addEnemyLauncher(String launcherId, boolean isHidden) {
		warModel.addEnemyLauncher(launcherId, isHidden);
	}
	
	@Override
	public void addEnemyLauncher() {
		warModel.addEnemyLauncher();
	}

	@Override
	public void addIronDome() {
		warModel.addIronDome();
	}
	
	@Override
	public void addIronDome(String id) {
		warModel.addIronDome(id);
	}

	@Override
	public void addDefenseLauncherDestructor(String type) {
		warModel.addDefenseLauncherDestructor(type);
	}

	@Override
	public void reqAllWarDestinations() {
		warModel.getAllTargetCities();
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

}

