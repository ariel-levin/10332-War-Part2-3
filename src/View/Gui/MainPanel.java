package View.Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
//import javax.swing.JSplitPane;


public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
//	private JSplitPane munitionSplitter;
	private SectionPanel launcherSection, ironDomeSection, launcherDestructorSection;

	public MainPanel() {
		setLayout(new BorderLayout());

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = new Dimension();
		panelSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		setSize(panelSize);

//		munitionSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		launcherSection = new SectionPanel("Launchers");
		ironDomeSection = new SectionPanel("Iron Domes");
		launcherDestructorSection = new SectionPanel("Launcher Destructors");

//		munitionSplitter.setLeftComponent(launcherSection);
//		munitionSplitter.setRightComponent(ironDomeSection);


//		munitionSplitter.setResizeWeight(0.5); // distributes the extra space when resizing.
//											 // without it the proportions will not be saved during resize
//		tribesSplitter.setResizeWeight(0.6);

//		registerToTribesEvents();
		
//		add(tribesSplitter, BorderLayout.CENTER);
	}

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

//	private void registerToTribesEvents() {
//		launcherSection.addListener(this);
//		ironDomeSection.addListener(this);
//	}

//	@Override
//	public void moveSurvivorTribeEvent(SurvivorPanel survivorPanel,
//			SectionPanel srcTribePanel) {
//		moveSurvivorToOtherTribe(survivorPanel, srcTribePanel);
//	}
	
}

