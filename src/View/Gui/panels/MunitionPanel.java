package View.Gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import View.GuiView;
import View.Gui.sections.*;
import View.Gui.utils.ImageUtils;


public class MunitionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected GuiView guiView;
	protected SectionPanel sectionPanel;
	protected String id;
	protected JLabel munitionNameAndIcon;
	protected JButton btnAction;
	protected JLabel status;
	
	
	public MunitionPanel(String id, SectionPanel sectionPanel, String icon,
			String btnStr, GuiView guiView) {
		
		this.id = id;
		this.sectionPanel = sectionPanel;
		this.guiView = guiView;
		
		setLayout(new BorderLayout());
		initLabelAndIcon(icon);
		add(this.munitionNameAndIcon, BorderLayout.CENTER);
		
		this.btnAction = new JButton(btnStr);
		add(this.btnAction, BorderLayout.SOUTH);
		setPreferredSize(new Dimension(150, 170));
		
	}
	
	private void initLabelAndIcon(String icon) {
		this.munitionNameAndIcon = new JLabel();
		this.munitionNameAndIcon.setText(this.id);
		setIcon(icon);
		this.munitionNameAndIcon.setHorizontalAlignment(SwingConstants.CENTER);
		this.munitionNameAndIcon.setBorder(BorderFactory.createEtchedBorder());
		this.munitionNameAndIcon.setVerticalTextPosition(SwingConstants.TOP);
		this.munitionNameAndIcon.setHorizontalTextPosition(JLabel.CENTER);
		this.munitionNameAndIcon.setPreferredSize(new Dimension(70, 80));
	}
	
	protected void setIcon(String icon) {
		this.munitionNameAndIcon.setIcon(ImageUtils.getImageIcon(icon));
	}
	
	public void setSectionPanel(SectionPanel sectionPanel) {
		this.sectionPanel = sectionPanel;
	}

	public String getId() {
		return id;
	}
	
	public void formClosed() {
		btnAction.setEnabled(true);
	}
	
}

