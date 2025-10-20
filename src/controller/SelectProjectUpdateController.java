package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.DbUtil;
import model.Project;
import model.Session;

public class SelectProjectUpdateController implements SceneControlled {
    private SceneController sceneController;
    private Project project;
    
    @FXML private CheckBox mon, tue, wed, thu, fri, sat, sun;
	@FXML private TableColumn<Project, String> titleCol; @FXML private TableColumn<Project, String> locationCol; @FXML private TableColumn<Project, Integer> totalSlotsCol;
	@FXML private TableColumn<Project, String> dayCol; @FXML private TableColumn<Project, Integer> hourlyCol; @FXML private TableColumn<Project, Integer> registeredSlotsCol;
	
	@FXML private TableView<Project> projectsTable;
	@FXML private Label msgLabel;
	
    @FXML
    private void updateDays() {
    	//updates projects based on selected days 

        try {
    		Project selected = projectsTable.getSelectionModel().getSelectedItem();
    		if (selected == null) {
    			msgLabel.setText("Please select a project to add days to!");
    			return;
    		}
    		
    		Session.setSelectedProject(selected);
            List<String> selectedDays = new ArrayList<>();
            if (mon.isSelected()) selectedDays.add("Mon");
            if (tue.isSelected()) selectedDays.add("Tue");
            if (wed.isSelected()) selectedDays.add("Wed");
            if (thu.isSelected()) selectedDays.add("Thu");
            if (fri.isSelected()) selectedDays.add("Fri");
            if (sat.isSelected()) selectedDays.add("Sat");
            if (sun.isSelected()) selectedDays.add("Sun");
            
            if (selectedDays.isEmpty()) {
                msgLabel.setText("Select at least one day for this project.");
                return;
            }

            //deleting old rows that are no longer selected and adding rows that are selected
            DbUtil.updateProjectDays(selectedDays); 
            msgLabel.setText("Project updated successfully!");

        } 
        catch (SQLException e) {
            msgLabel.setText("Error updating project: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
	@FXML
	private void selectProject() throws IOException {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			msgLabel.setText("Please select a specific project to edit");
			return;
		}
		Session.setSelectedProject(selected);
		sceneController.switchScene("/view/updateProjectMenu.fxml");
	}
	
	@FXML
	private void back() throws IOException {
		sceneController.switchScene("/view/adminDashboard.fxml");
	}

	@FXML
	public void initialize() {
        project = Session.getProject();
        ObservableList<Project> selectedProjects = DbUtil.getSelectedProjects(project);
        
        
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
		hourlyCol.setCellValueFactory(new PropertyValueFactory<>("hourly"));
		registeredSlotsCol.setCellValueFactory(new PropertyValueFactory<>("registeredSlots"));
		totalSlotsCol.setCellValueFactory(new PropertyValueFactory<>("totalSlots"));		
		projectsTable.setItems(selectedProjects);
        
		// if that project has a day (lets say monday), it checks the mon checkbox.
        for (Project p : selectedProjects) {
            switch (p.getDay().toLowerCase()) {
            case "mon" -> mon.setSelected(true);
            case "tue" -> tue.setSelected(true);
            case "wed" -> wed.setSelected(true);
            case "thu" -> thu.setSelected(true);
            case "fri" -> fri.setSelected(true);
            case "sat" -> sat.setSelected(true);
            case "sun" -> sun.setSelected(true);
            }

        	
        }
      }
	

	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	

}
