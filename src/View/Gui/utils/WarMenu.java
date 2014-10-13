package View.Gui.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import View.*;
import View.Gui.forms.AddLauncherDestructorForm;


public class WarMenu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	private GuiView guiView;
	
	public WarMenu(GuiView guiView) {
		this.guiView = guiView;
		
		JMenu fileMenu = new JMenu("File");
		
		JMenuItem statisticsItem = new JMenuItem("Show Statistics");
		statisticsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarMenu.this.guiView.fireShowStatistics();
			}
		});
		fileMenu.add(statisticsItem);
		
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarMenu.this.guiView.fireShowStatistics();
				WarMenu.this.guiView.fireFinishWar();
			}
		});
		fileMenu.add(exitMenuItem);
		
		this.add(fileMenu);

		JMenu addMunition = new JMenu("Add");
		
		JMenuItem addLauncherItem = new JMenuItem("Add Enemy Launcher");
		addLauncherItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarMenu.this.guiView.fireAddEnemyLauncher();
			}
		});
		addMunition.add(addLauncherItem);
		
		JMenuItem addIronDomeItem = new JMenuItem("Add Iron Dome");
		addIronDomeItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarMenu.this.guiView.fireAddDefenseIronDome();
			}
		});
		addMunition.add(addIronDomeItem);
		
		JMenuItem addDestructorItem = new JMenuItem("Add Launcher Destructor");
		addDestructorItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new AddLauncherDestructorForm(WarMenu.this.guiView);
			}
		});
		addMunition.add(addDestructorItem);
		
		this.add(addMunition);
	}
	
}

