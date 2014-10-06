package View.Gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

import View.Gui.utils.*;


public class CloseWindowAction implements ActionListener {
	private JFrame parent;
	
	public CloseWindowAction(JFrame parent) {
		this.parent = parent;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		CloseJFrameUtil.closeApplication(parent);
		
	}
}
