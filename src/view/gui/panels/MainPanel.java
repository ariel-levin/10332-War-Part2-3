package view.gui.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;

import view.GuiView;
import view.gui.sections.*;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class MainPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
//	private JSplitPane munitionSplitter;
	private GuiView guiView;
	private SectionPanel launcherSection, ironDomeSection, launcherDestructorSection;

	
	public MainPanel(GuiView guiView) {
//		allListeners = new LinkedList<WarEventUIListener>();
		this.guiView = guiView;
//		setLayout(new BorderLayout());
		setLayout(new GridLayout(1,3,10,10));

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension panelSize = new Dimension();
		panelSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		setSize(panelSize);

//		munitionSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);

		launcherSection = new LaunchersSectionPanel(this.guiView);
		ironDomeSection = new IronDomesSectionPanel(this.guiView);
		launcherDestructorSection = new LauncherDestructorsSectionPanel(this.guiView);

//		munitionSplitter.setLeftComponent(launcherSection);
//		munitionSplitter.setRightComponent(ironDomeSection);
		add(launcherSection);
		add(ironDomeSection);
		add(launcherDestructorSection);

//		munitionSplitter.setResizeWeight(0.5); // distributes the extra space when resizing.
//											 // without it the proportions will not be saved during resize
//		tribesSplitter.setResizeWeight(0.6);

//		add(tribesSplitter, BorderLayout.CENTER);
	}
	
	public void showAllMunitions() {
		((LaunchersSectionPanel)launcherSection).showAllLaunchers();
		((IronDomesSectionPanel)ironDomeSection).showAllIronDomes();
		((LauncherDestructorsSectionPanel)launcherDestructorSection).showAllLauncherDestructors();
	}
	
	public void enemyLauncherAdded(String id, boolean isHidden) {
		((LaunchersSectionPanel)launcherSection).enemyLauncherAdded(id, isHidden);
	}
	
	public void ironDomeAdded(String id) {
		((IronDomesSectionPanel)ironDomeSection).ironDomeAdded(id);
	}
	
	public void launcherDestructorAdded(String id, String type) {
		((LauncherDestructorsSectionPanel)launcherDestructorSection).launcherDestructorAdded(id, type);
	}
	
	public void showInterceptMissile(String ironDomeID, String missileID) {
		((IronDomesSectionPanel)ironDomeSection).showInterceptMissile(ironDomeID, missileID);
	}
	
	public void showDestroyLauncher(String destructorID, String launcherID) {
		((LauncherDestructorsSectionPanel)launcherDestructorSection).showDestroyLauncher(destructorID, launcherID);
	}
	
	public void showEnemyLaunch(String launcherID, String missileID) {
		((LaunchersSectionPanel)launcherSection).showEnemyLaunch(launcherID, missileID);
	}
	
	public void showLauncherIsVisible(String launcherID, boolean visible) {
		((LaunchersSectionPanel)launcherSection).showLauncherIsVisible(launcherID, visible);
	}
	
	public void ironDomeDone(String ironDomeID) {
		((IronDomesSectionPanel)ironDomeSection).ironDomeDone(ironDomeID);
	}
	
	public void launcherDone(String launcherID) {
		((LaunchersSectionPanel)launcherSection).launcherDone(launcherID);
	}
	
	public void launcherDestructorDone(String destructorID) {
		((LauncherDestructorsSectionPanel)launcherDestructorSection).launcherDestructorDone(destructorID);
	}
	
	public void launcherDestroyed(String launcherID) {
		((LaunchersSectionPanel)launcherSection).launcherDestroyed(launcherID);
	}
	
	public String getMissileOwner(String missileID) {
		return ((LaunchersSectionPanel)launcherSection).getMissileOwner(missileID);
	}
	
}

