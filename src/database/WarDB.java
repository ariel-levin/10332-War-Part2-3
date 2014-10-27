package database;

import java.util.Calendar;
import listeners.WarEventListener;

import model.War;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public interface WarDB extends WarEventListener {

	void setWar(War war);
	
	boolean setWarName(String name);
	
	long getNumOfLaunchMissiles(Calendar startDate, Calendar endDate);
	
	long getNumOfInterceptMissiles(Calendar startDate, Calendar endDate);
	
	long getNumOfHitTargetMissiles(Calendar startDate, Calendar endDate);
	
	long getNumOfLaunchersDestroyed(Calendar startDate, Calendar endDate);
	
	long getTotalDamage(Calendar startDate, Calendar endDate);	
	
}
