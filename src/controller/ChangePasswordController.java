package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.Session;

public class ChangePasswordController implements SceneControlled{
	private SceneController sceneController;
	
	@FXML
	private TextField currentPass;
	@FXML
	private TextField newPass;
	@FXML
	private TextField confirmPass;
	@FXML
	private Label msgLabel;
	
	@FXML
	public void confirm() {
		String currentP = currentPass.getText();
		String newP = newPass.getText();
		String confirmP = confirmPass.getText();
		//if the current password matches with password entered
		//if the new passwords are the same
		ArrayList<String> pCheck = model.PasswordModel.passwordIsValid(newP);
		if (!(newP.equals(confirmP))) {
			msgLabel.setText("New passwords do not match!");
		}
		else if (!(model.PasswordModel.passwordIsValid(newP).isEmpty())) {
			msgLabel.setText("Password must have: " + String.join(", ", pCheck));
		}
		else if (model.DbUtil.changePassword(currentP, newP, Session.getUser().getUserId())) {
			msgLabel.setText("Password changed!");
		}
		else {
			msgLabel.setText("Password was incorrect, please try again.");
		}
		//fond user id 
		
		//then change new password to current password (do hashing as well)
		//put success on the text label, else put requirements
		
		
	}
	
	
	@FXML
	public void back() throws IOException {
		sceneController.switchScene("/view/dashboardMenu.fxml");
	}
	
	
	

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	
	

}
