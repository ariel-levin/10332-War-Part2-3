package View.Gui.panels;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Listeners.WarEventUIListener;


public class SectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private List<WarEventUIListener> allListeners;

	private String sectionName;

	private JButton btnAddSurvivor;
	private JPanel innerPanel;
//	private List<TribeListener> allListseners;
	
	public SectionPanel(String sectionName) {
//		allListseners = new ArrayList<TribeListener>();
		this.sectionName = sectionName;
		allListeners = new LinkedList<WarEventUIListener>();
		
		setLayout(new BorderLayout());
		
		setBorder(BorderFactory.createTitledBorder(sectionName));

//		btnAddSurvivor = new JButton(new AddSurvivorAction(this));
		
		// OR:
		/*btnAddSurvivor = new JButton();
		btnAddSurvivor.setText("Add Survivor");
		btnAddSurvivor.addActionListener(new AddSurvivorAction(this));*/
		add(btnAddSurvivor, BorderLayout.NORTH);
		
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
	
	public void addMunition(MunitionPanel munition) {
		innerPanel.add(munition);
		munition.setSectionPanel(this);
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
