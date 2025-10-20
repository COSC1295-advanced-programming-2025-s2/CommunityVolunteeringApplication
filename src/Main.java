import controller.SceneController;
import javafx.application.Application;
import javafx.stage.Stage;
import model.DbUtil;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
        SceneController sceneController = new SceneController(stage);

        //CHANGES:
        //changed all public fxml methods to private for better practice.
        //NEED TO DO:
        //JUnit testing 
        
        sceneController.switchScene("/view/loginMenu.fxml");;


	}

	public static void main(String[] args) {
		launch(args);
		

	}

}
