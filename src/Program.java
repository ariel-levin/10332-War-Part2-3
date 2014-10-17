import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import View.*;
import War.*;


public class Program {

	public static void main(String[] args) {
		WarXMLReader warXML;

//		ConsoleView view = new ConsoleView();
//		AbstractWarView view = new ConsoleView();
		AbstractWarView view = new GuiView();
		
		War warModel = new War();

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
