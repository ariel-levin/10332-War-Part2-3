package View.Gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

import View.GuiView;
import View.Gui.forms.InterceptForm;
import View.Gui.sections.SectionPanel;


public class IronDomePanel extends MunitionPanel {

	private static final long serialVersionUID = 1L;

	private static final String DOME_IMAGE = "../images/dome.jpg";
	private static final String DOMEINT_IMAGE = "../images/dome-intercept.jpg";

	
	public IronDomePanel(String id, SectionPanel sectionPanel, GuiView guiView) {
		
		super(id, sectionPanel, DOME_IMAGE, "Intercept", guiView);

		super.btnAction.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createInterceptForm();
			}
		});
	}

	private void createInterceptForm() {
		if (super.guiView.getMissileToIntercept() == null)
			JOptionPane.showMessageDialog(null,"No Missiles On-Air available","Error",JOptionPane.ERROR_MESSAGE);
		else {
			super.btnAction.setEnabled(false);
			new InterceptForm(this, super.guiView);
		}
	}

	public void addTarget(String missileID) {
		super.guiView.fireInterceptMissile(super.id, missileID);
	}
	
	public void interceptMissile(String missileID) {
		super.setIcon(DOMEINT_IMAGE);
		
	}

	public void interceptDone() {
		super.setIcon(DOME_IMAGE);
		super.btnAction.setEnabled(true);
	}
	
}

