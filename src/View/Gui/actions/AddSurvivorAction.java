package View.Gui.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import survivors.ui.TribePanel;

public class AddSurvivorAction extends AbstractAction /*implements ActionListener*/ {

	private TribePanel tribePanel;
	
	public AddSurvivorAction(TribePanel tribePanel) {
		super("Add Survivor");
		this.tribePanel = tribePanel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg) {
		String name = JOptionPane.showInputDialog(tribePanel, "Enter survivor's name");
		if (name.length() > 0)
			tribePanel.addSurvivor(name);
		else
			JOptionPane.showMessageDialog(tribePanel, "You have to enter a name");
	}
}
