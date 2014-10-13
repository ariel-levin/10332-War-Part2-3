package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import View.GuiView;
import View.Gui.forms.DestroyForm;
import View.Gui.sections.SectionPanel;


public class LauncherDestructorPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String SHIP_IMAGE = "../images/ship.jpg";
	private static final String SHIPDES_IMAGE = "../images/ship-destroy.jpg";
	
	private static final String PLANE_IMAGE = "../images/plane.jpg";
	private static final String PLANEDES_IMAGE = "../images/plane-destroy.jpg";
	
	private String type;
	
	
	public LauncherDestructorPanel(String id, String type, SectionPanel sectionPanel, GuiView guiView) {

		super(id, sectionPanel, PLANE_IMAGE, "Destroy", guiView);
		this.type = type;

		if (isShip())
			super.setIcon(SHIP_IMAGE);
		
		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createDestroyForm();
			}
		});

	}

	private void createDestroyForm() {
		if (super.guiView.getLauncherToIntercept() == null)
			JOptionPane.showMessageDialog(null,"No visible Launchers available","Error",JOptionPane.ERROR_MESSAGE);
		else {
			super.btnAction.setEnabled(false);
			new DestroyForm(this, super.guiView);
		}
	}
	
	private boolean isShip() {
		return (this.type.toLowerCase().compareTo("ship") == 0);
	}

	public void addTarget(String launcherID) {
		if (super.guiView.isLauncherAliveAndVisible(launcherID)) {
			super.guiView.fireInterceptEnemyLauncher(super.id, launcherID);
			super.setStatus("Target " + launcherID);
		} else
			destroyDone();
	}
	
	public void destroyLauncher(String launcherID) {
		if (isShip())
			super.setIcon(SHIPDES_IMAGE);
		else
			super.setIcon(PLANEDES_IMAGE);
		
		super.setStatus("Destroy " + launcherID);
	}

	public void destroyDone() {
		if (isShip())
			super.setIcon(SHIP_IMAGE);
		else
			super.setIcon(PLANE_IMAGE);
		
		super.btnAction.setEnabled(true);
		super.setStatus("Free");
	}

}

