package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

import Listeners.WarEventUIListener;
import View.Gui.forms.DestroyForm;
import View.Gui.sections.SectionPanel;


public class LauncherDestructorPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String SHIP_IMAGE = "../images/ship.jpg";
	private static final String SHIPDES_IMAGE = "../images/ship-destroy.jpg";
	
	private static final String PLANE_IMAGE = "../images/plane.jpg";
	private static final String PLANEDES_IMAGE = "../images/plane-destroy.jpg";
	
	private String type;
	
	
	public LauncherDestructorPanel(String id, String type,
			SectionPanel sectionPanel, List<WarEventUIListener> allListeners) {

		super(id, sectionPanel, PLANE_IMAGE, "Destroy", allListeners);
		this.type = type;

		if (isShip())
			super.setIcon(SHIP_IMAGE);
		
		super.setButtonAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createDestroyForm();
			}
		});

	}

	private void createDestroyForm() {
		if (super.allListeners.get(0).chooseLauncherToIntercept() == null)
			JOptionPane.showMessageDialog(null,"No visible Launchers available","Error",JOptionPane.ERROR_MESSAGE);
		else {
			super.btnEnable(false);
			new DestroyForm(this, super.allListeners);
		}
	}
	
	private boolean isShip() {
		return (this.type.toLowerCase().compareTo("ship") == 0);
	}

	public void destroyLauncher(String launcherID) {

		if (isShip())
			super.setIcon(SHIPDES_IMAGE);
		else
			super.setIcon(PLANEDES_IMAGE);
		
		for (WarEventUIListener l : super.allListeners)
			l.interceptGivenLauncher(super.id, launcherID);

	}

	public void destroyDone() {

		if (isShip())
			super.setIcon(SHIP_IMAGE);
		else
			super.setIcon(PLANE_IMAGE);
		
		super.btnEnable(true);
	}

}

