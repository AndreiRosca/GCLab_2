
package md.utm.labs.gc;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader loader = new FXMLLoader();
		loader.setController(new Controller());
		Parent root = (Parent) loader.load(getClass().getResourceAsStream("/metadata.fxml"));
		stage.setScene(new Scene(root));
		stage.setTitle("Shape manipulator v1.0");
		stage.show();
	}
}
