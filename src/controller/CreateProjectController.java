package controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import model.DbUtil;
import model.Project;
import model.StringUtil;

public class CreateProjectController implements SceneControlled {
	private SceneController sceneController;
	@FXML
	private TextField titleField;
	@FXML
	private TextField locationField;
	@FXML
	private TextField hourlyField;
	@FXML
	private TextField totalSlotsField;
	@FXML
	private Label dayLabel;
	@FXML
	private Label msgLabel;

    //days
    @FXML private CheckBox mon; @FXML private CheckBox tue; @FXML private CheckBox wed; @FXML private CheckBox thu;
    @FXML private CheckBox fri; @FXML private CheckBox sat; @FXML private CheckBox sun;

	
	@FXML
	private void createProject() {
		String title = titleField.getText();
		String location = locationField.getText();
		//a list of the checkboxes 
		List<String> selectedDays = Stream.of(mon, tue, wed, thu, fri, sat, sun).filter(CheckBox::isSelected).map(CheckBox::getText).collect(Collectors.toList());

		String hourly = hourlyField.getText();
		String totalSlots = totalSlotsField.getText();
		
		
		if (title.isBlank() && location.isBlank() && selectedDays.isEmpty() && hourly.isBlank() && totalSlots.isBlank()) {
			msgLabel.setText("Please make sure all values are filled.");
		}
		else {
			//make title and location capitalised 
			title = StringUtil.toUpper(title.split(" "));
			location = StringUtil.toUpper(location.split(" "));
			
			//for each day selected we create a project row with that day 
			for (String day : selectedDays) {
				if (DbUtil.createProject(new Project(title, location, day, Integer.parseInt(hourly),Integer.parseInt(totalSlots)))) {
					msgLabel.setText("Project creation of " + title + " was successful!");
				}
				
				//shouldnt be happening but its there.
				else {
					msgLabel.setText("Project creation failed, please try again.");
				}

			}			
		}
		
	}
	
	@FXML
	private void backToHomepage() throws IOException {
		sceneController.switchScene("/view/adminDashboard.fxml");
	}
	
	
	
	
	
	
	
	@FXML
	public void initialize() {
		//no numbers
		titleField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("[a-zA-Z ]*") ? c : null));
		locationField.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches("[a-zA-Z ]*") ? c : null));
		//no letters
		hourlyField.setTextFormatter(new TextFormatter<> (c -> c.getControlNewText().matches("\\d*") ? c : null));
		totalSlotsField.setTextFormatter(new TextFormatter<> (c -> c.getControlNewText().matches("\\d*") ? c : null));



	}
	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	

}
