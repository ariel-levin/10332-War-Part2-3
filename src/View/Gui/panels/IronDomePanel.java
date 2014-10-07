package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;

import Listeners.WarEventUIListener;
import View.Gui.forms.InterceptForm;
import View.Gui.sections.SectionPanel;


public class IronDomePanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String DOME_IMAGE = "../images/dome.jpg";
	private static final String DOMEINT_IMAGE = "../images/dome-intercept.jpg";

	
	public IronDomePanel(String id, SectionPanel sectionPanel,
			List<WarEventUIListener> allListeners) {
		
		super(id, sectionPanel, DOME_IMAGE, "Intercept", allListeners);

		super.setButtonAction(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createInterceptForm();
			}
		});
	}

	private void createInterceptForm() {
		if (super.allListeners.get(0).chooseMissileToIntercept() == null)
			JOptionPane.showMessageDialog(null,"No Missiles On-Air available","Error",JOptionPane.ERROR_MESSAGE);
		else {
			super.btnEnable(false);
			new InterceptForm(this, super.allListeners);
		}
	}

	public void interceptMissile(String missileID) {

		super.setIcon(DOMEINT_IMAGE);
		
		for (WarEventUIListener l : super.allListeners)
			l.interceptGivenMissile(super.id, missileID);

	}

	public void interceptDone() {

		super.setIcon(DOME_IMAGE);
		super.btnEnable(true);
	}
	
}

