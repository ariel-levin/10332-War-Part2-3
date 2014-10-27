import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import model.War;
import org.xml.sax.SAXException;

import controllers.*;
import utils.WarXMLReader;
import view.*;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class Program {

	public static void main(String[] args) {
		WarXMLReader warXML;

//		ConsoleView view = new ConsoleView();
//		AbstractWarView view = new ConsoleView();
		AbstractWarView view = new GuiView();
		
		War warModel = new War();
		view.showMessage("Starting War Management program\n"
						+ "Database system chosen in chooseDB.xml :\n"
						+ warModel.getDBtype());

		WarControl warControl = new WarControl(warModel, view);
		
		String warName = view.getWarNameFromUser();
		if (warName == null)
			System.exit(0);
		Boolean isSet = warModel.setWarName(warName);
		while (!isSet) {
			view.showWarNameTaken();
			warName = view.getWarNameFromUser();
			if (warName == null)
				System.exit(0);
			isSet = warModel.setWarName(warName);
		}
		view.setWarName(warName);

		try {
			warXML = new WarXMLReader("warStart.xml", warControl);
			warXML.start();

			warXML.join();

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		warModel.start();
		view.start();
		
	}

}
