import controller.LoginController;
import controller.SceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
        SceneController sceneController = new SceneController(stage);

        
        // The SceneController will now handle all scene setup and loading.
        sceneController.switchScene("/loginMenu.fxml");;


	}

	public static void main(String[] args) {
		launch(args);
		

	}

}
