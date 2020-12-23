package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Main extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("build.fxml"));
			primaryStage.setScene(new Scene(root));
			primaryStage.setTitle("agenda");
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void stop() throws IOException {
		Controller.exit();
	}
}
