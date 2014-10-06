package View.Gui.panels;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Listeners.WarEventUIListener;
import View.Gui.sections.*;


public class MunitionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected List<WarEventUIListener> allListeners;
	protected SectionPanel sectionPanel;
	protected String id;
	protected JButton btnAction;
	protected JLabel munitionNameAndIcon;
	
	
	public MunitionPanel(String id, SectionPanel sectionPanel, List<WarEventUIListener> allListeners) {
		this.id = id;
		this.sectionPanel = sectionPanel;
		this.allListeners = allListeners;
//		allListeners = new LinkedList<WarEventUIListener>();
	}
	
//	public void registerListener(WarEventUIListener listener) {
//		allListeners.add(listener);
//	}
	
	public void setSectionPanel(SectionPanel sectionPanel) {
		this.sectionPanel = sectionPanel;
	}

	public String getId() {
		return id;
	}
	
	public void formClosed() {
		btnAction.setEnabled(true);
	}
	
}

