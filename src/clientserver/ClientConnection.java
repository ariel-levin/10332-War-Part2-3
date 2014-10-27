package clientserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class ClientConnection {

	private final String serverIP = "localhost";
	private final int serverPort = 7000;
	
	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;
	

	public boolean connect() {
		try {
			socket = new Socket(this.serverIP, this.serverPort);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			Protocol response = (Protocol) input.readObject();

			if (response.getSubject().equals(Protocol.Subject.SUCCESS))
				return true;
			
		} catch (IOException | ClassNotFoundException e) {
			return false;
		}
		return false;
	}
	
	public boolean disconnect() {
		try {
			Protocol req = new Protocol(Protocol.Subject.DISCONNECT);
			output.writeObject(req);
			
			output.close();
			input.close();
			socket.close();
			
			return true;
			
		} catch (IOException e) {
			return false;
		}
	}
	
	public String[] getDestinationsFromServer() {

		try {
			Protocol response = (Protocol) input.readObject();

			if (response.getSubject().equals(Protocol.Subject.WAR_DESTINATIONS)) {
				
				String[] cities = (String[])response.getData();
				return cities;
				
			} else
				return null;

		} catch (ClassNotFoundException | IOException e) {
			return null;
		}
	}
	
	public boolean addLauncher() {
		try {
			Protocol req = new Protocol(Protocol.Subject.ADD_LAUNCHER);
			output.writeObject(req);

			Protocol response = (Protocol) input.readObject();
			if (response.getSubject().equals(Protocol.Subject.SUCCESS))
				return true;

		} catch (IOException | ClassNotFoundException e) {
			return false;
		}
		return false;
	}
	
	public boolean launchMissile(String destination, int flyTime, int damage) {
		try {
			Protocol req = new Protocol(Protocol.Subject.LAUNCH_MISSILE,
					destination, flyTime, damage);

			output.writeObject(req);

			Protocol response = (Protocol) input.readObject();
			if (response.getSubject().equals(Protocol.Subject.SUCCESS))
				return true;

		} catch (IOException | ClassNotFoundException e) {
			return false;
		}
		return false;
	}

	
	public String getServerIP() {
		return serverIP;
	}
	

	public int getServerPort() {
		return serverPort;
	}

}
