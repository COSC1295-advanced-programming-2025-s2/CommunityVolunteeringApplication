package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import model.DbUtil;
import model.ExportUtil;
import model.Registration;
import model.Session;

public class RegistrationHistoryController implements SceneControlled {
	private SceneController sceneController;
	private String registrations;
	
	@FXML
	private Label msgLabel;
	
	@FXML
	private Label exportLabel;
	
	@FXML
	public void exportHistory() {
		
		if (ExportUtil.exportHistoryToFile(DbUtil.getRegistrations(), Session.getUser().getUsername())){
			exportLabel.setText("Export successful!");
		}
		else {
			exportLabel.setText("An error occured, please try again.");
		}
	}
	
	@FXML 
	public void back() throws IOException {
		sceneController.switchScene("/view/dashboardMenu.fxml");
	}

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	@FXML
	public void initialize() {
		ArrayList<Registration> registrationsList = DbUtil.getRegistrations();
		
		registrations = String.join("\n\n", registrationsList.stream().map(Registration::toString).toList());
		
		msgLabel.setText(registrations);

		
	}
	
	

}
