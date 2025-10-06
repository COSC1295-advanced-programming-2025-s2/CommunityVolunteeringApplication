package controller;

import java.io.IOException;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Cart;
import model.CartItem;
import model.DashboardModel;
import model.DbUtil;
import model.Project;
import model.Session;

public class DashboardController implements SceneControlled{
	
	private SceneController sceneController;
	private ObservableList<Project> projects = FXCollections.observableArrayList();		
	
	
	@FXML
	private Label welcomeLabel;
	
	@FXML
	private TableView<Project> projectsTable;
	
	//table columns 
	@FXML private TableColumn<Project, String> titleCol; @FXML private TableColumn<Project, String> locationCol; 
	@FXML private TableColumn<Project, String> dayCol; @FXML private TableColumn<Project, Integer> hourlyCol; @FXML private TableColumn<Project, Integer> slotsCol;
	
	@FXML
	private Spinner<Integer> numOfHoursSpinner;
	@FXML
	private Spinner<Integer> numOfSlotsSpinner;
	
	@FXML
	private Label msgLabel;
	
	
	@FXML
	public void viewHistory() {
		//WIP
	}
	
	@FXML
	public void changePassword() throws IOException {
		sceneController.switchScene("/view/changePasswordMenu.fxml");
	}
	
	
	@FXML
	public void logOut() throws IOException {
		DashboardModel.logout();
		sceneController.switchScene("/view/loginMenu.fxml");
	}
	
	
	@FXML
	public void add() {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		int hours = numOfHoursSpinner.getValue();
		int slots = numOfSlotsSpinner.getValue();
		
		//if item is already in cart then 
		if (Cart.getCart().stream().filter(item -> item.getProject().equals(selected)).findFirst().isPresent()) {
			msgLabel.setText("Project is already in cart.");
			
		}
		else {
			Cart.getCart().add(new CartItem(selected, hours, slots));
			msgLabel.setText(selected.getTitle() + " on " + selected.getDay() + " was added to cart!");
		}

	}
	
	@FXML 
	public void remove() {
		Project selected = projectsTable.getSelectionModel().getSelectedItem();
		if (selected == null) {
			msgLabel.setText("Please select a project to remove.");
		}
		else if (Cart.getCart().stream().filter(item -> item.getProject().equals(selected)).findFirst().isPresent()) {
			Cart.getCart().remove(selected);
			msgLabel.setText(selected.getTitle() + " on " + selected.getDay() +" was successfully removed from cart!");
		}
		else {
			msgLabel.setText("Project was not found in cart.");
		}

		
		
	}
	@FXML
	public void viewCart() throws IOException {
		sceneController.switchScene("/view/cartPage.fxml");
		
	}


	
	@Override
	public void setSceneController(SceneController sceneController) {
		this.sceneController = sceneController;
		
	}
	
	
	public void initialize() {
		welcomeLabel.setText("Welcome " + Session.getUser().getUsername() +"!");
		//gets the title variable from the Project class and sets the value on the column 
		titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
		locationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
		dayCol.setCellValueFactory(new PropertyValueFactory<>("day"));
		hourlyCol.setCellValueFactory(new PropertyValueFactory<>("hourly"));
		slotsCol.setCellValueFactory(new PropertyValueFactory<>("availableSlots"));
		
		projects = DbUtil.getProjects();
		projectsTable.setItems(projects);
		
		//setting up the spinners, both start at 1 with 1 being the minimum and 3 the max. 
		SpinnerValueFactory<Integer> hoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
		SpinnerValueFactory<Integer> slotsFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 3, 1);
		
		numOfHoursSpinner.setValueFactory(hoursFactory);
		numOfSlotsSpinner.setValueFactory(slotsFactory);





		
	}
	
	
	

}
