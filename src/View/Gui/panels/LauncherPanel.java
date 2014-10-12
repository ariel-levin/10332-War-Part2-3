package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import View.GuiView;
import View.Gui.forms.*;
import View.Gui.sections.*;


public class LauncherPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String LAUNCHER_IMAGE = "../images/launcher.jpg";
	private static final String LAUNCHERMIS_IMAGE = "../images/launcher-launch.jpg";
	private static final String LAUNCHERDES_IMAGE = "../images/launcher-destroyed.jpg";

	
	public LauncherPanel(String id, SectionPanel sectionPanel, GuiView guiView) {

		super(id, sectionPanel, LAUNCHER_IMAGE, "Launch", guiView);

		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createLaunchForm();
			}
		});
		
	}

	private void createLaunchForm() {
		super.btnAction.setEnabled(false);
		new LaunchForm(this, super.guiView);
	}

	public void addMissile(String destination, int flyTime, int damage) {
		super.guiView.fireAddEnemyMissile(super.id, destination, flyTime, damage);
	}
	
	public void launchMissile() {
		super.setIcon(LAUNCHERMIS_IMAGE);
		
		
	}

	public void launchDone() {

		super.setIcon(LAUNCHER_IMAGE);
		super.btnAction.setEnabled(true);

	}

	public void launcherDestroyed() {

		super.setIcon(LAUNCHERDES_IMAGE);
		super.btnAction.setEnabled(false);

	}

}
