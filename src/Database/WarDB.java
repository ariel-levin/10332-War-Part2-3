package Database;

import Listeners.WarEventListener;


public interface WarDB extends WarEventListener {

	boolean setWarName(String name);
	
}
