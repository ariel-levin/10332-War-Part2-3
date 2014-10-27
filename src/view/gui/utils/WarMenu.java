package view.gui.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import clientserver.Client;
import view.*;
import view.gui.forms.AddLauncherDestructorForm;
import view.gui.forms.DateSelectionForm;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class WarMenu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	private GuiView guiView;
	
	
	public WarMenu(GuiView guiView) {
		this.guiView = guiView;
		
		JMenu fileMenu = new JMenu("File");
		
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
		
		/////////////////////////////////////////////////////////////////////////////////////
		
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
		
		/////////////////////////////////////////////////////////////////////////////////////		
		
		JMenu statMenu = new JMenu("Statistics");
		
		JMenuItem statisticsItem = new JMenuItem("Show Statistics");
		statisticsItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarMenu.this.guiView.fireShowStatistics();
			}
		});
		statMenu.add(statisticsItem);
		
		JMenuItem datesItem = new JMenuItem("Statistics by Dates");
		datesItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new DateSelectionForm(WarMenu.this.guiView);
			}
		});
		statMenu.add(datesItem);
		
		this.add(statMenu);
		
		/////////////////////////////////////////////////////////////////////////////////////

		JMenu serverMenu = new JMenu("Server");
		
		JMenuItem serverItem = new JMenuItem("Start Server");
		serverItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				WarMenu.this.guiView.startServer();
			}
		});
		serverMenu.add(serverItem);
		
		JMenuItem clientItem = new JMenuItem("New Client");
		clientItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				new Thread(new Runnable() {
					@Override
					public void run() {
						
						try {
							Client.main(null);
							
						} catch (java.lang.IllegalStateException e1) {
							JOptionPane.showMessageDialog(null,"Some JavaFX implementation error:\n"
									+ "Application launch must not be called more than once.\n"
									+ "If you wish to launch another client, please launch it from Eclipse"
									,"Client JavaFX Launch Error",JOptionPane.ERROR_MESSAGE);
						}
					}
				}).start();

			}
		});
		serverMenu.add(clientItem);
		
		this.add(serverMenu);
		
		/////////////////////////////////////////////////////////////////////////////////////

		JMenu helpMenu = new JMenu("Help");
		
		JMenuItem aboutItem = new JMenuItem("About");
		aboutItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String msg = 	"Ariel Levin\n" +
								"ariel2011@gmail.com\n" +
								"http://about.me/ariel.levin";
				
				JOptionPane.showMessageDialog(null,msg,"About War Management",JOptionPane.INFORMATION_MESSAGE);
			}
		});
		helpMenu.add(aboutItem);
		
		this.add(helpMenu);
		
	}
	
}

