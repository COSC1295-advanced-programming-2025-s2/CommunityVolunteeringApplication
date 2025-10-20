package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import model.Project;
import model.Session;
import model.DbUtil;

public class UpdateProjectController implements SceneControlled {
    private SceneController sceneController;
    private Project project;

    @FXML private TextField titleField;
    @FXML private TextField locationField;
    @FXML private TextField hourlyField;
    @FXML private TextField totalSlotsField;
    @FXML private Label msgLabel;

	@FXML private TableColumn<Project, String> titleCol; @FXML private TableColumn<Project, String> locationCol; @FXML private TableColumn<Project, Integer> totalSlotsCol;
	@FXML private TableColumn<Project, String> dayCol; @FXML private TableColumn<Project, Integer> hourlyCol; @FXML private TableColumn<Project, Integer> registeredSlotsCol;
	
	@FXML private TableView<Project> projectsTable;

	

   
    
    @FXML
    private void updateProject() {
    	//if any of the fields are blank 
        if (titleField.getText().isBlank() || locationField.getText().isBlank() || hourlyField.getText().isBlank() || totalSlotsField.getText().isBlank()) {
            msgLabel.setText("Please fill in all fields.");
            return;
        }

        try {
        	String title = titleField.getText();
        	String location = locationField.getText();
            int hourly = Integer.parseInt(hourlyField.getText());
            int totalSlots = Integer.parseInt(totalSlotsField.getText());
            
            project.setTitle(title);
            project.setLocation(location);
            //if someone tries to enter a negative number for hourly it rejects the change.
            //hypothetically this SHOULD not happen because we reject all changes that arent numerical so someone physically cannot enter -10
            //however, i thought i'd throw in the extra check just in case. 
            if (!project.setHourly(hourly)) {
            	msgLabel.setText("Please makre sure the value for hourly is above or equal to 0.");
            }
            //set total slots will reject the change if its not above or equal to the amt of registered slots
            if (!project.setTotalSlots(totalSlots)) {
            	msgLabel.setText("Please make sure the total slots is above or equal to the number of registered slots.");
            }
            
            else if (DbUtil.updateProject()) {
                msgLabel.setText("Project updated successfully!");
            }
            


        } catch (NumberFormatException e) {
            msgLabel.setText("Please enter valid numbers for hourly and total slots.");
            
        } catch (SQLException e) {
            msgLabel.setText("Error updating project: " + e.getMessage());
            e.printStackTrace();
        }
        
        catch (Exception e) {
        	msgLabel.setText("Error updating project: " + e.getMessage());
        }
    }
    

    @FXML
    private void backToHomepage() throws IOException {
        sceneController.switchScene("/view/adminDashboard.fxml");
    }
    
    @FXML
    private void back() throws IOException {
    	sceneController.switchScene("/view/updateProject.fxml");
    }

    @Override
    public void setSceneController(SceneController sceneController) {
        this.sceneController = sceneController;
    }
    
   
    
    
    @FXML
    public void initialize() {
        this.project = Session.getProject();
        // only allow numbers for hourly and total slots
        hourlyField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d*") ? c : null));
        totalSlotsField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("\\d*") ? c : null));
        
        
        
        //setting the table up to display the selected project
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
		hourlyCol.setCellValueFactory(new PropertyValueFactory<>("hourly"));
		registeredSlotsCol.setCellValueFactory(new PropertyValueFactory<>("registeredSlots"));
		totalSlotsCol.setCellValueFactory(new PropertyValueFactory<>("totalSlots"));
		projectsTable.setItems(FXCollections.observableArrayList(project));
        

        //loading fields by getting current project

        titleField.setText(project.getTitle());
        locationField.setText(project.getLocation());
        hourlyField.setText(String.valueOf(project.getHourly()));
        totalSlotsField.setText(String.valueOf(project.getTotalSlots()));
        

      }

   

}
