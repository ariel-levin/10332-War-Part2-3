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
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Listeners.WarEventUIListener;
import View.Gui.panels.LauncherPanel;
import View.Gui.utils.SpringUtilities;


public class LaunchForm extends Form {

	static final long serialVersionUID = 1L;
	
	private final int MIN_FLY = 5;
	private final int MAX_FLY = 100;
	private final int MIN_DMG = 500;
	private final int MAX_DMG = 5000;
	
	private JComboBox<String> cbDest;
	private JSlider sliderFly;
	private JSlider sliderDmg;
	

	public LaunchForm(LauncherPanel launcherPanel, List<WarEventUIListener> allListeners) {
		super(launcherPanel,allListeners);
		setTitle("Launch a Missile");
		setSize(new Dimension(300,250));
		
		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlMain = new JPanel(new SpringLayout());
		
		JLabel lblDest = new JLabel("Destination");
		lblDest.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblDest);
//		String[] arrCity = {"city1","city2","city3","city4","city5"};
		String[] arrCity = super.allListeners.get(0).getAllWarDestinations();
		cbDest = new JComboBox<String>(arrCity);
		pnlMain.add(cbDest);
		
		JLabel lblFly = new JLabel("Fly Time - " + MIN_FLY);
		lblFly.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblFly);
		sliderFly = setSliderProperties(sliderFly,MIN_FLY,MAX_FLY,5,10);
		sliderFly.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblFly.setText("Fly Time - " + sliderFly.getValue());
			}
		});
		pnlMain.add(sliderFly);
		
		JLabel lblDmg = new JLabel("Damage - " + MIN_DMG);
		lblDmg.setHorizontalAlignment(JLabel.CENTER);
		pnlMain.add(lblDmg);
		sliderDmg = setSliderProperties(sliderDmg,MIN_DMG,MAX_DMG,100,500);
		sliderDmg.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent arg0) {
				lblDmg.setText("Damage - " + sliderDmg.getValue());
			}
		});
		pnlMain.add(sliderDmg);		

		JButton btnLaunch = new JButton("Launch");
		btnLaunch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				launchMissile();
			}
		});
		
		add(btnLaunch, BorderLayout.SOUTH);
		
		SpringUtilities.makeCompactGrid(pnlMain,
                6, 1, 			//rows, cols
                6, 6,        	//initX, initY
                6, 6);       	//xPad, yPad
		
		
		add(pnlMain, BorderLayout.CENTER);
		
		setVisible(true);
	}
	
	private JSlider setSliderProperties(JSlider slider, int min, int max, int minTick, int majTick) {
		slider = new JSlider(JSlider.HORIZONTAL);
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
		getLauncherPanel().launchMissile
							(	cbDest.getSelectedItem().toString(),
								sliderFly.getValue(),
								sliderDmg.getValue()	);
		dispose();
	}
	
	
	// for test
//	public static void main(String[] args) {
//		new LaunchForm();
//	}
	
}

