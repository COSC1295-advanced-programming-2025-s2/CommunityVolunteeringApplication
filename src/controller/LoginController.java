package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;


public class LoginController {
	
	@FXML 
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label msgLabel;
	
	@FXML 
	private void loginClicked(ActionEvent event) {
		msgLabel.setText("Login successful!");
		
	}
	
	@FXML 
	private void signupClicked(ActionEvent event) {
		String pass = password.getText().trim();
		
		
		if (!(pass.length() > 8)) {
			msgLabel.setText("Please make sure your password is above 8 characters in length!");
			
		}
	}
	
	
	

}
