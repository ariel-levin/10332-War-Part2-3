package view.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import view.GuiView;
import view.gui.forms.*;
import view.gui.sections.*;


public class LauncherPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String LAUNCHER_IMAGE = "../images/launcher.jpg";
	private static final String LAUNCHERMIS_IMAGE = "../images/launcher-launch.jpg";
	private static final String LAUNCHERDES_IMAGE = "../images/launcher-destroyed.jpg";

	private boolean alive = false;
	private String missileOnAir = null;
	
	
	public LauncherPanel(String id, boolean isHidden,
			SectionPanel sectionPanel, GuiView guiView) {

		super(id, sectionPanel, LAUNCHER_IMAGE, "Launch", guiView);
		alive = true;

		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createLaunchForm();
			}
		});
		
		String str = (isHidden)?"Hidden":"Visible";
		super.setTitle(super.id + " - " + str);
	}

	private void createLaunchForm() {
		if (alive) {
			super.btnAction.setEnabled(false);
			new LaunchForm(this, super.guiView);
			super.setStatus("Add Missile");
		} else
			JOptionPane.showMessageDialog(null,"Launcher already destroyed","Error",JOptionPane.ERROR_MESSAGE);
	}

	public void addMissile(String destination, int flyTime, int damage) {
		if (alive) {
			super.guiView.fireAddEnemyMissile(super.id, destination, flyTime, damage);
			super.setStatus("Target " + destination);
		} else
			JOptionPane.showMessageDialog(null,"Launcher already destroyed","Error",JOptionPane.ERROR_MESSAGE);
	}
	
	public void launchMissile(String missileID) {
		if (alive) {
			super.setIcon(LAUNCHERMIS_IMAGE);
			this.missileOnAir = missileID;
			super.setStatus("Launching " + missileID);
		}
	}
	
	public void changeVisible(boolean visible) {
		if (alive) {
			String str = (visible)?"Visible":"Hidden";
			super.setTitle(super.id + " - " + str);
		}
	}

	public void launchDone() {
		if (alive) {
			super.setIcon(LAUNCHER_IMAGE);
			super.btnAction.setEnabled(true);
			super.setStatus("Free");
		}
		this.missileOnAir = null;
	}

	public void launcherDestroyed() {
		alive = false;
		this.missileOnAir = null;
		super.setIcon(LAUNCHERDES_IMAGE);
		super.setTitle(super.id);
		super.setStatus("Destroyed");
		super.btnAction.setEnabled(false);
	}

	public String getMissileOnAir() {
		return missileOnAir;
	}
	
}
