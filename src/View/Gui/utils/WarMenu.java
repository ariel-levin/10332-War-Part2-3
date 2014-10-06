package View.Gui.utils;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import View.*;


public class WarMenu extends JMenuBar {
	
	private static final long serialVersionUID = 1L;
	private GuiView mainFrame;
	
	public WarMenu(GuiView mainFrame) {
		this.mainFrame = mainFrame;
		
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				CloseJFrameUtil.closeApplication(mainFrame);
			}
		});
		fileMenu.add(exitMenuItem);
		this.add(fileMenu);

		JMenu addSurvivor = new JMenu("Add");
		JMenuItem addToTribe1 = new JMenuItem("Add To Tribe 1");
//		addToTribe1.addActionListener(new AddSurvivorAction(mainFrame.getMainPanel().getTribe1()));
		
		JMenuItem addToTribe2 = new JMenuItem("Add To Tribe 2");
//		addToTribe2.addActionListener(new AddSurvivorAction(mainFrame.getMainPanel().getTribe2()));
		
		addSurvivor.add(addToTribe1);
		addSurvivor.add(addToTribe2);
		this.add(addSurvivor);
		
		
	}
}
