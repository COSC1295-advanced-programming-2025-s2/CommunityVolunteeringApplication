import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		
		//testing out the login menu
		//TO DO:
		//fix the sizing, opens up weirdly
		//fully implement the password requirements
		//add in sql to use for passwords and usernames
		
		Parent root = FXMLLoader.load(getClass().getResource("loginMenu.fxml"));
		
		
		stage.setTitle("Login menu");
		stage.setScene(new Scene(root, 400, 300));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
		

	}

}
