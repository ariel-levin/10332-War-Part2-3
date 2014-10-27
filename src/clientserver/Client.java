package clientserver;

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


/** 
 * @author	<a href="http://about.me/ariel.levin">Ariel Levin</a><br>
 * 			<a href="mailto:ariel2011@gmail.com">ariel2011@gmail.com</a><br>
 *			<a href="http://github.com/ariel-levin">github.com/ariel-levin</a>
 * */
public class Client extends Application {

	private Stage stage;
	private RadioButton rbAddLauncher, rbLaunchMissile;
	private Label lblInfo, lblFly, lblDmg;
	private ComboBox<String> cbDest;
	private Slider sliderFly, sliderDmg;
	private Button btnConnect, btnDisconnect, btnSend;
	private boolean isConnected = false;
	private ClientConnection connection;
	

	public static void main(String[] args) {
		launch(args);

	}
	
	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		connection = new ClientConnection();
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
			public void changed(ObservableValue<? extends Number> ov,
					Number oldValue, Number newValue) {
				
				lblFly.setText("Fly time - " + newValue.intValue());
			}
		});
		launchPane.getChildren().add(sliderFly);

		lblDmg = new Label("Damage - 1000");
		launchPane.getChildren().add(lblDmg);
		sliderDmg = setSilder(5000, 1000, 500, 100);
		sliderDmg.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> ov,
					Number oldValue, Number newValue) {
				
				lblDmg.setText("Damage - " + newValue.intValue());
			}
		});
		launchPane.getChildren().add(sliderDmg);

		FlowPane btnPane = new FlowPane(Orientation.HORIZONTAL);
		btnPane.setColumnHalignment(HPos.CENTER);
		btnPane.setAlignment(Pos.CENTER);
		btnPane.setHgap(60);
		
		btnConnect = new Button("Connect");
		setConnectAction();
		btnPane.getChildren().add(btnConnect);
		
		btnSend = new Button("Send");
		setSendAction();
		btnPane.getChildren().add(btnSend);
		
		btnDisconnect = new Button("Disconnect");
		btnDisconnect.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				if (isConnected) {
					boolean success = connection.disconnect();

					if (success) {
						isConnected = false;
						lblInfo.setText("Disconnected");
						stage.setTitle("Enemy Client - Disconnected");
						setComponentsActive();
						
					} else
						lblInfo.setText("Failed to disconnect from server...");
				}
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
				connection.disconnect();
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
	
	private void setConnectAction() {
		btnConnect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				if (!isConnected) {

					boolean success = connection.connect();

					if (success) {

						if (cbDest.getItems().isEmpty()) {
							String[] cities = connection
									.getDestinationsFromServer();

							if (cities == null) {
								success = false;
							} else {
								for (String city : cities)
									cbDest.getItems().addAll(city);
								cbDest.setValue(cities[0]);
							}
						}
					}

					if (success) {
						isConnected = true;
						lblInfo.setText("Connected to '"
								+ Client.this.connection.getServerIP()
								+ "' , Port "
								+ Client.this.connection.getServerPort());
						stage.setTitle("Enemy Client - Connected");
						setComponentsActive();

					} else
						lblInfo.setText("Failed to connect to server...");
				}
			}
		});
	}

	private void setSendAction() {
		btnSend.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {

				if (isConnected) {

					if (rbAddLauncher.isSelected()) {

						boolean success = connection.addLauncher();

						if (success)
							lblInfo.setText("Request to Add Launcher Succeeded");
						else
							lblInfo.setText("Request to Add Launcher failed...");

					} else if (rbLaunchMissile.isSelected()) {

						String dest = cbDest.getValue();
						int flyTime = (int) sliderFly.getValue();
						int dmg = (int) sliderDmg.getValue();

						boolean success = connection.launchMissile(dest,
								flyTime, dmg);

						if (success)
							lblInfo.setText("Request to Launch Missile Succeeded");
						else
							lblInfo.setText("Request to Launch Missile failed...");
					
					} // if rb
					
				} // if isConnected
				
			} // handle
		});
	}
		
	private void setLaunchEnable(boolean enable) {
		this.sliderFly.setDisable(!enable);
		this.sliderDmg.setDisable(!enable);
		this.cbDest.setDisable(!enable);
	}

	public void setComponentsActive() {
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

}
