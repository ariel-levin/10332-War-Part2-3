package View;

import Listeners.WarEventUIListener;

public interface AbstractWarView {

	void registerListeners(WarEventUIListener listener);

	void showDefenseLaunchMissile(String MunitionsId, String missileId,
			String enemyMissileId);

	void showDefenseLaunchMissile(String MunitionsId, String type,
			String missileId, String enemyLauncherId);

	void showEnemyLaunchMissile(String MunitionsId, String missileId,
			String destination, int damage);

	void showLauncherIsVisible(String id, boolean visible);

	void showMissInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId);

	void showHitInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId);

	void showEnemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage);

	void showMissInterceptionLauncher(String whoLaunchedMeId, String type,
			String enemyLauncherId, String missileId);

	void showMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId);

	void showHitInterceptionLauncher(String whoLaunchedMeId, String type,
			String enemyLauncherId, String missileId);

	void showStatistics(long[] array);

	void showWarHasBeenFinished();

	void showWarHasBeenStarted();

	void showNoSuchObject(String type);

	void showMissileNotExist(String defenseLauncherId, String enemyId);

	void showLauncherNotExist(String defenseLauncherId, String launcherId);

	void showEnemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime);
	
	
//	 void addPassengerToUI(int id, String name);
//	 void removePassengerFromUI(int id);

}

