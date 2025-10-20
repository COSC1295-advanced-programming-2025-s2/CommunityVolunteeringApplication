package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DbUtil;
import model.Session;
import model.User;

import java.io.IOException;
import java.sql.SQLException;

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

	@FXML 
	private void loginClicked(ActionEvent event) throws IOException, SQLException {
		String usern = username.getText().trim();
		String pass = password.getText().trim();
		User user = DbUtil.userLogin(usern, pass);
		if (usern.isEmpty()) {
			usernameLabel.setText("*REQUIRED FIELD");
		}
		if (pass.isEmpty()) {
			msgLabel.setText("*REQUIRED FIELD");
		}
		
		
		//if the user login comes back positive (i.e, we found the username and password in the system)
		else if (user != null) {
			Session.setUser(user);
			//set the session user so that we can grab it easily. 
			
			if (user.getUsername().equals("admin")) {
				//if the username of the user is admin we set it to the admin dashboard
				user.setAdmin(true);
				sceneController.switchScene("/view/adminDashboard.fxml");
			}
			else {
				sceneController.switchScene("/view/dashboardMenu.fxml");

			}

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
			
			
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
	}
	



	
}
	
