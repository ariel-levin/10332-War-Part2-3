package View.Gui.forms;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Listeners.WarEventUIListener;
import View.Gui.panels.MunitionPanel;


public class Form extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected List<WarEventUIListener> allListeners;
	protected MunitionPanel munitionPanel;
	

	public Form(MunitionPanel munitionPanel, List<WarEventUIListener> allListeners) {
		
		this.munitionPanel = munitionPanel;
//		allListeners = new LinkedList<WarEventUIListener>();
		this.allListeners = allListeners;
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				formClosed();
			}
		});
		
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);
		
	}
	
//	public void registerListener(WarEventUIListener listener) {
//		allListeners.add(listener);
//	}
	
	private void formClosed() {
		munitionPanel.formClosed();
	}
	
}
