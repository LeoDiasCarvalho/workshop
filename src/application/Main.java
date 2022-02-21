package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;


public class Main extends Application {
	
	@Override
	public void start(Stage MainView) {
		try {
			
			ScrollPane root = FXMLLoader.load(getClass().getResource("/gui/MainView.fxml"));
			Scene scene = new Scene(root);
			
			root.setFitToHeight(true);
			root.setFitToWidth(true);
			
			MainView.setTitle("Work Shop");
			MainView.setScene(scene);
			MainView.show();
			
		
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
