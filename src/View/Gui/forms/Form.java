package View.Gui.forms;

import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Listeners.WarEventUIListener;


public class Form extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected List<WarEventUIListener> allListeners;
	

	public Form() {
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
	public void registerListener(WarEventUIListener listener) {
		allListeners.add(listener);
	}
	
}
