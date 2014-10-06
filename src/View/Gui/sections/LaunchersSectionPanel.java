package View.Gui.sections;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import Listeners.WarEventUIListener;
import View.Gui.panels.*;


public class LaunchersSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public LaunchersSectionPanel(List<WarEventUIListener> allListeners) {
		super(allListeners);
		
		setBorder(BorderFactory.createTitledBorder("Launchers"));

		super.btnAddMunition = new JButton("Add Launcher");
		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewLauncher();
			}
		});
		super.add(btnAddMunition, BorderLayout.NORTH);
		
		showAllLaunchers();
	}
	
	private void addNewLauncher() {
		for(WarEventUIListener l : super.allListeners) {
			String id = l.addEnemyLauncher();
			addExistLauncher(id);			
		}
	}
	
	private void addExistLauncher(String id) {
		LauncherPanel launcherPanel = new LauncherPanel(id, this, super.allListeners);
//		for (WarEventUIListener l : allListeners)
//			launcherPanel.registerListener(l);
		
		super.displayMunition(launcherPanel);
	}
	
	private void showAllLaunchers() {
		
		for (WarEventUIListener l : super.allListeners) {
			Vector<String> launchersIds = l.showAllLaunchers();
			for (String id : launchersIds)
				addExistLauncher(id);
		}
		
	}

}

