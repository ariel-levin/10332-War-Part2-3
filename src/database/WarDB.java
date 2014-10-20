package database;

import java.util.Calendar;

import listeners.WarEventListener;


public interface WarDB extends WarEventListener {

	boolean setWarName(String name);
	
	long getNumOfLaunchMissiles(Calendar startDate, Calendar endDate);
	
	long getNumOfInterceptMissiles(Calendar startDate, Calendar endDate);
	
	long getNumOfHitTargetMissiles(Calendar startDate, Calendar endDate);
	
	long getNumOfLaunchersDestroyed(Calendar startDate, Calendar endDate);
	
	long getTotalDamage(Calendar startDate, Calendar endDate);	
	
}
