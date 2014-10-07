package Listeners;

import java.util.Vector;

public interface WarEventUIListener {
	/** Stop the war, truce and show war statistics **/
	public void finishWar();

	/** Show current war statistics **/
	public void showStatistics();

	/** Ask for current missiles in air **/
	public Vector<String> chooseMissileToIntercept();

	/** Will try to intercept given missile id **/
	public void interceptGivenMissile(String id);

	/** Used for xml **/
	void interceptGivenMissile(String ironDomeId, String missileId);

	/** Ask for current launcher that are not hidden **/
	public Vector<String> chooseLauncherToIntercept();

	/** Will try to intercept given launcher id **/
	public void interceptGivenLauncher(String id);

	/** Used for xml **/
	void interceptGivenLauncher(String destructorId, String launcherId);

	/** User will select from which launcher he would like to launch missile **/
	public Vector<String> showAllLaunchers();
	
	/** Get all the Iron Domes Id's in a Vector **/
	public Vector<String> showAllIronDomes();
	
	/** Get all the Launcher Destructors Id's and Type in a Vector **/
	public Vector<String> showAllLauncherDestructors();

	/** Add missile to given launcher **/
	public void addEnemyMissile(String launcherId, String destination,
			int damage, int flyTime);

	/** Add enemy launcher **/
	public void addEnemyLauncher();

	/** Add enemy launcher from xml **/
	public void addEnemyLauncher(String launcherId, boolean isHidden);

	/** Add defense Iron Dome **/
	public void addIronDome();

	/** Add plane or ship **/
	public void addDefenseLauncherDestructor(String type);

	/** Add defense Iron Dome from xml **/
	public void addIronDome(String id);

	/** Returns all war city targets **/
	public String[] getAllWarDestinations();

}
