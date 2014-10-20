package clientserver;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


public class Client extends Application {


	
	@Override
	public void start(Stage stage) {
		stage.setTitle("Statistics by Dates");

		FlowPane mainPane = new FlowPane(Orientation.VERTICAL);
		mainPane.setColumnHalignment(HPos.CENTER);

		GridPane datePane = new GridPane();
		datePane.setAlignment(Pos.CENTER);
		datePane.setHgap(10);
		datePane.setVgap(10);
		datePane.setPadding(new Insets(25, 25, 25, 25));
		
		Label lblStart = new Label("Start Date");
		datePane.add(lblStart, 0, 0);
		

		
		Label lblEnd = new Label("End Date");
		datePane.add(lblEnd, 0, 1);
		
		
		
		mainPane.getChildren().add(datePane);

		Button btn = new Button("Get Report");
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {


				
			}
		});
		
		mainPane.getChildren().add(btn);

		stage.setScene(new Scene(mainPane, 290, 160));

		stage.show();

	}
	
	

	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////
	//////////////////////////////////////////////////////////////////////////////////////

	
	public static void main(String[] args) {
		launch(args);

	}

}
