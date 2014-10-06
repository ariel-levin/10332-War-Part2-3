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
import View.Gui.forms.InterceptForm;
import View.Gui.sections.SectionPanel;
import View.Gui.utils.ImageUtils;


public class IronDomePanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String DOME_IMAGE = "../images/dome.jpg";
	private static final String DOMEINT_IMAGE = "../images/dome-intercept.jpg";

	
	public IronDomePanel(String id, SectionPanel sectionPanel,
			List<WarEventUIListener> allListeners) {
		
		super(id, sectionPanel, allListeners);

		setLayout(new BorderLayout());
		initLabelAndIcon();
		add(super.munitionNameAndIcon, BorderLayout.CENTER);

		super.btnAction = new JButton("Intercept");
		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createInterceptForm();
			}
		});

		add(super.btnAction, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(150, 170));

	}

	private void initLabelAndIcon() {
		super.munitionNameAndIcon = new JLabel();
		super.munitionNameAndIcon.setText(super.id);
		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(DOME_IMAGE));
		super.munitionNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		super.munitionNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		super.munitionNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		super.munitionNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		super.munitionNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}

	private void createInterceptForm() {
		if (super.allListeners.get(0).chooseMissileToIntercept() == null)
			JOptionPane.showMessageDialog(null,"No Missiles On-Air available","Error",JOptionPane.ERROR_MESSAGE);
		else {
			super.btnAction.setEnabled(false);
			new InterceptForm(this, super.allListeners);
		}
	}

	public void interceptMissile(String missileID) {

		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(DOMEINT_IMAGE));
		
		for (WarEventUIListener l : super.allListeners)
			l.interceptGivenMissile(super.id, missileID);

	}

	public void interceptDone() {

		super.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(DOME_IMAGE));
		
		super.btnAction.setEnabled(true);

	}
	
}

