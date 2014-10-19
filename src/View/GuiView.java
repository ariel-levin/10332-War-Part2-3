package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import Listeners.WarEventUIListener;
import Utils.*;
import View.Gui.panels.*;
import View.Gui.utils.WarMenu;


public class GuiView extends JFrame implements AbstractWarView {
	
	private static final long serialVersionUID = 1L;
	
	private List<WarEventUIListener> allListeners;
	private MainPanel mainPanel;
	private JTextArea console;
	

	public GuiView() {
		allListeners = new LinkedList<WarEventUIListener>();
	}

	private void createFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {}

		setTitle("War Managment");
		
		// set the frame's Close operation
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				fireShowStatistics();
				fireFinishWar();
			}
		});

//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension();
//		console.append("screenSize.width * 0.5 = " + screenSize.width * 0.5);
//		console.append("screenSize.height * 0.5 = " + screenSize.height * 0.5);
//		frameSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		frameSize.setSize(1080,650);
//		frameSize.setSize(1080,500);
		setSize(frameSize);
		
		getContentPane().setLayout(new BorderLayout());
		mainPanel = new MainPanel(this);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
		
		JPanel lowerPanel = new JPanel(new BorderLayout());
		console = new JTextArea();
		console.setFont(console.getFont().deriveFont(Font.PLAIN,12));
		console.setEditable(false);

		DefaultCaret caret = (DefaultCaret)console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(console);
		scrollPane.setPreferredSize(new Dimension(this.getWidth()-20, 150));
		lowerPanel.add(scrollPane, BorderLayout.CENTER);
		
		getContentPane().add(lowerPanel, BorderLayout.SOUTH);
		
		setJMenuBar(new WarMenu(this));
		setLocationRelativeTo(null);
