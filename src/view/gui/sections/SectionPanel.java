package view.gui.sections;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import view.GuiView;
import view.gui.panels.*;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class SectionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	protected GuiView guiView;
	protected JButton btnAddMunition;
	protected ArrayList<MunitionPanel> munitionArr;
	
	private JPanel innerPanel;
	
	
	public SectionPanel(GuiView guiView, String name) {

		this.guiView = guiView;
		munitionArr = new ArrayList<MunitionPanel>();
		
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
	
	protected void displayMunition(MunitionPanel munition) {
		munitionArr.add(munition);
		innerPanel.add(munition);
		validate();
		repaint();
	}
	
	protected MunitionPanel findMunition(String id) {
		if (this.munitionArr!=null && id!=null) {
			for (MunitionPanel mp : this.munitionArr) {
				if (mp.getId().compareTo(id) == 0)
					return mp;
			}
		}
		return null;
	}
	
}

