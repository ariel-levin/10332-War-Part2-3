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

	private boolean alive = false;
	private String missileOnAir = null;
	
	
	public LauncherPanel(String id, SectionPanel sectionPanel, GuiView guiView) {

		super(id, sectionPanel, LAUNCHER_IMAGE, "Launch", guiView);
		alive = true;

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
	
	public void launchMissile(String missileID) {
		super.setIcon(LAUNCHERMIS_IMAGE);
		this.missileOnAir = missileID;
		
	}
	
	public void changeVisible(boolean visible) {
		
		
	}

	public void launchDone() {
		if (alive) {
			super.setIcon(LAUNCHER_IMAGE);
			super.btnAction.setEnabled(true);
		}
		this.missileOnAir = null;
	}

	public void launcherDestroyed() {
		alive = false;
		this.missileOnAir = null;
		super.setIcon(LAUNCHERDES_IMAGE);
		super.btnAction.setEnabled(false);
	}

	public String getMissileOnAir() {
		return missileOnAir;
	}
	
}
