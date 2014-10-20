package clientserver;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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


public class Client extends Application {

	private Stage stage;
	private RadioButton rbAddLauncher, rbLaunchMissile;
	private Label lblFly, lblDmg;
	private ComboBox<String> cbDest;
	private Slider sliderFly, sliderDmg;

	
	@Override
	public void start(Stage stage) {
		this.stage = stage;
		buildScene();

	}
	

	private void buildScene() {
		stage.setTitle("Enemy Client");

		FlowPane mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setColumnHalignment(HPos.CENTER);
		mainPane.setAlignment(Pos.CENTER);
		mainPane.setVgap(30);

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
		String[] cities = {"test1","test2","test3"};
		ObservableList<String> listDest = FXCollections.observableArrayList(cities);
		cbDest = new ComboBox<String>(listDest);
		cbDest.setValue(cities[0]);
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
		
		Button btnConnect = new Button("Connect");
		btnConnect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				
			}
		});
		btnPane.getChildren().add(btnConnect);
		
		Button btnSend = new Button("Send");
		btnSend.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				
			}
		});
		btnPane.getChildren().add(btnSend);
		
		Button btnDisconnect = new Button("Disconnect");
		btnDisconnect.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				
				
			}
		});
		btnPane.getChildren().add(btnDisconnect);
		

		mainPane.getChildren().add(rbPane);
		mainPane.getChildren().add(launchPane);
		mainPane.getChildren().add(btnPane);

		stage.setScene(new Scene(mainPane, 450, 400));

		stage.show();
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
		slider.setDisable(true);
		return slider;
	}
	
	private void setLaunchEnable(boolean enable) {
		this.sliderFly.setDisable(!enable);
		this.sliderDmg.setDisable(!enable);
		this.cbDest.setDisable(!enable);
	}


	
	

	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////


	public static void main(String[] args) {
		launch(args);
	}

}
