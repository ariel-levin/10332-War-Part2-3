package clientserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.DefaultCaret;

import war.War;


public class Server extends Thread {

	private String ip;
	private int port;
	private JFrame frame;
	private War warModel;
	private ServerSocket server;
	private JTextArea console;
	private boolean alive;
	
	
	public Server (String ip, int port, War war) {
		this.ip = ip;
		this.port = port;
		this.warModel = war;
		createFrame();
		alive = true;
	}
	
	public void run() {
		
		try {
			server = new ServerSocket(port);
		} catch (IOException e2) {
			e2.printStackTrace();
		}
		console.append("\n" + new Date() + " >> The Server is waiting for clients...");

		while (alive) {

			try {
				final Socket client = server.accept();	// blocking

				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							String clientAddress = client.getInetAddress() + ":" + client.getPort();
							console.append("\n" + new Date() + " >> Client connected from " + clientAddress);

							ObjectInputStream input = new ObjectInputStream(client.getInputStream());
							ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());

							// client connection success
							output.writeObject(new Protocol(Protocol.Subject.SUCCESS));

							// send war destinations
							Protocol sendDest = new Protocol(Protocol.Subject.WAR_DESTINATIONS);
							sendDest.setData(warModel.getAllTargetCities());
							output.writeObject(sendDest);

							Protocol request = null;
							do {
								request = (Protocol) input.readObject();
								handleRequest(request, client, clientAddress);
								
								// if not a disconnect request, send confirmation
								if (!request.getSubject().equals(Protocol.Subject.DISCONNECT))
									output.writeObject(new Protocol(Protocol.Subject.SUCCESS));

							} while (!request.getSubject().equals(Protocol.Subject.DISCONNECT));

						} catch (IOException | ClassNotFoundException e) {
							System.err.println(e);

						} 					
					}

				}).start();

			} catch (IOException e1) {} 

		}
		
	}
	
	private void createFrame() {
		frame = new JFrame();
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(frame);
		} catch (Exception e1) {}

		frame.setTitle("Server - " + this.ip + " - Port " + this.port);
		
		// set the frame's Close operation
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				Server.this.alive = false;
				
				try {
					Server.this.server.close();
				} catch (IOException | IllegalMonitorStateException e1) {}
				
				Server.this.warModel.serverClosed();
			}
		});

		Dimension frameSize = new Dimension();
		frameSize.setSize(550,200);
		frame.setSize(frameSize);
		
		console = new JTextArea();
		console.setFont(console.getFont().deriveFont(Font.PLAIN,12));
		console.setEditable(false);

		DefaultCaret caret = (DefaultCaret)console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(console);
		scrollPane.setPreferredSize(new Dimension(frame.getWidth()-20, 150));
		
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		frame.setLocationRelativeTo(null);
		frame.setAlwaysOnTop(true);
		frame.setVisible(true);
	}

	private void handleRequest(Protocol request, Socket client, String clientAddress) {

		switch (request.getSubject()) {
		
			case ADD_LAUNCHER:		addNewLauncher(clientAddress);
									break;
									
			case LAUNCH_MISSILE:	Object[] obj = request.getData();
									launchMissile(	(String)obj[0], (int)obj[1], 
													(int)obj[2], clientAddress	);
									break;
									
			case DISCONNECT:		disconnectClient(client, clientAddress);
									break;
			
			default:				break;
		}
	}
	
	private void addNewLauncher(String clientAddress) {
		warModel.addEnemyLauncher();
		console.append("\n" + new Date() + " >> Recieved 'Add New Launcher' request "
				+ "\n\t\t\t\t>> from client " + clientAddress);
	}
	
	private void launchMissile(String destination, int flyTime, int damage, String clientAddress) {

		warModel.launchEnemyMissile(destination, damage, flyTime);
		
		console.append("\n" + new Date() + " >> Recieved 'Launch Missile' request "
				+ "\n\t\t\t\t>> from client " + clientAddress);
	}
	
	private void disconnectClient(Socket client, String clientAddress) {
		try {
			client.close();
			console.append(	"\nThe client from " + clientAddress + " is now disconnected");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	
	
	// for test
	public static void main(String[] args) {
		new Server("localhost", 7000, null);

	}
	
}
