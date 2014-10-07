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
import View.Gui.panels.IronDomePanel;


public class IronDomesSectionPanel extends SectionPanel {

	private static final long serialVersionUID = 1L;

	
	public IronDomesSectionPanel(GuiView guiView) {
		super(guiView,"Iron Dome");
		
		setBorder(BorderFactory.createTitledBorder("Iron Domes"));

		super.btnAddMunition = new JButton("Add Iron Dome");
		super.btnAddMunition.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addNewIronDome();
			}
		});
		super.add(btnAddMunition, BorderLayout.NORTH);
		
		showAllIronDomes();
	}
	
	private void addNewIronDome() {
		for(WarEventUIListener l : super.allListeners) {
			String id = l.addIronDome();
			addExistIronDome(id);			
		}
	}
	
	private void addExistIronDome(String id) {
		
		IronDomePanel ironDomePanel = new IronDomePanel(id, this, super.allListeners);
		super.displayMunition(ironDomePanel);
	}
	
	private void showAllIronDomes() {
		
		for (WarEventUIListener l : super.allListeners) {
			Vector<String> ironDomesIds = l.showAllIronDomes();
			for (String id : ironDomesIds)
				addExistIronDome(id);
		}
		
	}

}

