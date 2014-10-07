package View.Gui.sections;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import View.GuiView;
import View.Gui.panels.*;


public class SectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected GuiView guiView;
	protected JButton btnAddMunition;
	protected ArrayList<MunitionPanel> munitionArr = new ArrayList<MunitionPanel>();
	
	private JPanel innerPanel;
	
	
	public SectionPanel(GuiView guiView, String name) {

		this.guiView = guiView;

		setLayout(new BorderLayout());
		
		innerPanel = new JPanel();
		innerPanel.setLayout(new GridLayout(0, 2, 10, 10));
		
		JScrollPane scroller = new JScrollPane(innerPanel);
		scroller.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroller, BorderLayout.CENTER);
		
		setBorder(BorderFactory.createTitledBorder(name + "s"));
		
		this.btnAddMunition = new JButton("Add " + name);
		this.add(btnAddMunition, BorderLayout.NORTH);
	}
	
	public void displayMunition(MunitionPanel munition) {
		munitionArr.add(munition);
		innerPanel.add(munition);
//		munition.setSectionPanel(this);
		validate();
		repaint();
	}
	
}

