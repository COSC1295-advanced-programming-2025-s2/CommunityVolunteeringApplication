package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Project;
import model.ProjectsDatabase;

public class DashboardController implements SceneControlled{
	
	private SceneController sceneController;
	private ObservableList<Project> projects;
	
	
	@FXML
	private Label welcomeLabel;
	
	@FXML
	private TableView<Project> projectsTable;
	
	//table columns 
	@FXML private TableColumn<Project, String> titleCol; @FXML private TableColumn<Project, String> locationCol; 
	@FXML private TableColumn<Project, String> dayCol; @FXML private TableColumn<Project, Integer> hourlyCol; @FXML private TableColumn<Project, Integer> slotsCol;
	
	
	@FXML
	public void viewHistory() {
		//WIP
	}
	
	@FXML
	public void changePassword() {
		//WIP
	}
	
	
	@FXML
	public void logOut() {
		//WIP
	}
	
	public void setUsername(String username) {
		welcomeLabel.setText("Welcome " + username +"!");
	}

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	
	public void initialize() {
		//gets the title variable from the Project class and sets the value on the column 
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
		hourlyCol.setCellValueFactory(new PropertyValueFactory<>("hourly"));
		slotsCol.setCellValueFactory(new PropertyValueFactory<>("availableSlots"));
		
		projects = ProjectsDatabase.getList();
		projectsTable.setItems(projects);




		
	}
	
	
	

}
