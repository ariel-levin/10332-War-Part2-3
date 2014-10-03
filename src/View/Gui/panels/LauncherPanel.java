package View.Gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Listeners.WarEventUIListener;
import View.Gui.utils.*;
import View.Gui.forms.*;


public class LauncherPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String LAUNCHER_IMAGE = "../images/launcher.jpg";
	private static final String LAUNCHERMIS_IMAGE = "../images/launcher-launch.jpg";
	private static final String LAUNCHERDES_IMAGE = "../images/launcher-destroyed.jpg";
	
	
//	public LauncherPanel(String id, SectionPanel sectionPanel) {
	public LauncherPanel(String id) {	// for test
		super();
		super.id = id;
		super.sectionPanel = sectionPanel;
		
		setLayout(new BorderLayout());
		initLabelAndIcon();
		add(super.munitionNameAndIcon, BorderLayout.CENTER);
		
		super.btnAction = new JButton("Launch");
		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createLaunchForm();
			}
		});
		
		add(super.btnAction, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(150, 170));
		
	}

	private void initLabelAndIcon() {
		super.munitionNameAndIcon = new JLabel();
		super.munitionNameAndIcon.setText(super.id);
		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(LAUNCHER_IMAGE));
		super.munitionNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		super.munitionNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		super.munitionNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		super.munitionNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		super.munitionNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}
	
	private void createLaunchForm() {
		super.btnAction.setEnabled(false);
		Form form = new LaunchForm(this);
		for(WarEventUIListener l : super.allListeners)
			form.registerListener(l);
	}
	
	public void launchMissile(String destination, int flyTime, int damage) {
		
		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(LAUNCHERMIS_IMAGE));
		super.btnAction.setEnabled(true);
		
	}
	
	public void launchDone() {
		
		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(LAUNCHER_IMAGE));
		
	}
	
	public void launcherDestroyed() {
		
		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(LAUNCHERDES_IMAGE));
		super.btnAction.setEnabled(false);
		
	}
	
	// for test
	public static void main(String[] args) {
		javax.swing.JFrame frame = new javax.swing.JFrame();
		frame.add(new LauncherPanel("123"));
		frame.setSize(300, 300);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
}

