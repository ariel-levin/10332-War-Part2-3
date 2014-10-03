package View.Gui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Listeners.WarEventUIListener;


public class SectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected List<WarEventUIListener> allListeners;
	protected JButton btnAddMunition;
	protected ArrayList<MunitionPanel> munitionArr = new ArrayList<MunitionPanel>();
	
	private JPanel innerPanel;
	
	
	public SectionPanel() {
//		allListseners = new ArrayList<TribeListener>();
		allListeners = new LinkedList<WarEventUIListener>();
		
		setLayout(new BorderLayout());
		
		// OR:
		/*btnAddSurvivor = new JButton();
		btnAddSurvivor.setText("Add Survivor");
		btnAddSurvivor.addActionListener(new AddSurvivorAction(this));*/
		add(btnAddMunition, BorderLayout.NORTH);
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JScrollPane scroller = new JScrollPane(innerPanel);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller, BorderLayout.CENTER);
	}

	public void registerListener(WarEventUIListener listener) {
		allListeners.add(listener);
	}
	
//	public void addSurvivor(String name) {
//		survivorsInnerPanel.add(new SurvivorPanel(name, this));
//		validate();
//		repaint();
//	}

	public void displayMunition(MunitionPanel munition) {
		munitionArr.add(munition);
		innerPanel.add(munition);
//		munition.setSectionPanel(this);
		validate();
		repaint();
	}
	
//	public void removeSurvivor(SurvivorPanel theSurvivor) {
//		survivorsInnerPanel.remove(theSurvivor);
//		validate();
//		repaint(); 
//	}
	
//	public void addListener(TribeListener newListener) {
//		allListseners.add(newListener);
//	}
	
	// this method is triggered from the SurvivorPanel
//	public void moveSurvivorToOtherTribe(SurvivorPanel theSurvivorPanel) {
//		fireMoveSurvivorTribeEvent(theSurvivorPanel);
//	}
	
//	private void fireMoveSurvivorTribeEvent(SurvivorPanel theSurvivorPanel) {
//		for (TribeListener l : allListseners) {
//			l.moveSurvivorTribeEvent(theSurvivorPanel, this);
//		}
//	}
	
}
