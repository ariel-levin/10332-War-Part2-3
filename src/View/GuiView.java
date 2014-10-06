package View;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import Listeners.WarEventUIListener;
import Utils.*;
import View.Gui.panels.*;


public class GuiView extends JFrame implements AbstractWarView {
	
	private static final long serialVersionUID = 1L;
	
	private List<WarEventUIListener> allListeners;
	private MainPanel mainPanel;
	
//	private Scanner input = new Scanner(System.in);
//	private StringBuilder menu = new StringBuilder(1000);
//	private boolean isRunning = true;

	public GuiView() {
		allListeners = new LinkedList<WarEventUIListener>();
	}

	private void createFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		setTitle("War Managment");
		
		// set the frame's Close operation
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
//				CloseJFrameUtil.closeApplication(GuiView.this);
				// NOTE: when we want the 'Class' of the outer class, 'this' doesn't work.
				// Should use <OuterClass>.this
				
				fireFinishWar();
			}
		});

//		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = new Dimension();
//		System.out.println("screenSize.width * 0.5 = " + screenSize.width * 0.5);
//		System.out.println("screenSize.height * 0.5 = " + screenSize.height * 0.5);
//		frameSize.setSize(screenSize.width * 0.5, screenSize.height * 0.5);
		frameSize.setSize(1080,500);
		setSize(frameSize);
		
		getContentPane().setLayout(new BorderLayout());
		mainPanel = new MainPanel(this.allListeners);
		getContentPane().add(mainPanel, BorderLayout.CENTER);
		
//		for (WarEventUIListener l : allListeners)
//			mainPanel.registerListener(l);
		
//		setJMenuBar(new WarMenu(this));
		setLocationRelativeTo(null);
//		setAlwaysOnTop(true);
		setVisible(true);
	}
	
	public void registerListener(WarEventUIListener listener) {
		allListeners.add(listener);
	}

