package View.Gui.panels;

import java.util.List;

import Listeners.WarEventUIListener;
import View.Gui.sections.SectionPanel;


public class IronDomePanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String DOME_IMAGE = "../images/dome.jpg";
	private static final String DOMEINT_IMAGE = "../images/dome-intercept.jpg";

	
	public IronDomePanel(String id, SectionPanel sectionPanel,
			List<WarEventUIListener> allListeners) {
		
		super(id, sectionPanel, allListeners);

	}
	
	
}

