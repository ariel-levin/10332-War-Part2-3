package View.Gui.sections;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import View.GuiView;
import View.Gui.panels.IronDomePanel;


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
		
		showAllIronDomes();
	}
	
	private void showAllIronDomes() {
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
		
		
	}
	
	public void ironDomeDone(String ironDomeID) {
		
		
	}

}