//		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	public void registerListener(WarEventUIListener listener) {
		allListeners.add(listener);
	}

	public void fireAddEnemyLauncher() {
		for (WarEventUIListener l : allListeners)
			l.addEnemyLauncher();
	}
	
	public void enemyLauncherAdded(String id, boolean isHidden) {
		this.mainPanel.enemyLauncherAdded(id, isHidden);
	}

	public void fireAddDefenseIronDome() {
		for (WarEventUIListener l : allListeners)
			l.addIronDome();
	}
	
	public void ironDomeAdded(String id) {
		this.mainPanel.ironDomeAdded(id);
	}
	
	/* When user is select, an event is throw to the control */
	public void fireAddDefenseLauncherDestructor(String type) {
		for (WarEventUIListener l : allListeners)
			l.addDefenseLauncherDestructor(type);
	}
	
	public void launcherDestructorAdded(String id, String type) {
		this.mainPanel.launcherDestructorAdded(id, type);
	}
	
	public void fireAddEnemyMissile(String launcherID, String destination,
			int flyTime, int damage) {
		
		for (WarEventUIListener l : allListeners)
			l.addEnemyMissile(launcherID, destination, damage, flyTime);
	}

	public void fireInterceptEnemyLauncher(String destructorID, String launcherID) {
		for (WarEventUIListener l : allListeners)
			l.interceptGivenLauncher(destructorID, launcherID);
	}

	public void fireInterceptMissile(String ironDomeID, String missileID) {
		for (WarEventUIListener l : allListeners)
			l.interceptGivenMissile(ironDomeID, missileID);
	}

	@Override
	public Vector<String> getMissileToIntercept() {
		Vector<String> missiles = allListeners.get(0).chooseMissileToIntercept();
		return missiles;
	}
	
	@Override
	public Vector<String> getLauncherToIntercept() {
		Vector<String> launchers = allListeners.get(0).chooseLauncherToIntercept();
		return launchers;
	}
	
	@Override
	public Vector<String> getAllLaunchersID() {
		Vector<String> ids = allListeners.get(0).showAllLaunchers();
		return ids;
	}
	
	@Override
	public Vector<String> getAllIronDomesID() {
		Vector<String> ids = allListeners.get(0).showAllIronDomes();
		return ids;
	}
	
	@Override
	public Vector<String> getAllLauncherDestructors() {
		Vector<String> details = allListeners.get(0).showAllLauncherDestructors();
		return details;
	}
	
	@Override
	public String[] getAllWarDestinations() {
		String[] dest = allListeners.get(0).getAllWarDestinations();
		return dest;
	}
	
	public boolean isLauncherHidden(String launcherID) {
		boolean isHidden = allListeners.get(0).isLauncherHidden(launcherID);
		return isHidden;
	}
	
	public boolean isLauncherAliveAndVisible(String launcherID) {
		boolean isAliveAndVisible = allListeners.get(0).isLauncherAliveAndVisible(launcherID);
		return isAliveAndVisible;
	}
	
	public boolean isMissileOnAir(String missileID) {
		boolean isOnAir = allListeners.get(0).isMissileOnAir(missileID);
		return isOnAir;
	}
	
	public void fireShowStatistics() {
		for (WarEventUIListener l : allListeners)
			l.showStatistics();
	}

	public void fireShowStatisticsByDate(Calendar startDate, Calendar endDate) {
		for (WarEventUIListener l : allListeners)
			l.showStatisticsByDate(startDate, endDate);
	}
	
	public void fireFinishWar() {
		for (WarEventUIListener l : allListeners) {
			l.reqfinishWar();
		}
	}

	/* Prints to screen event from controller */
	public void showDefenseLaunchMissile(String MunitionsId, String missileId,
			String enemyMissileId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] Iron dome: "
						+ MunitionsId + " just launched missile: " + missileId
						+ " towards missile: " + enemyMissileId);
		
		this.mainPanel.showInterceptMissile(MunitionsId, enemyMissileId);
	}

	public void showDefenseLaunchMissile(String MunitionsId, String type,
			String missileId, String enemyLauncherId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] " + type + ": "
						+ MunitionsId + " just launched missile: " + missileId
						+ " towards launcher: " + enemyLauncherId);
		
		this.mainPanel.showDestroyLauncher(MunitionsId, enemyLauncherId);
	}

	public void showEnemyLaunchMissile(String MunitionsId, String missileId,
			String destination, int damage) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] Launcher: "
						+ MunitionsId + " just launched missile: " + missileId
						+ " towards: " + destination
						+ " its about to cause damade of: " + damage);
		
		this.mainPanel.showEnemyLaunch(MunitionsId, missileId);
	}

	public void showLauncherIsVisible(String id, boolean visible) {
		String str = visible ? "visible" : "hidden";
		console.append(	"\n[" + Utils.getCurrentTime() + "] Launcher: " + id
						+ " just turned " + str);
		
		this.mainPanel.showLauncherIsVisible(id, visible);
	}

	public void showMissInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] Iron Dome: "
						+ whoLaunchedMeId + " fired missile: " + id
						+ " but missed the missile: " + enemyMissileId);
		
		this.mainPanel.ironDomeDone(whoLaunchedMeId);
	}

	public void showHitInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] Iron Dome: "
						+ whoLaunchedMeId + " fired missile: " + id
						+ " and intercept succesfully the missile: " + enemyMissileId);
		
		this.mainPanel.ironDomeDone(whoLaunchedMeId);
		String launcherID = this.mainPanel.getMissileOwner(enemyMissileId);
		if (launcherID != null)
			this.mainPanel.launcherDone(launcherID);
	}

	public void showEnemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] Enemy Missile: "
						+ id + " HIT " + destination + ". the damage is: " + damage
						+ ". Launch by: " + whoLaunchedMeId);
		
		this.mainPanel.launcherDone(whoLaunchedMeId);
	}

	public void showEnemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] Enemy Missile: "
						+ id + " MISSED " + destination + " launch at: " + launchTime
						+ ". Launch by: " + whoLaunchedMeId);
		
		this.mainPanel.launcherDone(whoLaunchedMeId);
	}

	public void showMissInterceptionLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId, String missileId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] " + type + ": "
						+ whoLaunchedMeId + " fired missile: " + missileId
						+ " but missed the Launcher: " + enemyLauncherId);
		
		this.mainPanel.launcherDestructorDone(whoLaunchedMeId);
	}

	public void showMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] " + type + ": "
						+ whoLaunchedMeId + " missed the Launcher: " + enemyLauncherId
						+ " because he is hidden");
		
		this.mainPanel.launcherDestructorDone(whoLaunchedMeId);
	}

	public void showHitInterceptionLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId, String missileId) {
		
		console.append(	"\n[" + Utils.getCurrentTime() + "] " + type + ": "
						+ whoLaunchedMeId + " fired missile: " + missileId
						+ " and intercept succesfully the Launcher: "
						+ enemyLauncherId);
		
		this.mainPanel.launcherDestructorDone(whoLaunchedMeId);
		this.mainPanel.launcherDestroyed(enemyLauncherId);
	}

	// prints all war statistics
	public void showStatistics(long[] array, Calendar startDate, Calendar endDate) {
		StringBuilder msg = new StringBuilder();
		msg.append("[" + Utils.getCurrentTime() + "]"
				+ "\t\t   War Statistics\n");		
		msg.append("\t\t\t=========================================\n");
		
		if (startDate!=null && endDate!=null) {
			msg.append("\t\t\t||\tFrom " + startDate.getTime() + " to " + endDate.getTime() + "\t||\n");
			msg.append("\t\t\t=========================================\n");
		}
		
		msg.append("\t\t\t||\tNum of launch missiles: " + array[0] + "\t||\n");
		msg.append("\t\t\t||\tNum of intercept missiles: " + array[1]
				+ "\t||\n");
		msg.append("\t\t\t||\tNum of hit target missiles: " + array[2]
				+ "\t||\n");
		msg.append("\t\t\t||\tNum of launchers destroyed: " + array[3]
				+ "\t||\n");
		msg.append("\t\t\t||\ttotal damage: " + array[4] + "\t\t||\n");
		msg.append("\t\t\t==========================================\n");
		JOptionPane.showMessageDialog(null,msg.toString(),"War Statistics",JOptionPane.INFORMATION_MESSAGE);
	}

	public void showWarHasBeenFinished() {
		dispose();
		System.exit(0);
	}

	public void showWarHasBeenStarted() {
		createFrame();
		this.mainPanel.showAllMunitions();
	}

	public void showNoSuchObject(String type) {
		console.append(	"\n[" + Utils.getCurrentTime()
						+ "] ERROR: Cannot find " + type + " you selected in war");
	}

	public void showMissileNotExist(String defenseLauncherId, String enemyId) {
		console.append(	"\n[" + Utils.getCurrentTime() + "] ERROR: "
						+ defenseLauncherId + " tried to intercept, " + "but missed: "
						+ enemyId + " doesn't exist!");
	}

	public void showLauncherNotExist(String defenseLauncherId, String launcherId) {
		console.append(	"\n[" + Utils.getCurrentTime() + "] ERROR: "
						+ defenseLauncherId + " tried to intercept, " + "but missed: "
						+ launcherId + " doesn't exist!");
	}

	@Override
	public String getWarNameFromUser() {
		String name = JOptionPane.showInputDialog(null,
				"Please enter War name", "War Name",
				JOptionPane.QUESTION_MESSAGE);
		return name;
	}
	
	@Override
	public void showWarNameTaken() {
		JOptionPane.showMessageDialog(null,
				"War name taken, please select another", "Error",
				JOptionPane.ERROR_MESSAGE);
	}
	
	public void start() {
		// not needed
	}
	
}

