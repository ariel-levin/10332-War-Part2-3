package View.Gui.panels;

import java.util.List;

import Listeners.WarEventUIListener;
import View.Gui.sections.SectionPanel;


public class LauncherDestructorPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String PLANE_IMAGE = "../images/plane.jpg";
	private static final String PLANEDES_IMAGE = "../images/plane-destroy.jpg";
	
	private static final String SHIP_IMAGE = "../images/ship.jpg";
	private static final String SHIPDES_IMAGE = "../images/ship-destroy.jpg";
	
	private String type;
	
	
	public LauncherDestructorPanel(String id, SectionPanel sectionPanel,
			List<WarEventUIListener> allListeners) {
		
		super(id, sectionPanel, allListeners);

	}
	

}
