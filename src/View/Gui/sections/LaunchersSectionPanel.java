package View.Gui.sections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import View.GuiView;
import View.Gui.panels.*;


public class LaunchersSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public LaunchersSectionPanel(GuiView guiView) {
		super(guiView,"Launcher");
		
		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewLauncher();
			}
		});
		
//		showAllLaunchers();
	}
	
	public void showAllLaunchers() {
		Vector<String> launchersIds = super.guiView.getAllLaunchersID();
		for (String id : launchersIds) {
			boolean isHidden = super.guiView.isLauncherHidden(id);
			addExistLauncher(id, isHidden);
		}
	}
	
	private void addExistLauncher(String id, boolean isHidden) {
		super.displayMunition(new LauncherPanel(id, isHidden, this, super.guiView));
	}
	
	private void addNewLauncher() {
		super.guiView.fireAddEnemyLauncher();
	}
	
	public void enemyLauncherAdded(String id, boolean isHidden) {
		addExistLauncher(id, isHidden);
	}
	
	public void showEnemyLaunch(String launcherID, String missileID) {
		LauncherPanel lp = (LauncherPanel)super.findMunition(launcherID);
		if (lp != null)
			lp.launchMissile(missileID);
	}
	
	public void showLauncherIsVisible(String launcherID, boolean visible) {
		LauncherPanel lp = (LauncherPanel)super.findMunition(launcherID);
		if (lp != null)
			lp.changeVisible(visible);
	}
	
	public void launcherDone(String launcherID) {
		LauncherPanel lp = (LauncherPanel)super.findMunition(launcherID);
		if (lp != null)
			lp.launchDone();
	}
	
	public void launcherDestroyed(String launcherID) {
		LauncherPanel lp = (LauncherPanel)super.findMunition(launcherID);
		if (lp != null)
			lp.launcherDestroyed();
	}
	
	public String getMissileOwner(String missileID) {
		for (MunitionPanel mp : super.munitionArr) {
			String onAir = ((LauncherPanel)mp).getMissileOnAir();
			if (onAir!=null && onAir.compareTo(missileID)==0)
				return mp.getId();
		}
		return null;
	}
	
}

