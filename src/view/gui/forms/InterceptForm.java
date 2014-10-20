package view.gui.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import view.GuiView;
import view.gui.panels.IronDomePanel;


public class InterceptForm extends MunitionForm {

	static final long serialVersionUID = 1L;
	
	private JComboBox<String> cbMissiles;
	

	public InterceptForm(IronDomePanel ironDomePanel, GuiView guiView) {
		super(ironDomePanel, guiView);
		setTitle("Intercept a Missile");
		setSize(new Dimension(300,120));
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.PAGE_AXIS));
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel lblMissile = new JLabel("Missile to Intercept");
		lblMissile.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblMissile);
		pnlMain.add(Box.createRigidArea(new Dimension(0,5)));
		String[] arrMissiles = super.guiView.getMissileToIntercept().toArray(new String[0]);
		cbMissiles = new JComboBox<String>(arrMissiles);
		pnlMain.add(cbMissiles);	

		pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
		
		JButton btnIntercept = new JButton("Intercept");
		btnIntercept.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				interceptMissile();
			}
		});
		
		add(btnIntercept, BorderLayout.SOUTH);
		
		add(pnlMain, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.EAST);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.WEST);
		
		setVisible(true);
	}
	
	private IronDomePanel getIronDomePanel() {
		return (IronDomePanel)super.munitionPanel;
	}
	
	private void interceptMissile() {
		getIronDomePanel().addTarget(cbMissiles.getSelectedItem().toString());
		dispose();
	}
	
}

