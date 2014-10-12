package View.Gui.forms;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import View.GuiView;
import View.Gui.panels.MunitionPanel;


public class MunitionForm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	protected GuiView guiView;
	protected MunitionPanel munitionPanel;
	

	public MunitionForm(MunitionPanel munitionPanel, GuiView guiView) {
		
		this.munitionPanel = munitionPanel;
		this.guiView = guiView;
		
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
	
	private void formClosed() {
		munitionPanel.formClosed();
	}
	
}

