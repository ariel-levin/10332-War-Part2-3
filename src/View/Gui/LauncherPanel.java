package View.Gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Utils.*;
import View.Gui.actions.LauncherLaunchAction;


public class LauncherPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String LAUNCHER_IMAGE = "/images/launcher.jpg";
	private static final String LAUNCHERMIS_IMAGE = "/images/launcher-launch.jpg";
	private static final String LAUNCHERDES_IMAGE = "/images/launcher-destroyed.jpg";
	
	private JLabel munitionNameAndIcon;
//	private LauncherLaunchAction launcherLaunchAction;

	
	public LauncherPanel(String id, SectionPanel sectionPanel) {
		setSectionPanel(sectionPanel);
		setLayout(new BorderLayout());
		initLabelAndIcon(id);
		add(munitionNameAndIcon, BorderLayout.CENTER);
		
//		launcherLaunchAction = new LauncherLaunchAction(this);
		btnAction = new JButton(new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		
	}
	
	private void initLabelAndIcon(String id) {
		munitionNameAndIcon = new JLabel();
		munitionNameAndIcon.setText(id);
		munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(LAUNCHER_IMAGE));
		munitionNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		munitionNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		munitionNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		munitionNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		munitionNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}
	
}
