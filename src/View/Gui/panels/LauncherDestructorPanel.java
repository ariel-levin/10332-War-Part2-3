package View.Gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import Listeners.WarEventUIListener;
import View.Gui.forms.DestroyForm;
import View.Gui.sections.SectionPanel;
import View.Gui.utils.ImageUtils;


public class LauncherDestructorPanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;
	
	private static final String SHIP_IMAGE = "../images/ship.jpg";
	private static final String SHIPDES_IMAGE = "../images/ship-destroy.jpg";
	
	private static final String PLANE_IMAGE = "../images/plane.jpg";
	private static final String PLANEDES_IMAGE = "../images/plane-destroy.jpg";
	
	private String type;
	
	
	public LauncherDestructorPanel(String id, String type,
			SectionPanel sectionPanel, List<WarEventUIListener> allListeners) {
		
		super(id, sectionPanel, allListeners);
		this.type = type;
		
		setLayout(new BorderLayout());
		initLabelAndIcon();
		add(super.munitionNameAndIcon, BorderLayout.CENTER);

		super.btnAction = new JButton("Destroy");
		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createDestroyForm();
			}
		});

		add(super.btnAction, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(150, 170));

	}

	private void initLabelAndIcon() {
		super.munitionNameAndIcon = new JLabel();
		super.munitionNameAndIcon.setText(super.id);
		
		if (type.toLowerCase().compareTo("ship") == 0)
			super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(SHIP_IMAGE));
		else
			super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(PLANE_IMAGE));
		
		super.munitionNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		super.munitionNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		super.munitionNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		super.munitionNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		super.munitionNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}

	private void createDestroyForm() {
		if (super.allListeners.get(0).chooseLauncherToIntercept() == null)
			JOptionPane.showMessageDialog(null,"No visible Launchers available","Error",JOptionPane.ERROR_MESSAGE);
		else {
			super.btnAction.setEnabled(false);
			new DestroyForm(this, super.allListeners);
		}
	}

	public void destroyLauncher(String launcherID) {

		if (type.toLowerCase().compareTo("ship") == 0)
			super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(SHIPDES_IMAGE));
		else
			super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(PLANEDES_IMAGE));
		
		for (WarEventUIListener l : super.allListeners)
			l.interceptGivenLauncher(super.id, launcherID);

	}

	public void destroyDone() {

		if (type.toLowerCase().compareTo("ship") == 0)
			super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(SHIP_IMAGE));
		else
			super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(PLANE_IMAGE));
		
		super.btnAction.setEnabled(true);

	}

}
