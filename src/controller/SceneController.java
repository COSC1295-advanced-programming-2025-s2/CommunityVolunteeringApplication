package controller;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Project;

import java.io.IOException;

public class SceneController {
	//i created this class to help me switch scenes instead of creating a new instance every time, this avoids too much memory use by using only one stage
    private Stage stage;
    private Scene scene;
    private Object currentController;

    public SceneController(Stage stage) {
        this.stage = stage;
        stage.setTitle("VolunTrack");
    }
    
    
    //switches the current scene by the one specified in the fxml file
    public void switchScene(String file) throws IOException{
    	//throws io exception if file cannot be loaded, and illegalargument exception if file cant be found
        var resource = getClass().getResource(file);
        //i wondered whether it'd be needed to put a try catch in this area. However, since I am the one hardcoding the fxml files there is no need. In the future, if I am working with
        //fxml files that need to be hardcoded or if someone else downloads the code on their computer and deletes some of the files then it'd be needed. But for now, I'll leave it as is.
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
        //helps me parse data through 
        currentController = controller; 
        
        //set or update scene with the new root, makes sure only one scene is active at a time 
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
    
    
    public Object getCurrentController() {
    	return currentController;
    }

}