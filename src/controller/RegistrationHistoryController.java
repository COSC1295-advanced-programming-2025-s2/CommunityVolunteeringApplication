package controller;

import java.io.IOException;
import java.util.ArrayList;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DbUtil;
import model.ExportUtil;
import model.Registration;
import model.Session;

public class RegistrationHistoryController implements SceneControlled {
	private SceneController sceneController;
	
	@FXML
	private TableView<Registration> registrationsTable;

	@FXML private TableColumn<Registration, Integer> regIDCol;
	@FXML private TableColumn<Registration, Integer> userIDCol;
	@FXML private TableColumn<Registration, Integer> projIDCol;
	@FXML private TableColumn<Registration, String> titleCol;
	@FXML private TableColumn<Registration, String> dayCol;
	@FXML private TableColumn<Registration, String> locationCol;
	@FXML private TableColumn<Registration, String> timeCol;

	private ObservableList<Registration> registrations = FXCollections.observableArrayList();

	@FXML
	private Label msgLabel;
	
	@FXML
	private Label exportLabel;
	
	@FXML
	private void exportHistory() {
		
		if (ExportUtil.exportHistoryToFile(DbUtil.getRegistrations(), Session.getUser().getUsername())){
			exportLabel.setText("Export successful!");
		}
		else {
			exportLabel.setText("An error occured, please try again.");
		}
	}
	
	@FXML 
	private void back() throws IOException {
		if (Session.getUser().isAdmin()) {
			sceneController.switchScene("/view/adminDashboard.fxml");
		}
		else {
			sceneController.switchScene("/view/dashboardMenu.fxml");
		}
	}

	
	@FXML
	private void initialize() {
		//loads data from the registrationslist into the tableview 
		registrations = DbUtil.getRegistrations();
		
	    regIDCol.setCellValueFactory(new PropertyValueFactory<>("registrationId"));
	    userIDCol.setCellValueFactory(new PropertyValueFactory<>("userId"));
	    projIDCol.setCellValueFactory(new PropertyValueFactory<>("projectId"));

	    titleCol.setCellValueFactory(cellData ->  new SimpleStringProperty(cellData.getValue().getProject().getTitle()));
	    dayCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProject().getDay()));
	    locationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProject().getLocation()));
	    timeCol.setCellValueFactory(new PropertyValueFactory<>("dateTime"));

	    registrationsTable.setItems(registrations);


		
	}
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	

}
