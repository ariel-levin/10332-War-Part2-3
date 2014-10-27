package view.gui.sections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import view.GuiView;
import view.gui.forms.AddLauncherDestructorForm;
import view.gui.panels.LauncherDestructorPanel;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
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
		
//		showAllLauncherDestructors();
	}
	
	public void showAllLauncherDestructors() {
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
		new AddLauncherDestructorForm(super.guiView);
	}
	
	public void launcherDestructorAdded(String id, String type) {
		addExistLauncherDestructor(id, type);
		super.btnAddMunition.setEnabled(true);
	}
	
	public void showDestroyLauncher(String destructorID, String launcherID) {
		LauncherDestructorPanel ldp = (LauncherDestructorPanel)super.findMunition(destructorID);
		if (ldp != null)
			ldp.destroyLauncher(launcherID);
	}
	
	public void launcherDestructorDone(String destructorID) {
		LauncherDestructorPanel ldp = (LauncherDestructorPanel)super.findMunition(destructorID);
		if (ldp != null)
			ldp.destroyDone();
	}

}

