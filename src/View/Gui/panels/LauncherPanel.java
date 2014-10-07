package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import Listeners.WarEventUIListener;
import View.GuiView;
import View.Gui.forms.*;
import View.Gui.sections.*;


public class LauncherPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String LAUNCHER_IMAGE = "../images/launcher.jpg";
	private static final String LAUNCHERMIS_IMAGE = "../images/launcher-launch.jpg";
	private static final String LAUNCHERDES_IMAGE = "../images/launcher-destroyed.jpg";

	
	public LauncherPanel(String id, SectionPanel sectionPanel, GuiView guiView) {
	
//	public LauncherPanel(String id) { // for test
		
		super(id, sectionPanel, LAUNCHER_IMAGE, "Launch", guiView);

		super.setButtonAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createLaunchForm();
			}
		});
		
	}

	private void createLaunchForm() {
		super.btnEnable(false);

		// Form form = new LaunchForm(this);
		// for(WarEventUIListener l : super.allListeners)
		// form.registerListener(l);

		new LaunchForm(this, super.allListeners);
	}

	public void launchMissile(String destination, int flyTime, int damage) {

		super.setIcon(LAUNCHERMIS_IMAGE);
		for (WarEventUIListener l : super.allListeners)
			l.addEnemyMissile(super.id, destination, damage, flyTime);

	}

	public void launchDone() {

		super.setIcon(LAUNCHER_IMAGE);
		super.btnEnable(true);

	}

	public void launcherDestroyed() {

		super.setIcon(LAUNCHERDES_IMAGE);
		super.btnEnable(false);

	}

	// for test
	// public static void main(String[] args) {
	// javax.swing.JFrame frame = new javax.swing.JFrame();
	// frame.add(new LauncherPanel("123"));
	// frame.setSize(300, 300);
	// frame.setLocationRelativeTo(null);
	// frame.setVisible(true);
	// }

}
