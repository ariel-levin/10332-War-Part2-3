package view.gui.forms;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import view.GuiView;
import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class DateSelectionForm extends JFrame {

	static final long serialVersionUID = 1L;
	
	private GuiView guiView;
	private JDatePickerImpl startDatePicker, endDatePicker;

	
	public DateSelectionForm(GuiView guiView) {
		this.guiView = guiView;
		
		setTitle("Statistics by Date");
		setSize(new Dimension(250, 185));

		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {}
		
		setLocationRelativeTo(null);
		setResizable(false);
//		setAlwaysOnTop(true);

		getContentPane().setLayout(new BorderLayout());
		
		JPanel pnlDates = new JPanel();
		pnlDates.setLayout(new BoxLayout(pnlDates, BoxLayout.PAGE_AXIS));
		
		pnlDates.add(Box.createRigidArea(new Dimension(0,10)));
		
		JLabel lblStart = new JLabel("Start Date");
		lblStart.setHorizontalAlignment(JLabel.CENTER);
		pnlDates.add(lblStart);
		pnlDates.add(Box.createRigidArea(new Dimension(0,5)));
		UtilDateModel startModel = new UtilDateModel();
		JDatePanelImpl startPanel = new JDatePanelImpl(startModel);
		startDatePicker = new JDatePickerImpl(startPanel);
		pnlDates.add(startDatePicker);
		
		pnlDates.add(Box.createRigidArea(new Dimension(0,20)));
		
		JLabel lblEnd = new JLabel("End Date");
		lblEnd.setHorizontalAlignment(JLabel.CENTER);
		pnlDates.add(lblEnd);
		UtilDateModel endModel = new UtilDateModel();
		JDatePanelImpl endPanel = new JDatePanelImpl(endModel);
		endDatePicker = new JDatePickerImpl(endPanel);
		pnlDates.add(endDatePicker);
		
		pnlDates.add(Box.createRigidArea(new Dimension(0,20)));
		
		JButton btnAction = new JButton("Get Statistics");
		btnAction.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				action();
			}
		});
		
		add(btnAction, BorderLayout.SOUTH);
		add(pnlDates, BorderLayout.CENTER);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.EAST);
		add(Box.createRigidArea(new Dimension(10,0)), BorderLayout.WEST);
		
		setVisible(true);
	}

	private void action() {
		
		// start date
		Date date = (Date)startDatePicker.getModel().getValue();
		Calendar startDate = null;
		if (date != null) {
			startDate = Calendar.getInstance();
			startDate.setTime(date);
			startDate.set(Calendar.HOUR, 0);
			startDate.set(Calendar.MINUTE, 0);
			startDate.set(Calendar.SECOND, 0);
		}

		// end date
		date = (Date)endDatePicker.getModel().getValue();
		Calendar endDate = null;
		if (date != null) {
			endDate = Calendar.getInstance();
			endDate.setTime(date);
			endDate.set(Calendar.HOUR, 23);
			endDate.set(Calendar.MINUTE, 59);
			endDate.set(Calendar.SECOND, 0);
		}
		
		if (isDatesOK(startDate, endDate)) {
			this.guiView.fireShowStatisticsByDate(startDate, endDate);
			dispose();
		} else {
			JOptionPane.showMessageDialog(null,"Incorrect Dates, "
					+ "could be from the following reasons:\n"
					+ "- One or both of the dates are not selected\n"
					+ "- The End Date is before the Start Date\n"
					+ "- The Start Date is after now",
							"Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private boolean isDatesOK(Calendar startDate, Calendar endDate) {
		Date date = new java.util.Date();
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		
		if (startDate==null || endDate==null)
			return false;
		
		if (endDate.after(now))
			endDate.setTime(now.getTime());
		
		if (endDate.before(startDate) || startDate.after(now))
			return false;
		else
			return true;
	}
	
}

