package View.Gui.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JPanel;
//import javax.swing.JSplitPane;


import Listeners.WarEventUIListener;
import View.Gui.sections.*;


public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
//	private JSplitPane munitionSplitter;
	private List<WarEventUIListener> allListeners;
	private SectionPanel launcherSection, ironDomeSection, launcherDestructorSection;

	
	public MainPanel(List<WarEventUIListener> allListeners) {
//		allListeners = new LinkedList<WarEventUIListener>();
		this.allListeners = allListeners;
//		setLayout(new BorderLayout());
		setLayout(new GridLayout(1,3,10,10));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = new Dimension();
		panelSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		setSize(panelSize);

//		munitionSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		launcherSection = new LaunchersSectionPanel(this.allListeners);
//		setSectionListerners(launcherSection);
		
//		ironDomeSection = new SectionPanel("Iron Domes");
//		for (WarEventUIListener l : allListeners)
//			ironDomeSection.registerListener(l);
//		launcherDestructorSection = new SectionPanel("Launcher Destructors");
//		for (WarEventUIListener l : allListeners)
//			launcherDestructorSection.registerListener(l);

		
//		munitionSplitter.setLeftComponent(launcherSection);
//		munitionSplitter.setRightComponent(ironDomeSection);
		add(launcherSection);


//		munitionSplitter.setResizeWeight(0.5); // distributes the extra space when resizing.
//											 // without it the proportions will not be saved during resize
//		tribesSplitter.setResizeWeight(0.6);

//		registerToTribesEvents();
		
//		add(tribesSplitter, BorderLayout.CENTER);
	}
	
//	public void registerListener(WarEventUIListener listener) {
//		allListeners.add(listener);
//	}

//	public SectionPanel getTribe1() {
//		return launcherSection;
//	}

//	public SectionPanel getTribe2() {
//		return ironDomeSection;
//	}

//	private void moveSurvivorToOtherTribe(SurvivorPanel survivorPanel,
//			SectionPanel srcTribePanel) {
//		if (launcherSection == srcTribePanel) {
//			launcherSection.removeSurvivor(survivorPanel);
//			ironDomeSection.addSurvivor(survivorPanel);
//		} else {
//			ironDomeSection.removeSurvivor(survivorPanel);
//			launcherSection.addSurvivor(survivorPanel);
//		}
//	}

//	private void setSectionListerners(SectionPanel sectionPanel) {
//		if (allListeners.isEmpty())
//			System.out.println("MainPanel no listeners");
//		
//		for (WarEventUIListener l : allListeners) {
//			System.out.println("enter setSectionListerners");
//			sectionPanel.registerListener(l);
//		}
//	}
	
//	public void showAllMunitions() {
//		((LaunchersSectionPanel)launcherSection).showAllLaunchers();
//		
//		
//		
//	}

//	@Override
//	public void moveSurvivorTribeEvent(SurvivorPanel survivorPanel,
//			SectionPanel srcTribePanel) {
//		moveSurvivorToOtherTribe(survivorPanel, srcTribePanel);
//	}
	
}

