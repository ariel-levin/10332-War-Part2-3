package Utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Utils {
	public static final int SECOND = 1000;
	public static final int FLY_TIME = 3;
	public static final int LAUNCH_DURATION = 1000;
	private static final double DESTRACOTR_SUCCES_RATE = 0.95;
	private static final double SUCCES_RATE = 0.7;
	private static final double IS_HIDDEN_RATE = 0.5;
	private static final String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";
	
	// create local date
	public static String getCurrentTime() {
		LocalDateTime ldt = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
		
		return ldt.format(dtf);
	}

	// delete all old logs in the folder
	public static void cleanLogFolder() {
		File file = new File("log");
		String[] myFiles;
		if(file.isDirectory()){  
			myFiles = file.list();  
			for (int i=0; i<myFiles.length; i++) {  
				File myFile = new File(file, myFiles[i]);   
				if (!myFile.canWrite()) {
					javax.swing.JOptionPane.showMessageDialog(null,
									"Some old log files are locked and can't be deleted.\n" +
									"Please restart Eclipse and try to launch the program again.",
									"ERROR", javax.swing.JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
				myFile.delete();
			}  
		}
		
		// if log directory does not exist, create it
		java.io.File dir = new java.io.File("log");
		if (!dir.exists()) {
			try{
				dir.mkdir();
			} catch(SecurityException se){}
		}
	}

	/** randomize the success rate of missile **/
	public static boolean randomSuccesRate() {
		return Math.random() < SUCCES_RATE;
	}

	/** randomize the success rate of destructor **/
	public static boolean randomDestractorSucces() {
		return Math.random() < DESTRACOTR_SUCCES_RATE;
	}

	/** randomize if launcher will be hidden or not **/
	public static boolean randomIsHidden() {
		return Math.random() < IS_HIDDEN_RATE;
	}
	
	/** Capitalize any given string **/
	public static String capitalize(String s) {
        if (s.length() == 0){
        	return s;
        }
        
        return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
    }
}