//	private void fireShowStatistics() {
//		for (WarEventUIListener l : allListeners)
//			l.showStatistics();
//	}

	private void fireFinishWar() {
		for (WarEventUIListener l : allListeners) {
			l.finishWar();
		}
//		isRunning = false;
	}

	/* Prints to screen event from controller */
	public void showDefenseLaunchMissile(String MunitionsId, String missileId,
			String enemyMissileId) {
		System.out.println("[" + Utils.getCurrentTime() + "] Iron dome: "
				+ MunitionsId + " just launched missile: " + missileId
				+ " towards missile: " + enemyMissileId);
	}

	public void showDefenseLaunchMissile(String MunitionsId, String type,
			String missileId, String enemyLauncherId) {
		System.out.println("[" + Utils.getCurrentTime() + "] " + type + ": "
				+ MunitionsId + " just launched missile: " + missileId
				+ " towards launcher: " + enemyLauncherId);
	}

	public void showEnemyLaunchMissile(String MunitionsId, String missileId,
			String destination, int damage) {
		System.out.println("[" + Utils.getCurrentTime() + "] Launcher: "
				+ MunitionsId + " just launched missile: " + missileId
				+ " towards: " + destination
				+ " its about to cause damade of: " + damage);
	}

	public void showLauncherIsVisible(String id, boolean visible) {
		String str = visible ? "visible" : "hidden";
		System.out.println("[" + Utils.getCurrentTime() + "] Launcher: " + id
				+ " just turned " + str);
	}

	public void showMissInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		System.out.println("[" + Utils.getCurrentTime() + "] Iron Dome: "
				+ whoLaunchedMeId + " fired missile: " + id
				+ " but missed the missile: " + enemyMissileId);
	}

	public void showHitInterceptionMissile(String whoLaunchedMeId, String id,
			String enemyMissileId) {
		System.out.println("[" + Utils.getCurrentTime() + "] Iron Dome: "
				+ whoLaunchedMeId + " fired missile: " + id
				+ " and intercept succesfully the missile: " + enemyMissileId);
	}

	public void showEnemyHitDestination(String whoLaunchedMeId, String id,
			String destination, int damage) {
		System.out.println("[" + Utils.getCurrentTime() + "] Enemy Missile: "
				+ id + " HIT " + destination + ". the damage is: " + damage
				+ ". Launch by: " + whoLaunchedMeId);
	}

	public void showEnemyMissDestination(String whoLaunchedMeId, String id,
			String destination, String launchTime) {
		System.out.println("[" + Utils.getCurrentTime() + "] Enemy Missile: "
				+ id + " MISSED " + destination + " launch at: " + launchTime
				+ ". Launch by: " + whoLaunchedMeId);
	}

	public void showMissInterceptionLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId, String missileId) {
		System.out.println("[" + Utils.getCurrentTime() + "] " + type + ": "
				+ whoLaunchedMeId + " fired missile: " + missileId
				+ " but missed the Launcher: " + enemyLauncherId);
	}

	public void showMissInterceptionHiddenLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId) {
		System.out.println("[" + Utils.getCurrentTime() + "] " + type + ": "
				+ whoLaunchedMeId + " missed the Launcher: " + enemyLauncherId
				+ " because he is hidden");
	}

	public void showHitInterceptionLauncher(String whoLaunchedMeId,
			String type, String enemyLauncherId, String missileId) {
		System.out
				.println("[" + Utils.getCurrentTime() + "] " + type + ": "
						+ whoLaunchedMeId + " fired missile: " + missileId
						+ " and intercept succesfully the Launcher: "
						+ enemyLauncherId);
	}

	// prints all war statistics
	public void showStatistics(long[] array) {
		StringBuilder msg = new StringBuilder();
		msg.append("\n[" + Utils.getCurrentTime() + "]"
				+ "\t\t   War Statistics\n");
		msg.append("\t\t\t=========================================\n");
		msg.append("\t\t\t||\tNum of launch missiles: " + array[0] + "\t||\n");
		msg.append("\t\t\t||\tNum of intercept missiles: " + array[1]
				+ "\t||\n");
		msg.append("\t\t\t||\tNum of hit target missiles: " + array[2]
				+ "\t||\n");
		msg.append("\t\t\t||\tNum of launchers destroyed: " + array[3]
				+ "\t||\n");
		msg.append("\t\t\t||\ttotal damage: " + array[4] + "\t\t||\n");
		msg.append("\t\t\t==========================================\n");
		System.out.println(msg.toString());
	}

	public void showWarHasBeenFinished() {
		for (WarEventUIListener l : allListeners) {
			l.showStatistics();
		}

		System.out.println("[" + Utils.getCurrentTime()
				+ "] =========>> Finally THIS WAR IS OVER!!! <<=========");
		// System.out.println("[" + Utils.getCurrentTime() + "]");
	}

	public void showWarHasBeenStarted() {
		System.out.println("[" + Utils.getCurrentTime()
				+ "] =========>> War has been strated!!! <<=========");
		// System.out.println("[" + Utils.getCurrentTime() + "]");
	}

	public void showNoSuchObject(String type) {
		System.out.println("[" + Utils.getCurrentTime()
				+ "] ERROR: Cannot find " + type + " you selected in war");
	}

	public void showMissileNotExist(String defenseLauncherId, String enemyId) {
		System.out.println("[" + Utils.getCurrentTime() + "] ERROR: "
				+ defenseLauncherId + " tried to intercept, " + "but missed: "
				+ enemyId + " doesn't exist!");
	}

	public void showLauncherNotExist(String defenseLauncherId, String launcherId) {
		System.out.println("[" + Utils.getCurrentTime() + "] ERROR: "
				+ defenseLauncherId + " tried to intercept, " + "but missed: "
				+ launcherId + " doesn't exist!");
	}

	public void start() {
		createFrame();
	}

}
