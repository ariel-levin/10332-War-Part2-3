package View.Gui.forms;

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

import View.GuiView;
import View.Gui.panels.LauncherDestructorPanel;


public class DestroyForm extends MunitionForm {

	static final long serialVersionUID = 1L;
	
	private JComboBox<String> cbLaunchers;
	

	public DestroyForm(LauncherDestructorPanel destructorPanel, GuiView guiView) {
		super(destructorPanel, guiView);
		setTitle("Destroy a Missile");
		setSize(new Dimension(300,120));
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.PAGE_AXIS));
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel lblLauncher = new JLabel("Launcher to Destroy");
		lblLauncher.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblLauncher);
		pnlMain.add(Box.createRigidArea(new Dimension(0,5)));
		String[] arrLaunchers = super.guiView.getLauncherToIntercept().toArray(new String[0]);
		cbLaunchers = new JComboBox<String>(arrLaunchers);
		pnlMain.add(cbLaunchers);
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,10)));

		JButton btnDestroy = new JButton("Destroy");
		btnDestroy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destroyLauncher();
			}
		});
		
		add(btnDestroy, BorderLayout.SOUTH);
		
		add(pnlMain, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.EAST);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.WEST);
		
		setVisible(true);
	}
	
	private LauncherDestructorPanel getLauncherDestructorPanel() {
		return (LauncherDestructorPanel)super.munitionPanel;
	}
	
	private void destroyLauncher() {
		getLauncherDestructorPanel().addTarget(cbLaunchers.getSelectedItem().toString());
		dispose();
	}
	
}

