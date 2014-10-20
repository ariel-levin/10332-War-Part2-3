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
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import view.GuiView;
import view.gui.panels.LauncherPanel;


public class LaunchForm extends MunitionForm {

	static final long serialVersionUID = 1L;
	
	private final int MIN_FLY = 5;
	private final int MAX_FLY = 100;
	private final int MIN_DMG = 1000;
	private final int MAX_DMG = 5000;
	
	private JComboBox<String> cbDest;
	private JSlider sliderFly;
	private JSlider sliderDmg;
	

	public LaunchForm(LauncherPanel launcherPanel, GuiView guiView) {
		super(launcherPanel, guiView);
		setTitle("Launch a Missile");
		setSize(new Dimension(350,270));
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlMain = new JPanel();
		pnlMain.setLayout(new BoxLayout(pnlMain, BoxLayout.PAGE_AXIS));
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel lblDest = new JLabel("Destination");
		lblDest.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblDest);
		pnlMain.add(Box.createRigidArea(new Dimension(0,5)));
		String[] arrCity = super.guiView.getAllWarDestinations();
		cbDest = new JComboBox<String>(arrCity);
		pnlMain.add(cbDest);
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,20)));
		
		JLabel lblFly = new JLabel("Fly Time - " + MIN_FLY);
		lblFly.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblFly);
		sliderFly = setSliderProperties(MIN_FLY,MAX_FLY,5,10);
		sliderFly.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblFly.setText("Fly Time - " + sliderFly.getValue());
			}
		});
		pnlMain.add(sliderFly);
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,20)));
		
		JLabel lblDmg = new JLabel("Damage - " + MIN_DMG);
		lblDmg.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblDmg);
		sliderDmg = setSliderProperties(MIN_DMG,MAX_DMG,100,500);
		sliderDmg.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblDmg.setText("Damage - " + sliderDmg.getValue());
			}
		});
		pnlMain.add(sliderDmg);
		
		pnlMain.add(Box.createRigidArea(new Dimension(0,20)));

		JButton btnLaunch = new JButton("Launch");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchMissile();
			}
		});
		
		add(btnLaunch, BorderLayout.SOUTH);
		add(pnlMain, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.EAST);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.WEST);
		
		setVisible(true);
	}
	
	private JSlider setSliderProperties(int min, int max, int minTick, int majTick) {
		JSlider slider = new JSlider(JSlider.HORIZONTAL);
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setPaintLabels(true);
		slider.setPaintTicks(true);
		slider.setMinorTickSpacing(minTick);
		slider.setMajorTickSpacing(majTick);
		slider.setValue(min);
		return slider;
	}
	
	private LauncherPanel getLauncherPanel() {
		return (LauncherPanel)super.munitionPanel;
	}
	
	private void launchMissile() {
		getLauncherPanel().addMissile
							(	cbDest.getSelectedItem().toString(),
								sliderFly.getValue(),
								sliderDmg.getValue()	);
		dispose();
	}
	
}

