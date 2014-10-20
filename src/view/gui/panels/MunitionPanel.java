package view.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import view.GuiView;
import view.gui.sections.*;
import view.gui.utils.ImageUtils;


public class MunitionPanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	
	protected GuiView guiView;
	protected SectionPanel sectionPanel;
	protected String id;
	protected JLabel munitionNameAndIcon, status;
	protected JButton btnAction;
	
	
	public MunitionPanel(String id, SectionPanel sectionPanel, String icon,
			String btnStr, GuiView guiView) {
		
		this.id = id;
		this.sectionPanel = sectionPanel;
		this.guiView = guiView;
		
		setLayout(new BorderLayout());
		initLabelAndIcon(icon);
		add(this.munitionNameAndIcon, BorderLayout.CENTER);
		
		JPanel lowerPanel = new JPanel(new GridLayout(2, 1, 5, 5));
		this.status = new JLabel("Free");
		this.status.setHorizontalAlignment(JLabel.CENTER);
		this.status.setFont(status.getFont().deriveFont(Font.PLAIN,11));
		this.btnAction = new JButton(btnStr);
		lowerPanel.add(status);
		lowerPanel.add(btnAction);
		add(lowerPanel, BorderLayout.SOUTH);
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
	
	protected void setTitle(String text) {
		this.munitionNameAndIcon.setText(text);
	}
	
	protected void setStatus(String text) {
		this.status.setText(text);
	}
	
	public void setSectionPanel(SectionPanel sectionPanel) {
		this.sectionPanel = sectionPanel;
	}

	public String getId() {
		return id;
	}
	
	public void formClosed() {
		this.btnAction.setEnabled(true);
		setStatus("Free");
	}
	
}

