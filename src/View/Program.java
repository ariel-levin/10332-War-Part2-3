package View;

import java.io.IOException;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;
import War.War;
import War.WarControl;


public class Program {

	public static void main(String[] args) {
		WarXMLReader warXML;

		ConsoleView view = new ConsoleView();
		War warModel = new War();

		WarControl warControl = new WarControl(warModel, view);

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
