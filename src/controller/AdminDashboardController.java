package controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;

import model.DbUtil;
import model.Project;
import model.Session;

public class AdminDashboardController implements SceneControlled {
	private SceneController sceneController;
	@FXML
	private TableView<Project> projectsTable;
	private ObservableList<Project> projects = FXCollections.observableArrayList();	
	@FXML
	private Label msgLabel;
	
	//table columns 
	@FXML private TableColumn<Project, String> titleCol; @FXML private TableColumn<Project, String> locationCol; 
	@FXML private TableColumn<Project, String> dayCol; @FXML private TableColumn<Project, Integer> hourlyCol;
	
	


	
	
	
	@FXML
	private void enable() {
		//selects the project
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			msgLabel.setText("Please select a project.");
		}
		
		//if signup is already enabled 
		else if (selected.getSignupAvailable() == true) {
			msgLabel.setText("Project is already enabled.");
		}
		//enable project 
		else if (DbUtil.enableOrDisableProject(selected, true)) {
			msgLabel.setText(selected.getTitle() + " is now enabled!");
			initialize();
			
		}
		else {
			msgLabel.setText("An error occurred when attempting to enable the project, please try again.");
		}
		
	}
	
	@FXML
	private void update() throws IOException {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			msgLabel.setText("Please select a project.");
		}
		else {
			Session.setSelectedProject(selected);
			sceneController.switchScene("/view/updateProject.fxml");
		}
		
	}
	
	
	
	//scene switchers
	@FXML
	private void viewHistory() throws IOException {
		sceneController.switchScene("/view/registrationHistory.fxml");
	}
	
	@FXML
	private void logOut() throws IOException {
		sceneController.switchScene("/view/loginMenu.fxml");
		Session.clear();
	}
	
	@FXML
	private void add() throws IOException {
		sceneController.switchScene("/view/createProjectMenu.fxml");
		
	}
	
	
	
	@FXML
	private void disable() {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			msgLabel.setText("Please select a project.");
		}
		else if (selected.getSignupAvailable() == false) {
			msgLabel.setText("Project is already disabled.");
		}
		else if (DbUtil.enableOrDisableProject(selected, false)) {
			msgLabel.setText(selected.getTitle() + " is now disabled!");
			initialize();
			
		}
		else {
			msgLabel.setText("An error occurred when attempting to disable the project, please try again.");
		}
	}
	
	


	
	public void initialize() {
		DbUtil.importProjects(); 
		DbUtil.getAllRegistrations();
		projects = DbUtil.getProjects();
		Map<String, List<Project>> groupedProjects = projects.stream().collect(Collectors.groupingBy(Project::getTitle));
		
		//groups all projects with same title together 
		ObservableList<Project> groupedProjectList = FXCollections.observableArrayList();
	    groupedProjects.forEach((title, projectList) -> {
	        if (!projectList.isEmpty()) {
	        	groupedProjectList.add(projectList.get(0)); 
	        }
	    });


		//gets the title variable from the Project class and sets the value on the column 
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		
		//changes days to mon / wed if theres multiple days for example
		dayCol.setCellValueFactory(cellData -> {
		    Project project = cellData.getValue();
		    List<Project> sameTitleProjects = groupedProjects.get(project.getTitle());
		    String joinedDays = sameTitleProjects.stream().map(Project::getDay).distinct().collect(Collectors.joining(" / ")); 
		    return new SimpleStringProperty(joinedDays);
		});
		
		hourlyCol.setCellValueFactory(new PropertyValueFactory<>("hourly"));
		
		
		projectsTable.setItems(groupedProjectList);
		
		//greys out any projects that the user has already signed up for.
		projectsTable.setRowFactory(tv -> new TableRow<Project>() {
		    @Override
		    protected void updateItem(Project project, boolean empty) {
		        super.updateItem(project, empty);
		        //if the list of project ids contains the project id of a row it means theyve alrdy signed up for it so we grey it out (change the colour and disable selection)
		        if (project == null || empty) {
		            setStyle("");
		            setDisable(false);
		        }
		        
		            
		        else if (project.getSignupAvailable() == false) {
		            setStyle("-fx-background-color: #d3d3d3;");
		            setDisable(false);
		        } 
		        else {
		            setStyle("");
		            setDisable(false);
		        }
		    }
		});
		

	}



	
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	

}
