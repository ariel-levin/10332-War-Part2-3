package View.Gui.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import Listeners.WarEventUIListener;
import View.Gui.panels.IronDomePanel;
import View.Gui.utils.SpringUtilities;


public class InterceptForm extends MunitionForm {

	static final long serialVersionUID = 1L;
	
	private JComboBox<String> cbMissiles;
	

	public InterceptForm(IronDomePanel ironDomePanel, List<WarEventUIListener> allListeners) {
		super(ironDomePanel,allListeners);
		setTitle("Intercept a Missile");
		setSize(new Dimension(300,120));
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlMain = new JPanel(new SpringLayout());
		
		JLabel lblMissile = new JLabel("Missile to Intercept");
		lblMissile.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblMissile);
//		String[] arrMissiles = {"missile1","missile2","missile3","missile4","missile5"};
//		String[] arrMissiles = (String[])(allListeners.get(0).chooseMissileToIntercept().toArray());
		String[] arrMissiles = allListeners.get(0).chooseMissileToIntercept().toArray(new String[0]);
		cbMissiles = new JComboBox<String>(arrMissiles);
		pnlMain.add(cbMissiles);	

		JButton btnIntercept = new JButton("Intercept");
		btnIntercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interceptMissile();
			}
		});
		
		add(btnIntercept, BorderLayout.SOUTH);
		
		SpringUtilities.makeCompactGrid(pnlMain,
                2, 1, 			//rows, cols
                6, 6,        	//initX, initY
                6, 6);       	//xPad, yPad
		
		
		add(pnlMain, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private IronDomePanel getIronDomePanel() {
		return (IronDomePanel)super.munitionPanel;
	}
	
	private void interceptMissile() {
		getIronDomePanel().interceptMissile(cbMissiles.getSelectedItem().toString());

		dispose();
	}
	
	
	// for test
//	public static void main(String[] args) {
//		new InterceptForm(null,null);
//	}
	
}
