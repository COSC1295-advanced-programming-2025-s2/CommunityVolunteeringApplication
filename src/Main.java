import controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.DbUtil;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
        SceneController sceneController = new SceneController(stage);

        
        //the SceneController now handles all scene setup and loading so there is no need for scene setup here.
        sceneController.switchScene("/view/loginMenu.fxml");;


	}

	public static void main(String[] args) {
		launch(args);
		

	}

}
