package view.gui.sections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import view.GuiView;
import view.gui.panels.IronDomePanel;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class IronDomesSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public IronDomesSectionPanel(GuiView guiView) {
		super(guiView,"Iron Dome");
		
		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewIronDome();
			}
		});
		
//		showAllIronDomes();
	}
	
	public void showAllIronDomes() {
		Vector<String> ironDomesIds = super.guiView.getAllIronDomesID();
		for (String id : ironDomesIds)
			addExistIronDome(id);
	}
	
	private void addExistIronDome(String id) {
		super.displayMunition(new IronDomePanel(id, this, super.guiView));
	}
	
	private void addNewIronDome() {
		super.guiView.fireAddDefenseIronDome();
	}
		
	public void ironDomeAdded(String id) {
		addExistIronDome(id);
	}
	
	public void showInterceptMissile(String ironDomeID, String missileID) {
		IronDomePanel idp = (IronDomePanel)super.findMunition(ironDomeID);
		if (idp != null)
			idp.interceptMissile(missileID);
	}
	
	public void ironDomeDone(String ironDomeID) {
		IronDomePanel idp = (IronDomePanel)super.findMunition(ironDomeID);
		if (idp != null)
			idp.interceptDone();
	}

}

