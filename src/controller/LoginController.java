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

import java.util.ArrayList;

import javafx.event.ActionEvent;


public class LoginController implements SceneControlled {
	
	@FXML 
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label msgLabel;
	
	private SceneController sceneController;
	
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
	}

	@FXML 
	private void loginClicked(ActionEvent event) {
		String user = username.getText().trim();
		String pass = password.getText().trim();
		if (DbUtil.userLogin(user, pass)) {
			msgLabel.setText("Login successful!");

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
			
			System.out.println("scene changed");
			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	

}
	
