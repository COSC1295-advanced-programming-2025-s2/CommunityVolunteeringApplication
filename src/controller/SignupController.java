package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.DbUtil;
import model.SignupModel;
import model.User;

public class SignupController implements SceneControlled {
	
	
	@FXML 
	private TextField name;	
	@FXML 
	private TextField email;	
	@FXML 
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private Label msgLabel;
	@FXML
	private Label emailLabel;
	@FXML
	private Label usernameLabel;
	@FXML 
	private Label nameLabel;
	
	private SceneController sceneController;
	
	
	@FXML 
	public void signup() throws IOException {
		String fName = name.getText().trim();
		String e = email.getText().trim();
		String user = username.getText().trim();
		String pass = password.getText().trim();
		
		
		isEmpty(fName,nameLabel);
		isEmpty(e,emailLabel);
		isEmpty(user,usernameLabel);
		isEmpty(pass,msgLabel);
		
		lengthCheck("Name",fName,nameLabel);
		lengthCheck("Username", user,usernameLabel);

		ArrayList<String> eCheck = SignupModel.emailIsValid(e);
		if (!(eCheck.isEmpty())) {
			emailLabel.setText("Email must have: " + String.join(", ", eCheck));
		}

		
		
		ArrayList<String> pCheck = SignupModel.passwordIsValid(pass);
		if (nameLabel.getText().isEmpty() && emailLabel.getText().isEmpty() && usernameLabel.getText().isEmpty() && msgLabel.getText().isEmpty()) {
			User u = new User(fName,e,user,pass);
			String result = SignupModel.registerUser(u);
			if (result == null) {
				msgLabel.setText("Signup successful!");
			}
			else {
				msgLabel.setText(result);
			}
			
		}
		else if (!(pCheck.isEmpty())){
			msgLabel.setText("Password must have: " + String.join(", ", pCheck));
		}
		
		
		
		
	}
	
	@FXML
	public void backToLogin() throws IOException {
		sceneController.switchScene("/view/loginMenu.fxml");
	}
	
	private void isEmpty(String str, Label lbl) {
		if (SignupModel.emptyField(str)) {
			lbl.setText("*REQUIRED FIELD");
		}
		else {
			lbl.setText("");
		}
	}
	
	private void lengthCheck(String field, String str, Label lbl) {
		if (SignupModel.underLen(str)) {
			lbl.setText(field + " must have at least 3 characters");
		}
	}

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
		
	

}
