package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DbUtil;
import model.LoginModel;
import model.SignupModel;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;


public class LoginController implements SceneControlled {
	
	@FXML 
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label msgLabel;
	@FXML
	private Label usernameLabel;
	
	private SceneController sceneController;
	
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
	}

	@FXML 
	private void loginClicked(ActionEvent event) throws IOException {
		String user = username.getText().trim();
		String pass = password.getText().trim();
		if (user.isEmpty()) {
			usernameLabel.setText("*REQUIRED FIELD");
		}
		if (pass.isEmpty()) {
			msgLabel.setText("*REQUIRED FIELD");
		}
		
		
		//if the user login comes back positive (i.e, we found the username and password in the system)
		else if (DbUtil.userLogin(user, pass)) {
			//we change the scene to the dashboard, and then we pass on the username to the dashboard label with the method setUsername. This is a bit of a 
			//bandaid fix for now since I will have to change it eventually because the user will need to sign up to events and activities, meaning I'll need to have made
			//a user object or some other thing instead of just needing the username. 
			sceneController.switchScene("/view/dashboardMenu.fxml");
			DashboardController controller = (DashboardController) sceneController.getCurrentController();
			controller.setUsername(user);

		}
		else {
			msgLabel.setText("Username or password is incorrect.");
		}
		
	}
	
	//when the user clicks sign up, it'll change the scene to the signup scene.
	@FXML 
	private void signupClicked(ActionEvent event) {
		try {
			sceneController.switchScene("/view/signupMenu.fxml");;
			
			//FXMLLoader loader = new FXMLLoader(getClass().getResource("/signupMenu.fxml"));
			//Parent root = loader.load();
			
			//Stage stage = (Stage) msgLabel.getScene().getWindow();
			//stage.setScene(new Scene(root));
						
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	



	
}
	
