package controller;

import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.SignupModel;

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
	public void signup() {
		String fName = name.getText().trim();
		String e = email.getText().trim();
		String user = username.getText().trim();
		String pass = password.getText().trim();
		
		
		isEmpty(fName,nameLabel);
		isEmpty(e,emailLabel);
		isEmpty(user,usernameLabel);
		isEmpty(pass,msgLabel);
		
		ArrayList<String> eCheck = SignupModel.emailIsValid(e);
		if (!(eCheck.isEmpty())) {
			emailLabel.setText("Email must have: " + String.join(", ", eCheck));
		}

		
		
		ArrayList<String> pCheck = SignupModel.passwordIsValid(pass);
		if (!(pass.isBlank()) && pCheck.isEmpty() && !(fName.isBlank()) && !(e.isBlank()) && !(user.isBlank())) {
			msgLabel.setText("Signup successful!");
			
		}
		else if (!pass.isBlank()){
			msgLabel.setText("Password must have: " + String.join(", ", pCheck));
		}
		
		
		
		
	}
	
	private void isEmpty(String str, Label lbl) {
		if (SignupModel.emptyField(str)) {
			lbl.setText("*REQUIRED FIELD");
		}
		else {
			lbl.setText("");
		}
	}

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
		
	

}
