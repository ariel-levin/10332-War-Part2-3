package View.Gui.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import View.GuiView;
import View.Gui.panels.LauncherDestructorPanel;
import View.Gui.utils.SpringUtilities;


public class DestroyForm extends MunitionForm {

	static final long serialVersionUID = 1L;
	
	private JComboBox<String> cbLaunchers;
	

	public DestroyForm(LauncherDestructorPanel destructorPanel, GuiView guiView) {
		super(destructorPanel, guiView);
		setTitle("Destroy a Missile");
		setSize(new Dimension(300,120));
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlMain = new JPanel(new SpringLayout());
		
		JLabel lblLauncher = new JLabel("Launcher to Destroy");
		lblLauncher.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblLauncher);
		String[] arrLaunchers = super.guiView.getLauncherToIntercept().toArray(new String[0]);
		cbLaunchers = new JComboBox<String>(arrLaunchers);
		pnlMain.add(cbLaunchers);

		JButton btnDestroy = new JButton("Destroy");
		btnDestroy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				destroyLauncher();
			}
		});
		
		add(btnDestroy, BorderLayout.SOUTH);
		
		SpringUtilities.makeCompactGrid(pnlMain,
                2, 1, 			//rows, cols
                6, 6,        	//initX, initY
                6, 6);       	//xPad, yPad
		
		
		add(pnlMain, BorderLayout.CENTER);
		
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

