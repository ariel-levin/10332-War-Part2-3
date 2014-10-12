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
		
		showAllLaunchers();
	}
	
	private void showAllLaunchers() {
		Vector<String> launchersIds = super.guiView.getAllLaunchersID();
		for (String id : launchersIds)
			addExistLauncher(id);
	}
	
	private void addExistLauncher(String id) {
		super.displayMunition(new LauncherPanel(id, this, super.guiView));
	}
	
	private void addNewLauncher() {
		super.guiView.fireAddEnemyLauncher();
	}
	
	public void enemyLauncherAdded(String id) {
		addExistLauncher(id);
	}
	
}

