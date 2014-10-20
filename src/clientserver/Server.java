package clientserver;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
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


public class Server extends JFrame {

	private static final long serialVersionUID = 1L;

	private final int port = 7000;
	
	private War war;
	private JTextArea console;
	private boolean alive;
	
	
	public Server (War war) throws IOException {
		this.war = war;
		createFrame();
		alive = true;
		
		final ServerSocket server = new ServerSocket(port);
		console.append(new Date() + " --> Server waits for clients...");

		while (alive) {
			final Socket socket = server.accept(); // blocking

			new Thread(new Runnable() {
				@Override
				public void run() {
					String clientAddress = "";
					try {
						clientAddress = socket.getInetAddress() + ":" + socket.getPort();
						console.append(new Date() + " --> Client connected from " + clientAddress);
						DataInputStream inputStream = new DataInputStream(socket.getInputStream());
						PrintStream outputStream = new PrintStream(socket.getOutputStream());
						outputStream.println("Welcome to server!");

						String line = "";
						while (!line.equals("goodbye")) {
							line = inputStream.readLine();
							outputStream.println(line);
							console.append(new Date() + " --> Recieved from client " + clientAddress + ": " + line);
						}
					} catch (IOException e) {
						System.err.println(e);
					} finally {
						try {
							socket.close();
							server.close();
							console.append("The client from " + clientAddress + " is disconnected");
						} catch (IOException e) { // log and ignore
						}
					}
				} // run
			}).start();
		} // while
		
	}
	
	private void createFrame() {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e1) {}

		setTitle("Server - Port " + this.port);
		
		// set the frame's Close operation
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				alive = false;
				Server.this.war.serverClosed();
			}
		});

		Dimension frameSize = new Dimension();
		frameSize.setSize(550,200);
		setSize(frameSize);
		
		console = new JTextArea();
		console.setFont(console.getFont().deriveFont(Font.PLAIN,12));
		console.setEditable(false);

		DefaultCaret caret = (DefaultCaret)console.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setViewportView(console);
		scrollPane.setPreferredSize(new Dimension(this.getWidth()-20, 150));
		
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		setLocationRelativeTo(null);
//		setAlwaysOnTop(true);
		setVisible(true);
	}

	private void addNewLauncher() {
		
	}
	
	private void launchMissile(String destination, int flyTime, int damage) {
		
	}
	
	
	///////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////
	
	
	// for test
	public static void main(String[] args) {
		try {
			new Server(null);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
