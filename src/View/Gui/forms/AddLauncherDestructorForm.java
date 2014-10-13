package View.Gui.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import View.GuiView;


public class AddLauncherDestructorForm extends JFrame {

	static final long serialVersionUID = 1L;
	
	private GuiView guiView;
	private JRadioButton rbShip, rbPlane;
	private ButtonGroup bGroup;

	
	public AddLauncherDestructorForm(GuiView guiView) {
		this.guiView = guiView;
		setTitle("Choose a Type");
		setSize(new Dimension(300, 100));
		
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		setLocationRelativeTo(null);
		setResizable(false);
		setAlwaysOnTop(true);

		getContentPane().setLayout(new BorderLayout());

		JPanel plnRadioButtons = new JPanel();
		plnRadioButtons.setLayout(new GridLayout(1, 2, 10, 10));
		rbShip = new JRadioButton("Ship");
		rbShip.setHorizontalAlignment(JLabel.CENTER);
		rbPlane = new JRadioButton("Plane");
		rbPlane.setHorizontalAlignment(JLabel.CENTER);
		plnRadioButtons.add(rbShip);
		plnRadioButtons.add(rbPlane);
		bGroup = new ButtonGroup();
		bGroup.add(rbShip);
		bGroup.add(rbPlane);
		rbShip.setSelected(true);
		add(plnRadioButtons, BorderLayout.CENTER);

		JButton btnLaunch = new JButton("Add");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addDestructor();
			}
		});

		add(btnLaunch, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void addDestructor() {

		java.util.Enumeration<javax.swing.AbstractButton> en = bGroup.getElements();
		while(en.hasMoreElements()) {
			JRadioButton rb = (JRadioButton)en.nextElement();
			if (rb.isSelected()) {
				String type = rb.getText().toLowerCase();
				this.guiView.fireAddDefenseLauncherDestructor(type);
			}
		}
		
		dispose();
	}

}

