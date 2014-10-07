package View.Gui.sections;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import Listeners.WarEventUIListener;
import View.GuiView;
import View.Gui.forms.AddLauncherDestructorForm;
import View.Gui.panels.LauncherDestructorPanel;


public class LauncherDestructorsSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public LauncherDestructorsSectionPanel(GuiView guiView) {
		super(guiView,"Launcher Destructor");

		setBorder(BorderFactory.createTitledBorder("Launcher Destructors"));

		super.btnAddMunition = new JButton("Add Launcher Destructor");
		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAddForm();
			}
		});
		super.add(btnAddMunition, BorderLayout.NORTH);
		
		showAllLauncherDestructors();
	}
	
	private void showAllLauncherDestructors() {
		
		for (WarEventUIListener l : super.allListeners) {
			Vector<String> destructorsDetails = l.showAllLauncherDestructors();
			int size = destructorsDetails.size();
			for (int i = 0 ; i < size-1 ; i+=2) {
				String id = destructorsDetails.get(i);
				String type = destructorsDetails.get(i+1);
				addExistLauncherDestructor(id,type);
			}
		}
		
	}
	
	private void createAddForm() {
		super.btnAddMunition.setEnabled(false);
		new AddLauncherDestructorForm(this);
	}
	
	public void formClosed() {
		super.btnAddMunition.setEnabled(true);
	}
	
	public void addNewLauncherDestructor(String type) {
		for(WarEventUIListener l : super.allListeners) {
			String id = l.addDefenseLauncherDestructor(type);
			addExistLauncherDestructor(id,type);
		}
		super.btnAddMunition.setEnabled(true);
	}
	
	private void addExistLauncherDestructor(String id, String type) {
		
		LauncherDestructorPanel launcherDestructorPanel = new LauncherDestructorPanel(id, type, this, super.allListeners);
		super.displayMunition(launcherDestructorPanel);
	}

}

