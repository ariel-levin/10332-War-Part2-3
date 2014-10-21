package clientserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class Client extends Application {
	
	private final String serverIP = "localhost";
	private final int serverPort = 7000;
	
	private Stage stage;
	private RadioButton rbAddLauncher, rbLaunchMissile;
	private Label lblInfo, lblFly, lblDmg;
	private ComboBox<String> cbDest;
	private Slider sliderFly, sliderDmg;
	private Button btnConnect, btnDisconnect, btnSend;

	private Socket socket;
	private ObjectInputStream input;
	private ObjectOutputStream output;	
	private boolean isConnected = false;
	
	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		buildScene();
		setComponentsActive();
		
	}
	
	private void buildScene() {
		stage.setTitle("Enemy Client - Disconnected");

		FlowPane mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setColumnHalignment(HPos.CENTER);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(30);

		lblInfo = new Label("Disconnected");
		lblInfo.setStyle("-fx-font-weight: bold;" + "-fx-font-size: 14;");
		
		GridPane rbPane = new GridPane();
		rbPane.setAlignment(Pos.CENTER);
		rbPane.setHgap(10);
		rbPane.setVgap(10);

		ToggleGroup group = new ToggleGroup();
		rbAddLauncher = new RadioButton("Add New Enemy Launcher");
		rbAddLauncher.setToggleGroup(group);
		rbAddLauncher.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setLaunchEnable(false);
			}
		});
		rbAddLauncher.setSelected(true);
		rbLaunchMissile = new RadioButton("Launch a Missile");
		rbLaunchMissile.setToggleGroup(group);
		rbLaunchMissile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				setLaunchEnable(true);
			}
		});

		rbPane.add(rbAddLauncher, 0, 0);
		rbPane.add(rbLaunchMissile, 0, 1);

		VBox launchPane = new VBox(15);
		launchPane.setAlignment(Pos.CENTER);

		Label lblDest = new Label("Destination");
		launchPane.getChildren().add(lblDest);
		cbDest = new ComboBox<String>();
		cbDest.setDisable(true);
		launchPane.getChildren().add(cbDest);

		lblFly = new Label("Fly Time - 5");
		launchPane.getChildren().add(lblFly);
		sliderFly = setSilder(100, 5, 10, 5);
		sliderFly.valueProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		        lblFly.setText("Fly time - " + newValue.intValue());
		    }
		});
		launchPane.getChildren().add(sliderFly);

		lblDmg = new Label("Damage - 1000");
		launchPane.getChildren().add(lblDmg);
		sliderDmg  = setSilder(5000, 1000, 500, 100);
		sliderDmg.valueProperty().addListener(new ChangeListener<Number>() {
		    @Override
		    public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
		    	lblDmg.setText("Damage - " + newValue.intValue());
		    }
		});
		launchPane.getChildren().add(sliderDmg);

		FlowPane btnPane = new FlowPane(Orientation.HORIZONTAL);
		btnPane.setColumnHalignment(HPos.CENTER);
		btnPane.setAlignment(Pos.CENTER);
		btnPane.setHgap(60);
		
		btnConnect = new Button("Connect");
		btnConnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				connect();
			}
		});
		btnPane.getChildren().add(btnConnect);
		
		btnSend = new Button("Send");
		btnSend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				send();
			}
		});
		btnPane.getChildren().add(btnSend);
		
		btnDisconnect = new Button("Disconnect");
		btnDisconnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				disconnect();
			}
		});
		btnPane.getChildren().add(btnDisconnect);
		
		mainPane.getChildren().add(lblInfo);
		mainPane.getChildren().add(rbPane);
		mainPane.getChildren().add(launchPane);
		mainPane.getChildren().add(btnPane);

		stage.setScene(new Scene(mainPane, 450, 450));
		
		stage.show();
		
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				disconnect();
			}
		}); 
	}

	private Slider setSilder(int max, int min, int majTick, int minTick) {
		Slider slider = new Slider();
		slider.setMax(max);
		slider.setMin(min);
		slider.setMajorTickUnit(majTick);
		slider.setMinorTickCount(minTick);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.setValue(min);
		return slider;
	}
	
	private void setLaunchEnable(boolean enable) {
		this.sliderFly.setDisable(!enable);
		this.sliderDmg.setDisable(!enable);
		this.cbDest.setDisable(!enable);
	}

	private void setComponentsActive() {
		btnSend.setDisable(!isConnected);
		btnDisconnect.setDisable(!isConnected);
		btnConnect.setDisable(isConnected);
		rbAddLauncher.setDisable(!isConnected);
		rbLaunchMissile.setDisable(!isConnected);
		
		if (rbLaunchMissile.isSelected())
			setLaunchEnable(isConnected);
		else
			setLaunchEnable(false);
	}

	
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////


	private void connect() {
		if (isConnected)
			return;

		try {
			socket = new Socket(this.serverIP, this.serverPort);
			output = new ObjectOutputStream(socket.getOutputStream());
			input = new ObjectInputStream(socket.getInputStream());

			Protocol response = (Protocol) input.readObject();

			// client connection success
			if (response.getSubject().equals(Protocol.Subject.SUCCESS)) {
				isConnected = true;
				lblInfo.setText("Connected to '" + Client.this.serverIP + "' , Port " + Client.this.serverPort);
				stage.setTitle("Enemy Client - Connected");
				setComponentsActive();
			}

			response = (Protocol) input.readObject();
			
			// get war destinations
			if (response.getSubject().equals(Protocol.Subject.WAR_DESTINATIONS))
				setDestinationFromServer(response);
			
		} catch (IOException | ClassNotFoundException e) {
			lblInfo.setText("Failed to connect to server...");
		}
	}
	
	private void disconnect() {
		if (!isConnected)
			return;
		
		try {
			Protocol req = new Protocol(Protocol.Subject.DISCONNECT);
			output.writeObject(req);
			
			output.close();
			input.close();
			socket.close();
			isConnected = false;
			lblInfo.setText("Disconnected");
			stage.setTitle("Enemy Client - Disconnected");
			setComponentsActive();
			
		} catch (IOException e) {
			lblInfo.setText("Failed to disconnect from server...");
		}

	}
	
	private void setDestinationFromServer(Protocol response) {
		if (isConnected && cbDest.getItems().isEmpty()) {
			String[] cities = (String[])response.getData();
			for (String city : cities)
				cbDest.getItems().addAll(city);
			cbDest.setValue(cities[0]);
		}
	}
	
	private void send() {
		if (!isConnected)
			return;
		
		Protocol req = null;
		
		if (rbAddLauncher.isSelected()) {
			
			req = new Protocol(Protocol.Subject.ADD_LAUNCHER);
			
		} else if (rbLaunchMissile.isSelected()) {
			
			String dest = cbDest.getValue();
			int flyTime = (int)sliderFly.getValue();
			int dmg = (int)sliderDmg.getValue();
			
			req = new Protocol(Protocol.Subject.LAUNCH_MISSILE, dest, flyTime, dmg);
		}
		
		if (req != null) {
			try {
				output.writeObject(req);
				
				Protocol response = (Protocol) input.readObject();
				if (response.getSubject().equals(Protocol.Subject.SUCCESS)) {
					lblInfo.setText("Request Succeeded");
				} else
					throw new IOException();
				
			} catch (IOException | ClassNotFoundException e) {
				lblInfo.setText("Request failed...");
			}
		}
	}
	
	

	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////


	public static void main(String[] args) {
		launch(args);

	}

}
