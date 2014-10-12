package View.Gui.sections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import View.GuiView;
import View.Gui.forms.AddLauncherDestructorForm;
import View.Gui.panels.LauncherDestructorPanel;


public class LauncherDestructorsSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public LauncherDestructorsSectionPanel(GuiView guiView) {
		super(guiView,"Launcher Destructor");

		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createAddForm();
			}
		});
		
		showAllLauncherDestructors();
	}
	
	private void showAllLauncherDestructors() {
		Vector<String> destructorsDetails = super.guiView.getAllLauncherDestructors();
		int size = destructorsDetails.size();
		for (int i = 0 ; i < size-1 ; i+=2) {
			String id = destructorsDetails.get(i);
			String type = destructorsDetails.get(i+1);
			addExistLauncherDestructor(id,type);
		}		
	}
	
	private void addExistLauncherDestructor(String id, String type) {
		super.displayMunition(new LauncherDestructorPanel(id, type, this, super.guiView));
	}
	
	public void addNewLauncherDestructor(String type) {
		super.guiView.fireAddDefenseLauncherDestructor(type);
	}
	
	private void createAddForm() {
		super.btnAddMunition.setEnabled(false);
		new AddLauncherDestructorForm(this);
	}
	
	public void formClosed() {
		super.btnAddMunition.setEnabled(true);
	}
	
	public void launcherDestructorAdded(String id, String type) {
		addExistLauncherDestructor(id, type);
		super.btnAddMunition.setEnabled(true);
	}
	
	public void showDestroyLauncher(String destructorID, String launcherID) {
		
		
	}
	
	public void launcherDestructorDone(String destructorID) {
		
		
	}

}

