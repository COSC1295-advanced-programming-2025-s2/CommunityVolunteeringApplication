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
	private void confirm() {
		String currentP = currentPass.getText().trim();
		String newP = newPass.getText().trim();
		String confirmP = confirmPass.getText().trim();
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
		
		
	}
	
	
	@FXML
	private void back() throws IOException {
		sceneController.switchScene("/view/dashboardMenu.fxml");
	}
	
	
	

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	
	

}
