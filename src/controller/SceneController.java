package controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
	//i created this class to help me switch scenes instead of creating a new instance every time
    private Stage stage;
    private Scene scene;

    public SceneController(Stage stage) {
        this.stage = stage;
    }
    
    
    //switches the current scene by the one specified in the fxml file
    public void switchScene(String file) throws IOException{
    	//throws io exception if file cannot be loaded, and illegalargument exception if file cant be found
        var resource = getClass().getResource(file);
        if (resource == null) {
            throw new IllegalArgumentException("FXML file not found: " + file);
        }
        

        FXMLLoader loader = new FXMLLoader(resource);
        Parent root = loader.load();
        
        //injects the scenecontroller into the controller if its supported
        Object controller = loader.getController();
        if (controller instanceof SceneControlled) {
            ((SceneControlled) controller).setSceneController(this);
        }
        
        //set or update scene with the new root 
        if (scene == null) {
            scene = new Scene(root);
            stage.setScene(scene);
        } else {
            scene.setRoot(root);
        }
        stage.sizeToScene(); 
        //stage resizes to fit the scenes preferred size
        stage.show();
    }

}