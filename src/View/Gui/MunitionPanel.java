package View.Gui;

import javax.swing.JButton;
import javax.swing.JPanel;

public class MunitionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected SectionPanel sectionPanel;
	
	protected String id;
	protected JButton btnAction;
	
	
	
	public void setSectionPanel(SectionPanel sectionPanel) {
		this.sectionPanel = sectionPanel;
	}

}
