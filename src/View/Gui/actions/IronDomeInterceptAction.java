package View.Gui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import View.Gui.*;


public class IronDomeInterceptAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	
	private IronDomePanel ironDomePanel;

	public IronDomeInterceptAction(IronDomePanel ironDomePanel) {
		
		super("Intercept");
		this.ironDomePanel = ironDomePanel;
	}

	// VER 4: the BEST solution
	@Override
	public void actionPerformed(ActionEvent arg) {
		int result = JOptionPane.showConfirmDialog(ironDomePanel,
				"Are you sure you want to move to the other tribe?", "Move?",
				JOptionPane.YES_NO_OPTION);
		if (result == JOptionPane.YES_OPTION) {
			setEnabled(false); // here still in the EDT
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.currentThread().sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					// switch back to the EDT (swing thread)
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
//							arg.getSource()
							
							
//							launcherPanel.getTribePanel().moveSurvivorToOtherTribe(launcherPanel);
							
							setEnabled(true);
							
							
						}
					});
				}
			}).start();
		}
	} 
}

