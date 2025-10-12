package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import model.DbUtil;
import model.PasswordModel;
import model.SignupModel;
import model.StringUtil;
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
	private void signup() throws IOException {
		String fName = name.getText().trim();
		String e = email.getText().trim();
		String user = username.getText().trim();
		String pass = password.getText().trim();
		
		
		isEmpty(fName,nameLabel);
		isEmpty(e,emailLabel);
		isEmpty(user,usernameLabel);
		isEmpty(pass,msgLabel);
		
		ArrayList<String> nCheck = SignupModel.nameIsValid(fName);
		if (!(nCheck.isEmpty())) {
			nameLabel.setText("Name must have: " + String.join(", ", nCheck));
		}
		
		ArrayList<String> eCheck = SignupModel.emailIsValid(e);
		if (!(eCheck.isEmpty())) {
			emailLabel.setText("Email must have: " + String.join(", ", eCheck));
		}
		
		ArrayList<String> uCheck = SignupModel.usernameIsValid(user);
		if (!(uCheck.isEmpty())) {
			usernameLabel.setText("Username must: " + String.join(", ", uCheck));
		}

		
		//this is the final check, we create the passwordcheck and if password is valid
		ArrayList<String> pCheck = PasswordModel.passwordIsValid(pass);
		//we check for if all the other requirements are valid (if the label is empty then theres no more requirements so we go ahead)
		if (nameLabel.getText().isEmpty() && emailLabel.getText().isEmpty() && usernameLabel.getText().isEmpty() && msgLabel.getText().isEmpty() && pCheck.isEmpty()) {
			fName = StringUtil.toUpper(fName.split(" "));
			User u = new User(fName,e,user,pass);
			//and make the user
			String result = SignupModel.registerUser(u);
			//then we sign them up, if the result comes back empty (i.e, theres no errors)
			if (result == null) {
				//then the signup was successful and the user has now been added to the database
				msgLabel.setText("Signup successful!");
			}
			//if it wasnt, then we see the result (username or email was taken)
			else {
				msgLabel.setText(result);
			}
			
		}
		//bit of extra code with emptyfield but its okay for now. 
		else if (!(SignupModel.emptyField(pass)) && !(pCheck.isEmpty())){
			msgLabel.setText("Password must have: " + String.join(", ", pCheck));
		}
		
		
		
		
	}
	
	@FXML
	private void backToLogin() throws IOException {
		//when the user clicks on the back to login hyperlink it sends them back to the login page
		sceneController.switchScene("/view/loginMenu.fxml");
	}
	
	
	public void isEmpty(String str, Label lbl) {
		if (SignupModel.emptyField(str)) {
			lbl.setText("*REQUIRED FIELD");
		}
		else {
			lbl.setText("");
		}
	}
	
	@FXML
	public void initialize() {
		//no numbers 
		name.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("[a-zA-Z ]*") ? c : null));

	}
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
		
	

}
