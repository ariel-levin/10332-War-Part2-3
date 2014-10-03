package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.BorderFactory;
import javax.swing.JButton;

import Listeners.WarEventUIListener;


public class LaunchersSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public LaunchersSectionPanel() {
		super();
		
		setBorder(BorderFactory.createTitledBorder("Launchers"));
		
		showAllLaunchers();
		
		super.btnAddMunition = new JButton("Add Launcher");
		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewLauncher();
			}
		});
		
	}
	
	private void showAllLaunchers() {

		for (WarEventUIListener l : allListeners) {
			Vector<String> launchersIds = l.showAllLaunchers();
			for (String id : launchersIds)
				addExistLauncher(id);
		}
	}
	
	private void addNewLauncher() {
		for(WarEventUIListener l : allListeners) {
			String id = l.addEnemyLauncher();
			addExistLauncher(id);			
		}
	}
	
	private void addExistLauncher(String id) {
		LauncherPanel launcherPanel = new LauncherPanel(id, this);
		for (WarEventUIListener l : allListeners)
			launcherPanel.registerListener(l);
		
		super.displayMunition(launcherPanel);
	}

}

